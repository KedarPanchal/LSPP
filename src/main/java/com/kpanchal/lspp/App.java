package com.kpanchal.lspp;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {
        Files.walk(Paths.get("C:/Users/astha/Documents/code/lspp/src"), FileVisitOption.FOLLOW_LINKS).forEach(path -> System.out.println(path));
    }
}