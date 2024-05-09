package com.kpanchal.lspp.tree;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * A class that constructs FileTrees
 */
public class FileTreeFactory {
    /**
     * Creates an empty FileTree
     * @return an empty FileTree
     */
    public FileTree makeFileTree() {
        return new FileTree();
    }

    /**
     * Creates a FileTree containing a single root/head node
     * @param head the root/head node of the created FileTree
     * @return a FileTree with a single root/head node
     */
    public FileTree makeFileTree(FileTree.FileTreeNode head) {
        return new FileTree(head);
    }

    /**
     * Constructs a FileTree containing a list of Paths and their parent directories
     * @param paths the list of paths to add to the FileTree
     * @return a FileTree containing those paths and their parent directories
     */
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
