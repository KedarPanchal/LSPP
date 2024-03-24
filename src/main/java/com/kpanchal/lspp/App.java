package com.kpanchal.lspp;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.kpanchal.lspp.tree.FileTree;;

public class App {
    public static void main(String[] args) throws IOException {
        FileTree tree = new FileTree();
        Files.walk(Paths.get("C:/Users/astha/Documents/code/lspp/src"), FileVisitOption.FOLLOW_LINKS).forEach(path -> {
            tree.add(path);
        });
    }
}