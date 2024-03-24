package com.kpanchal.lspp.tree;

import java.util.HashSet;
import java.nio.file.Path;

public class FileTree {
    private FileTree.FileTreeNode head;

    public FileTree() {
        this.head = null;
    }

    public FileTree.FileTreeNode getHead() {
        return this.head;
    }

    public void add(Path toAdd) {
        if (this.head == null) {
            this.head = new FileTree.FileTreeNode(toAdd);
        } else {
            this.add(toAdd, head);
        }
    }

    private boolean add(Path toAdd, FileTree.FileTreeNode current) {
        if (current.getPath().equals(toAdd.getParent())) {
            current.addChild(new FileTreeNode(toAdd));
            return true;
        } else {
            for (FileTree.FileTreeNode child : current.getChildren()) {
                if (this.add(toAdd, child)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class FileTreeNode {
        private Path contents;
        private HashSet<FileTreeNode> children;

        public FileTreeNode(Path path) {
            this.contents = path;
            this.children = new HashSet<>();
        }

        public Path getPath() {
            return this.contents;
        }

        public HashSet<FileTreeNode> getChildren() {
            return this.children;
        }

        public void addChild(FileTreeNode child) {
            this.children.add(child);
        }

        public String toString() {
            return this.contents.toString();
        }
    }
}
