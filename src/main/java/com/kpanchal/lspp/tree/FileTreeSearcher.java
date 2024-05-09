package com.kpanchal.lspp.tree;

import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.LinkedList;

import java.nio.file.Path;

import com.kpanchal.lspp.args.CharsetEnum;

/**
 * A class that searches for a Path contained inside a FileTree with the purpose of displaying the found Path and its
 * parent directories in the form of a single-branched tree
 * @author Kedar Panchal
 */
public class FileTreeSearcher {
    // The root/head node of the FileTree that is being searched
    private FileTree.FileTreeNode head;
    // The Path to search within the FileTree
    private Path toSearch;
    // The charset to use when printing out the single-branched tree
    private final CharsetEnum charset;

    /**
     * Constructs a FileTreeSearcher looking for a specified Path within a FileTree
     * @param tree the tree to search for the Path
     * @param path the Path to search for within the tree
     * @param charset the charset to use when printing out the single-branched tree
     */
    public FileTreeSearcher(FileTree tree, Path path, CharsetEnum charset) {
        this.head = tree.getHead();
        this.toSearch = path.getFileName();
        this.charset = charset;
    }

    /**
     * Prints out a single-branched tree of the Path searched for by this FileTreeSearcher. This tree contains all the
     * parent directories of the Path relative to the directory being searched by the {@code lspp} command.
     * @throws FileNotFoundException if the file being searched by this FileTreeSearcher does not exist
     */
    public void printSearch() throws FileNotFoundException {
        LinkedList<FileTree.FileTreeNode> traversalPath = this.getTraversalPath(this.search(this.head));
        if (traversalPath == null) {
            throw new FileNotFoundException("Error: file not found");
        }

        for (int i = 0; i < traversalPath.size(); i++) {
            StringBuilder start = new StringBuilder();
            for (int j = 0; j < i; j++) {
                if (j == i - 1) {
                    start.append(this.charset.getTail());
                } else {
                    start.append("    ");
                }
            }
            System.out.println(start.toString() + traversalPath.get(i));
        }
    }

    /**
     * Traverses a FileTree to search for the Path being searched for by this FileTreeSearcher
     * @param root the current node of the FileTree being traversed
     * @return the node if it is present in the FileTree, or null if otherwise
     */
    public FileTree.FileTreeNode search(FileTree.FileTreeNode root) {
        Queue<FileTree.FileTreeNode> explored = new LinkedList<>();
        explored.add(this.head);

        while (!explored.isEmpty()) {
            FileTree.FileTreeNode node = explored.poll();
            if (toSearch.equals(node.getPath().getFileName())) {
                return node;
            }

            explored.addAll(node.getChildren());
        }
        return null;
    }

    /**
     * Generates a list containing the Path being searched for and its parent directories relative to the directory
     * being searched by the {@code lspp} command
     * @param tail the current node being added to the list
     * @return a LinkedList containing the Path being searched for and its parent directories
     *         relative to the directory being searched by the {@code lspp} command
     */
    private LinkedList<FileTree.FileTreeNode> getTraversalPath(FileTree.FileTreeNode tail) {
        if (tail == null) {
            return null;
        }

        LinkedList<FileTree.FileTreeNode> ret = new LinkedList<>();
        FileTree.FileTreeNode current = tail;
        ret.add(current);

        while (current.getParent() != null) {
            current = current.getParent();
            ret.addFirst(current);
        }
        return ret;
    }
}