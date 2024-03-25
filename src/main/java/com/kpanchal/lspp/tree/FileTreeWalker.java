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
        if (depth == 0) {
            return;
        }

        if (!symbol.isEmpty()) {
            System.out.print(symbol.substring(0, symbol.length() - 4).replace("├── ", "│   ").replace("└── ", "    ") + symbol.substring(symbol.length() - 4) + treeHead.toString());
        } else {
            System.out.print(treeHead);
        }

        for (FileTree.FileTreeNode child : treeHead.getChildren()) {
            System.out.println();
            if (child.equals(treeHead.getChildren().getLast())) {
                this.listFiles(child, depth - 1, symbol + "└── ");
            }  else {
                this.listFiles(child, depth - 1, symbol + "├── ");
            }
        }
    }
}
