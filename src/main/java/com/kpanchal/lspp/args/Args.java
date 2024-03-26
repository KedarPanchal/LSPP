package com.kpanchal.lspp.args;

import java.nio.file.Path;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(converter=PathConverter.class)
    private Path directory = Path.of(System.getProperty("user.dir"));

    @Parameter(names={"-d", "--depth"})
    private int depth = 0;

    @Parameter(names={"-s", "--search"})
    private String fileName;

    @Parameter(names={"-c", "--charset"}, converter=CharsetConverter.class)
    private CharsetEnum charset = CharsetEnum.BOX;

    @Parameter(names={"-h", "--help"}, help=true)
    private boolean help = false;

    @Parameter(names={"-v", "--version"})
    private boolean version = false;
}
