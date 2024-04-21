package com.kpanchal.lspp.args;

import java.nio.file.Path;

import picocli.CommandLine.ITypeConverter;

public class PathConverter implements ITypeConverter<Path> {
    public Path convert(String value) throws Exception {
        if (value.isBlank()) {
            return Path.of(System.getProperty("user.dir"));
        } else {
            return Path.of(value);
        }
    }
}
