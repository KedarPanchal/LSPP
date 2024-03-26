package com.kpanchal.lspp.args;

import java.nio.file.Path;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(converter=PathConverter.class)
    private Path directory = Path.of(System.getProperty("user.dir"));

    @Parameter(names={"-d", "--depth"})
    private Integer depth = null;

    @Parameter(names={"-s", "--search"})
    private String fileName;

    @Parameter(names={"-c", "--charset"}, converter=CharsetConverter.class)
    private CharsetEnum charset = CharsetEnum.BOX;

    @Parameter(names={"-h", "--help"}, help=true)
    private boolean help = false;

    @Parameter(names={"-v", "--version"})
    private boolean version = false;

    public Path getPath() {
        return this.directory;
    }

    public Integer getDepth() {
        return this.depth;
    }

    public String getFileName() {
        return this.fileName;
    }

    public CharsetEnum getCharset() {
        return this.charset;
    }

    public boolean getHelp() {
        return this.help;
    }

    public boolean getVersion() {
        return this.version;
    }
}