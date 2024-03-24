package com.kpanchal.lspp.tree;

public class FileTreeWalker {
    private FileTree.FileTreeNode head;
    private int toDepth;

    public FileTreeWalker(FileTree tree, int depth) {
        this.head = tree.getHead();
        this.toDepth = depth;
    }

    public FileTreeWalker(FileTree tree) {
        this.head = tree.getHead();
        this.toDepth = tree.getDepth();
    }

    public void listFiles() {
        listFiles(this.head, this.toDepth, "");
    }

    private void listFiles(FileTree.FileTreeNode treeHead, int depth, String symbol) {
        for (int i = this.toDepth - 1; i > depth; i--) {
            System.out.print("    ");
        }
        System.out.print(symbol + treeHead.toString());
        for (FileTree.FileTreeNode child : treeHead.getChildren()) {
            String nextSymbol = "├── ";
            if (child.equals(treeHead.getChildren().getLast())) {
                nextSymbol = "└── ";
            }
            System.out.println();
            listFiles(child, depth - 1, nextSymbol);
        }
    }
}
