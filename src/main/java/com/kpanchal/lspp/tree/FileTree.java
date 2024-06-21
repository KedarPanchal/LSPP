package com.kpanchal.lspp.tree;

import java.util.LinkedHashSet;
import java.nio.file.Path;

/**
 * A tree-style data structure for keeping track of directories and their contents
 * @author Kedar Panchal
 */
public class FileTree {
    // The root node of the FileTree
    private FileTree.FileTreeNode head;

    /**
     * Constructs an empty FileTree
     */
    FileTree() {
        this.head = null;
    }

    /**
     * Constructs a FileTree with a root node
     * @param head the root/head node of the FileTree
     */
    FileTree(FileTree.FileTreeNode head) {
        this.head = head;
    }

    /**
     * Gets the root/head node of the FileTree
     * @return the root/head node of the FileTree
     */
    public FileTree.FileTreeNode getHead() {
        return this.head;
    }

    /**
     * Adds a node to the FileTree. If the FileTree is empty, the added node becomes the head node
     * @param toAdd the Path to add to the FileTree
     */
    public void add(Path toAdd) {
        if (this.head == null) {
            this.head = new FileTree.FileTreeNode(toAdd);
        } else {
            this.add(toAdd, head);
        }
    }

    /**
     * Adds a node to the FileTree by recursively searching for a node containing its parent directory
     * and adding a child node to that node
     * @param toAdd the Path to add to the FileTree
     * @param current the current node the search algorithm is on
     * @return whether toAdd can be a child of the current node or not
     */
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

    /**
     * A node of the FileTree
     * @author Kedar Panchal
     */
    public static class FileTreeNode {
        // The Path stored by this node
        private final Path contents;
        // The parent node of this node
        private FileTreeNode parent;
        // A set of all the children nodes of this node
        private final LinkedHashSet<FileTreeNode> children;

        /**
         * Constructs a FileTreeNode containing a given Path
         * @param path the path stored by the created FileTreeNode
         */
        private FileTreeNode(Path path) {
            this.contents = path;
            this.parent = null;
            this.children = new LinkedHashSet<>();
        }

        /**
         * Gets the Path stored by this FileTreeNode
         * @return the Path stored by this FileTreeNode
         */
        public Path getPath() {
            return this.contents;
        }

        /**
         * Gets the parent node of this FileTreeNode
         * @return the parent node of this FileTreeNode
         */
        public FileTreeNode getParent() {
            return this.parent;
        }

        /**
         * Sets the parent node of this FileTreeNode
         * @param parent the node that will become the parent of this FileTreeNode
         */
        public void setParent(FileTreeNode parent) {
            this.parent = parent;
        }

        /**
         * Returns whether this FileTreeNode has children or not
         * @return whether this FileTreeNode has any child nodes
         */
        public boolean childless() { // FREEDOM
            return this.children.isEmpty();
        }

        /**
         * Checks whether a node is a child of this FileTreeNode
         * @param child the node to search for in this FileTreeNode's children
         * @return whether child is a child of this FileTreeNode
         */
        private boolean hasChild(FileTreeNode child) { // Is this a Maury Povich moment?
            for (FileTreeNode kid : this.children) {
                if (kid.toString().equals(child.toString())) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns the children nodes of this FileTreeNode
         * @return a LinkedHashSet containing the children nodes of this FileTreeNode
         */
        public LinkedHashSet<FileTreeNode> getChildren() {
            return this.children;
        }

        /**
         * Adds a child node to this FileTreeNode
         * @param child the node that will become a child of this FileTreeNode
         */
        public void addChild(FileTreeNode child) {
            if (!this.hasChild(child)) {
                this.children.add(child);
            }
        }

        /**
         * Returns a String representation of the Path contained by this FileTreeNode
         * @return a String representation of the Path contained by this FileTreeNode
         */
        @Override
        public String toString() {
            return this.contents.getFileName().toString();
        }

        /**
         * Checks two FileTreeNodes' equality by comparing the Paths contained by them
         * @param o the other FileTreeNode to compare this one too
         * @return whether both FileTreeNodes are equal or not
         */
        @Override
        public boolean equals(Object o) {
            return this.contents.equals(((FileTree.FileTreeNode) o).contents);
        }
    }
}