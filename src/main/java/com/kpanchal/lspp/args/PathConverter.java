package com.kpanchal.lspp.args;

import java.nio.file.Path;

import com.beust.jcommander.IStringConverter;

public class PathConverter implements IStringConverter<Path> {
    @Override
    public Path convert(String value) {
        return Path.of(value);
    }
}