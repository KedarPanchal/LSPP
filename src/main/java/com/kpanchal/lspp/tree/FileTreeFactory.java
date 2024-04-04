package com.kpanchal.lspp.tree;

import java.nio.file.Path;
import java.util.ArrayList;

public class FileTreeFactory {
    
    public FileTree makeFileTree() {
        return new FileTree();
    }

    public FileTree makeFileTree(FileTree.FileTreeNode head) {
        return new FileTree(head);
    }

    public FileTree makeFileTree(ArrayList<Path> paths) {
        FileTree ret = new FileTree();
        for (Path path : paths) {
            for (int i = 1; i <= path.getNameCount(); i++) {
                ret.add(path.subpath(0, i));
            }
        }
        return ret;
    }
}
