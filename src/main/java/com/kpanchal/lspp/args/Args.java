package com.kpanchal.lspp.args;

import java.nio.file.Path;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(description="The name of the file to list all subfiles and subdirectories from. If left blank, this is the current working directory.", converter=PathConverter.class)
    private Path directory = Path.of(System.getProperty("user.dir"));

    @Parameter(names={"-d", "--depth"}, description="The number of layers of files that will be listed. If left blank or given a value of 0, all subfiles and subdirectories will be listed.", validateWith=DepthValidator.class)
    private Integer depth = null;

    @Parameter(names={"-s", "--search"}, description="The file to search for. Only the file's parent directories will be displayed.")
    private String fileName = null;

    @Parameter(names={"-a", "--search-all"}, description="The regular expression pattern used to search for files.")
    private String regex = null;

    @Parameter(names={"-c", "--charset"}, description="The character set to use when displaying the tree.", converter=CharsetConverter.class)
    private CharsetEnum charset = CharsetEnum.BOX;

    @Parameter(names={"-h", "--help"}, description="Lists the functionality of each of this program's parameters.", help=true)
    private boolean help = false;

    @Parameter(names={"-v", "--version"}, description="Prints the version of this program.")
    private boolean version;

    public Path getPath() {
        return this.directory;
    }

    public Integer getDepth() {
        return this.depth;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getRegex() {
        return this.regex;
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