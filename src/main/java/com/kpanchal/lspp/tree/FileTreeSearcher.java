package com.kpanchal.lspp.tree;

import java.util.Queue;
import java.util.LinkedList;

import java.nio.file.Path;

import com.kpanchal.lspp.args.CharsetEnum;

public class FileTreeSearcher {
    private FileTree.FileTreeNode head;
    private Path toSearch;
    // The charset to use when printing out the single-branched tree
    private final CharsetEnum charset;

    public FileTreeSearcher(FileTree tree, Path path, CharsetEnum charset) {
        this.head = tree.getHead();
        this.toSearch = path.getFileName();
        this.charset = charset;
    }

    public void printSearch() {
        LinkedList<FileTree.FileTreeNode> traversalPath = this.getTraversalPath(this.search(this.head));
        if (traversalPath == null) {
            System.err.println("Error: File not found");
            return;
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
