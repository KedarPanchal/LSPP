package com.kpanchal.lspp.tree;

import java.util.LinkedHashSet;
import java.nio.file.Path;

public class FileTree {
    private FileTree.FileTreeNode head;
    private int depth;

    public FileTree() {
        this.head = null;
        this.depth = 0;
    }

    public FileTree(FileTree.FileTreeNode head) {
        this.head = head;
        this.depth = 0;
    }

    public FileTree.FileTreeNode getHead() {
        return this.head;
    }

    public int getDepth() {
        return this.depth;
    }

    public void add(Path toAdd) {
        if (this.head == null) {
            this.head = new FileTree.FileTreeNode(toAdd);
        } else {
            this.add(toAdd, head);
        }
        this.depth = this.calculateDepth(head);
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

    private int calculateDepth(FileTree.FileTreeNode head) {
        if (head.childless()) {
            return 1;
        } else {
            return 1 + head.getChildren().stream().map(child -> calculateDepth(child)).max(Integer::compare).get();
        } 
    }

    public static class FileTreeNode {
        private Path contents;
        private LinkedHashSet<FileTreeNode> children;

        private FileTreeNode(Path path) {
            this.contents = path;
            this.children = new LinkedHashSet<>();
        }

        public Path getPath() {
            return this.contents;
        }

        public boolean childless() { // FREEDOM
            return this.children.isEmpty();
        }

        public int getChildrenCount() {
            return this.children.size();
        }

        public LinkedHashSet<FileTreeNode> getChildren() {
            return this.children;
        }

        public void addChild(FileTreeNode child) {
            this.children.add(child);
        }

        public String toString() {
            return this.contents.getFileName().toString();
        }

        @Override
        public boolean equals(Object o) {
            return this.contents.equals(((FileTree.FileTreeNode) o).contents);
        }
    }
}