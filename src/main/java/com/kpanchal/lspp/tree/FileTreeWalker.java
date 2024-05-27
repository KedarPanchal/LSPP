package com.kpanchal.lspp.tree;

import com.kpanchal.lspp.args.CharsetEnum;

/**
 * A class that traverses through a FileTree and prints its contents in the form of a tree
 * @author Kedar Panchal
 */
public class FileTreeWalker {
    // The head of the FileTree being traversed
    private FileTree.FileTreeNode head;
    // The charset to use when printing out the FileTree
    private final CharsetEnum charset;


    /**
     * Constructs a FileTreeWalker that traverses and prints the contents of a FileTree
     * @param tree the FileTree to traverse
     * @param charset the charset to use when printing out the FileTree
     */
    public FileTreeWalker(FileTree tree, CharsetEnum charset) {
        this.head = tree.getHead();
        this.charset = charset;
    }

    /**
     * Prints out all the file names of the files contained by the FileTree being traversed
     */
    public void listFiles() {
        listFiles(this.head, "");
        System.out.println();
    }

    /**
     * Recursively traverses the FileTree being traversed by this FileTreeWalker to print out the file names of the
     * files contained by said FileTree
     * @param treeHead the current head of the FileTree as its being traversed
     * @param symbol the current combination of charset parts (connectors, pipes, and tails) that will be printed out in
     *               the next line of the FileTree
     */
    private void listFiles(FileTree.FileTreeNode treeHead, String symbol) {
        if (treeHead == null) {
            return;
        }

        if (!symbol.isEmpty()) {
            // Replaces redundant charset parts and newlines in the line of the FileTree that needs to be printed with
            // the appropriate number of charset parts and newlines
            System.out.print(symbol.substring(0, symbol.length() - 4).replace(this.charset.getConnector(), this.charset.getPipe()).replace(this.charset.getTail(), "    ").replaceAll("\n+", "\n") + symbol.substring(symbol.length() - 4) + treeHead);
        } else {
            System.out.print(treeHead);
        }

        for (FileTree.FileTreeNode child : treeHead.getChildren()) {
            // Checks whether a tail charset part or a connector charset part needs to be used for the next node being
            // printed
            if (child.equals(treeHead.getChildren().getLast())) {
                this.listFiles(child, "\n" + symbol + this.charset.getTail());
            } else {
                this.listFiles(child, "\n" + symbol + this.charset.getConnector());
            }
        }
    }
}