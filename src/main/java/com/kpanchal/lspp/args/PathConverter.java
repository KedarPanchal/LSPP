package com.kpanchal.lspp.args;

import java.nio.file.Path;

import picocli.CommandLine.ITypeConverter;

/**
 * A class for converting a String command-line input into a Path for usage in the {@code lspp} command
 * @author Kedar Panchal
 */
public class PathConverter implements ITypeConverter<Path> {
    /**
     * Converts a String into a Path for usage in the {@code lspp} command
     * @param value the command-line argument String value that will be converted into a Path. If this value is blank,
     *              then the Path of the current working directory is returned
     * @return the Path equivalent of the String command-line argument
     * @throws Exception if the String value cannot be converted into a Path, or if {@code lspp} cannot recognize the
     *                   parameter
     */
    public Path convert(String value) throws Exception {
        if (value.isBlank()) {
            return Path.of(System.getProperty("user.dir"));
        } else {
            return Path.of(value);
        }
    }
}
