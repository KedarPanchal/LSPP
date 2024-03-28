package com.kpanchal.lspp;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;

import com.beust.jcommander.JCommander;
import com.kpanchal.lspp.args.Args;
import com.kpanchal.lspp.args.CharsetEnum;
import com.kpanchal.lspp.tree.FileTree;
import com.kpanchal.lspp.tree.FileTreeSearcher;
import com.kpanchal.lspp.tree.FileTreeWalker;

public class App {
    private static final String VERSION = "1.0.0-SNAPSHOT";
    public static void main(String[] args) throws IOException {
        Args arguments = new Args();
        JCommander commander = JCommander.newBuilder()
            .addObject(arguments)
            .build();
        commander.parse(args);
            
        if (arguments.getHelp()) {
            commander.usage();
        } else if (arguments.getVersion()) {
            printVersion();
        } else if (arguments.getDepth() != null && arguments.getDepth() > 0) {
            FileTree tree = buildFileTree(arguments.getPath());
            depthList(tree, arguments.getDepth(), arguments.getCharset());
        } else if (arguments.getFileName() != null) {
            FileTree tree = buildFileTree(arguments.getPath());
            searchList(tree, Path.of(arguments.getFileName()), arguments.getCharset());
        } else {
            FileTree tree = buildFileTree(arguments.getPath());
            depthList(tree, tree.getDepth(), arguments.getCharset());
        }
    }

    public static void printVersion() {
        System.out.println("ls++ v" + VERSION + " by Kedar Panchal");
    }

    public static FileTree buildFileTree(Path path) throws IOException {
        FileTree tree = new FileTree();
        Files.walk(path, FileVisitOption.FOLLOW_LINKS).forEach(p -> tree.add(p));
        return tree;
    }

    public static void depthList(FileTree tree, int depth, CharsetEnum charset) {
        (new FileTreeWalker(tree, depth, charset)).listFiles();
    }

    public static void searchList(FileTree tree, Path toSearch, CharsetEnum charset) {
        (new FileTreeSearcher(tree, toSearch, charset)).printSearch();
    }
}