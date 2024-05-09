package com.kpanchal.lspp.tree;

import java.util.LinkedHashSet;
import java.nio.file.Path;

public class FileTree {
    private FileTree.FileTreeNode head;
    private int depth;

    FileTree() {
        this.head = null;
        this.depth = 0;
    }

    FileTree(FileTree.FileTreeNode head) {
        this.head = head;
        this.depth = 1;
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
            FileTreeNode nodeToAdd = new FileTreeNode(toAdd);
            nodeToAdd.setParent(current);
            current.addChild(nodeToAdd);
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
            return 1 + head.getChildren().stream().map(this::calculateDepth).max(Integer::compare).get();
        } 
    }

    public static class FileTreeNode {
        // The Path stored by this node
        private final Path contents;
        // The parent node of this node
        private FileTreeNode parent;
        // A set of all the children nodes of this node
        private final LinkedHashSet<FileTreeNode> children;

        private FileTreeNode(Path path) {
            this.contents = path;
            this.parent = null;
            this.children = new LinkedHashSet<>();
        }

        public Path getPath() {
            return this.contents;
        }

        public FileTreeNode getParent() {
            return this.parent;
        }

        public void setParent(FileTreeNode parent) {
            this.parent = parent;
        }

        public boolean childless() { // FREEDOM
            return this.children.isEmpty();
        }

        private boolean hasChild(FileTreeNode child) {
            for (FileTreeNode kid : this.children) {
                if (kid.toString().equals(child.toString())) {
                    return true;
                }
            }
            return false;
        }

        public LinkedHashSet<FileTreeNode> getChildren() {
            return this.children;
        }

        public void addChild(FileTreeNode child) {
            if (!this.hasChild(child)) {
                this.children.add(child);
            }
        }

        @Override
        public String toString() {
            return this.contents.getFileName().toString();
        }

        @Override
        public boolean equals(Object o) {
            return this.contents.equals(((FileTree.FileTreeNode) o).contents);
        }
    }
}