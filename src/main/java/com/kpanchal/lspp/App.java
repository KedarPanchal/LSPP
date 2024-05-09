package com.kpanchal.lspp;

import java.io.FileNotFoundException;
import java.util.concurrent.Callable;
import java.util.ArrayList;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;

import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Spec;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import com.kpanchal.lspp.tree.FileTree;
import com.kpanchal.lspp.tree.FileTreeFactory;
import com.kpanchal.lspp.tree.FileTreeSearcher;
import com.kpanchal.lspp.tree.FileTreeWalker;

import com.kpanchal.lspp.args.CharsetConverter;
import com.kpanchal.lspp.args.CharsetEnum;
import com.kpanchal.lspp.args.PathConverter;

/**
 * The main application class for the {@code lspp} command, which lists out the files of a directory in a tree-style
 * structure
 * @author Kedar Panchal
 */
@Command(name="lspp", version="lspp 1.0.1", description="Lists the files in a folder in a tree-style output\n\nWritten by Kedar Panchal\n", sortOptions=false)
public class App implements Callable<Integer> {
    // Contains version and help information for the lspp command
    @Spec CommandSpec spec;

    // The directory to list files in. By default, this is the current working directory
    @Parameters(index = "0", defaultValue="", paramLabel="DIRECTORY", converter=PathConverter.class, description="The directory to list files in. If none is specified, then the current working directory's contents are listed.")
    private Path directory;

    // How many layers of the tree should be listed in the command line. This needs to be an integer greater than or
    // equal to zero
    private int depth;
    @Option(names={"-d", "--depth"}, defaultValue="0", description="The depth of the files to list in a tree.")
    public void setDepth(int value) {
        if (value < 0) {
            throw new ParameterException(spec.commandLine(), String.format("Invalid value '%s' for option '--depth': value is not a positive integer", value));
        } else {
            this.depth = value;
        }
    }

    // The name of the file to search for, if there are any
    @Option(names={"-s", "--search"}, description="The name of the file to search for. Only the file and its parent directories will be displayed.")
    private String fileName;

    // The regular expression used to construct a filtered FileTree with, which only contains the files and
    // subdirectories in the directory being searched that match the following regular expression, along with their
    // parent directories relative to the directory being searched
    @Option(names={"-a", "--search-all"}, description="Searches for all files that match the specified regex and lists a file tree containing only those files.")
    private String regex;

    // The charset used to display the FileTree with
    @Option(names={"-c", "--charset"}, defaultValue="ascii", converter=CharsetConverter.class, description="The charset to use when displaying the file tree (default: ASCII). Valid values (case-insensitive): ${COMPLETION-CANDIDATES}.")
    private CharsetEnum charset;

    // Whether to display version information or not
    @Option(names={"-v", "--version"}, versionHelp=true, description="Outputs the version of the program.")
    private boolean version;

    // Whether to display help information or not
    @Option(names={"-h", "--help"}, usageHelp=true, description="Displays this message.")
    private boolean help;

    /**
     * Processes the various arguments passed into lspp as described above and by the {@code --help} argument, and
     * executes the {@code lspp} command accordingly.
     * @return the following error codes:
     *         <ul>
     *             <li>{@code 0} if the program executes successfully</li>
     *             <li>{@code 1} if a file cannot be found</li>
     *         </ul>
     * @throws Exception if the file tree cannot be constructed -- TODO replace this with error codes
     */
    @Override
    public Integer call() throws Exception {
        this.validate();

        if (this.depth > 0) {
            FileTree tree = this.buildFileTree(this.directory);
            this.depthList(tree, this.depth, this.charset);
        } else if (this.fileName != null) {
            FileTree tree = this.buildFileTree(this.directory);
            this.searchList(tree, Path.of(this.fileName), this.charset);
        } else if (this.regex != null) {
            FileTree tree = this.buildFilteredTree(this.directory, this.regex);
            this.depthList(tree, tree.getDepth(), this.charset);
        } else if (this.help) {
            spec.commandLine().usage(System.out);
        } else if (this.version) {
             spec.commandLine().printVersionHelp(System.out);
        } else {
            FileTree tree = this.buildFileTree(this.directory);
            this.depthList(tree, tree.getDepth(), this.charset);
        }

        return 0;
    }

    public static void main(String[] args) {
        System.exit(new CommandLine(new App()).execute(args));
    }

    /**
     * Validates the arguments passed into the {@code lspp} command by checking if the {@code --depth},
     * {@code --search}, {@code --search-all}, {@code --version}, and {@code --help} commands aren't used in tandem with
     * each other
     * @throws ParameterException if more than one of the arguments listed above are passed into the {@code lspp}
     *                            command
     */
    private void validate() throws ParameterException {
        ArrayList<Boolean> validateList = new ArrayList<>();
        validateList.add(depth > 0);
        validateList.add(fileName != null);
        validateList.add(regex != null);
        validateList.add(version);
        validateList.add(help);

        if (validateList.stream().filter(b -> b).count() > 1) {
            throw new ParameterException(spec.commandLine(), "Error: too many arguments");
        }
    }

    /**
     * Constructs a FileTree from a provided Path using its contents, subdirectories, and subdirectories' contents
     * @param path the path of the directory to construct a FileTree for
     * @return a FileTree constructed from the provided directory
     * @throws IOException if the Path provided is not a directory, or if the directory's contents cannot be accessed
     */
    public FileTree buildFileTree(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            throw new IOException("Error: file provided is not a directory");
        }

        FileTreeFactory treeFactory = new FileTreeFactory();
        FileTree tree = treeFactory.makeFileTree();
        Files.walk(path, FileVisitOption.FOLLOW_LINKS).forEach(tree::add);
        return tree;
    }

    /**
     * Constructs a FileTree from a provided Path containing only its contents that match the provided regular
     * expression, along with the parent directories of those contents relative to the provided Path
     * @param path the path of the directory to construct a FileTree for
     * @param regex the regular expression to apply for the contents of the path parameter
     * @return a FileTree constructed from the provided directory containing only its contents that match the provided
     *         regular expression and those contents' parent directories relative to the provided Path
     * @throws IOException if the Path provided is not a directory, or if the directory's contents cannot be accessed
     */
    public FileTree buildFilteredTree(Path path, String regex) throws IOException {
        if (!Files.isDirectory(path)) {
            throw new IOException("Error: file provided is not a directory");
        }

        FileTreeFactory treeFactory = new FileTreeFactory();
        ArrayList<Path> allMatching = new ArrayList<>();
        Files.walk(path, FileVisitOption.FOLLOW_LINKS).forEach(p -> {
           if (p.toString().matches(regex) || p.getFileName().toString().matches(regex)) {
                allMatching.add(path.getFileName().resolve(path.relativize(p)));
           } 
        });
        return treeFactory.makeFileTree(allMatching);
    }

    /**
     * Prints out the contents of a FileTree until a specific depth
     * @param tree the FileTree whose contents are going to be printed out
     * @param depth the number of layers of the FileTree to print out
     * @param charset the charset to use when printing out the FileTree
     */
    public void depthList(FileTree tree, int depth, CharsetEnum charset) {
        (new FileTreeWalker(tree, depth, charset)).listFiles();
    }

    /**
     * Searches for a file within a FileTree and prints out a single-branched tree containing the found file and its
     * parent directories relative to the Path of the head of the FileTree being searched
     * @param tree the FileTree being searched
     * @param toSearch the Path of the file being searched
     * @param charset the charset to use when printing out the single-branched tree
     * @throws FileNotFoundException if the file being searched for does not exist
     */
    public void searchList(FileTree tree, Path toSearch, CharsetEnum charset) throws FileNotFoundException {
        (new FileTreeSearcher(tree, toSearch, charset)).printSearch();
    }
}
