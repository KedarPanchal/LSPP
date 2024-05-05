package com.kpanchal.lspp;

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

@Command(name="lspp", version="lspp 1.0", description="Lists the files in a folder in a tree-style output\n\nWritten by Kedar Panchal\n", sortOptions=false)
public class App implements Callable<Integer> {
    @Spec CommandSpec spec;

    @Parameters(index = "0", defaultValue="", paramLabel="DIRECTORY", converter=PathConverter.class, description="The directory to list files in. If none is specified, then the current working director's contents are listed")
    private Path directory;

    private int depth;
    @Option(names={"-d", "--depth"}, defaultValue="0", description="The depth of the files to list in a tree")
    public void setDepth(int value) {
        if (value < 0) {
            throw new ParameterException(spec.commandLine(), String.format("Invalid value '%s' for option '--depth': value is not a positive integer", value));
        } else {
            this.depth = value;
        }
    }

    @Option(names={"-s", "--search"}, description="The file to search for. Only the file's parent directories will be displayed")
    private String fileName;

    @Option(names={"-a", "--search-all"}, description="The regular expression pattern used to search for files")
    private String regex;

    @Option(names={"-c", "--charset"}, defaultValue="ascii", converter=CharsetConverter.class, description="The charset to use when displaying the file tree. Valid values (case-insensitive): ${COMPLETION-CANDIDATES}")
    private CharsetEnum charset;

    @Option(names={"-v", "--version"}, versionHelp=true, description="Outputs the version of the program")
    private boolean version;

    @Option(names={"-h", "--help"}, usageHelp=true, description="Display usage information")
    private boolean help;

    @Override
    public Integer call() throws Exception {
        validate();

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

    public FileTree buildFileTree(Path path) throws IOException {
        FileTreeFactory treeFactory = new FileTreeFactory();
        FileTree tree = treeFactory.makeFileTree();
        Files.walk(path, FileVisitOption.FOLLOW_LINKS).forEach(p -> tree.add(p));
        return tree;
    }

    public FileTree buildFilteredTree(Path path, String regex) throws IOException {
        FileTreeFactory treeFactory = new FileTreeFactory();
        ArrayList<Path> allMatching = new ArrayList<>();
        Files.walk(path, FileVisitOption.FOLLOW_LINKS).forEach(p -> {
           if (p.toString().matches(regex) || p.getFileName().toString().matches(regex)) {
                allMatching.add(path.getFileName().resolve(path.relativize(p)));
           } 
        });
        return treeFactory.makeFileTree(allMatching);
    }

    public void depthList(FileTree tree, int depth, CharsetEnum charset) {
        (new FileTreeWalker(tree, depth, charset)).listFiles();
    }

    public void searchList(FileTree tree, Path toSearch, CharsetEnum charset) {
        (new FileTreeSearcher(tree, toSearch, charset)).printSearch();
    }
}
