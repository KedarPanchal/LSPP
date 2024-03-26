package com.kpanchal.lspp.tree;

import com.kpanchal.lspp.args.CharsetEnum;

public class FileTreeWalker {
    private FileTree.FileTreeNode head;
    private CharsetEnum charset;
    private int toDepth;

    public FileTreeWalker(FileTree tree, int depth, CharsetEnum charset) {
        this.head = tree.getHead();
        this.toDepth = depth;
        this.charset = charset;
    }

    public FileTreeWalker(FileTree tree, CharsetEnum charset) {
        this.head = tree.getHead();
        this.toDepth = tree.getDepth();
        this.charset = charset;
    }

    public void listFiles() {
        listFiles(this.head, this.toDepth, "");
    }

    private void listFiles(FileTree.FileTreeNode treeHead, int depth, String symbol) {
        if (depth == 0) {
            return;
        }

        if (!symbol.isEmpty()) {
            System.out.print(symbol.substring(0, symbol.length() - 4).replace(this.charset.getConnector(), this.charset.getPipe()).replace(this.charset.getTail(), "    ").replaceAll("\n+", "\n") + symbol.substring(symbol.length() - 4) + treeHead.toString());
        } else {
            System.out.print(treeHead);
        }

        for (FileTree.FileTreeNode child : treeHead.getChildren()) {
            if (child.equals(treeHead.getChildren().getLast())) {
                this.listFiles(child, depth - 1, "\n" + symbol + this.charset.getTail());
            } else {
                this.listFiles(child, depth - 1, "\n" + symbol + this.charset.getConnector());
            }
        }
    }
}
