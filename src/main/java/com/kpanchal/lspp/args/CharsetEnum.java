package com.kpanchal.lspp.args;

/**
 * An enum containing all the charsets used by the {@code lspp} command
 * @author Kedar Panchal
 */
public enum CharsetEnum {
    // The BOX charset (is UTF-8, will not work on Windows terminals by default)
    BOX(new String[]{"│   ", "├── ", "└── "}),
    // The ROUND charset (is UTF-8, will not work on Windows terminals by default)
    ROUND(new String[]{"│   ", "├── ", "╰── "}),
    // The TUBE charset (is UTF-8, will not work on Windows terminals by default)
    TUBE(new String[]{"║   ", "╠══ ", "╚══ "}),
    // the ASCII charset (works on all terminals, and is the default charset used by lspp)
    ASCII(new String[]{"|   ", "+-- ", "\\-- "});

    // The characters contained by each CharsetEnum instance. Should contain only 3 elements
    private final String[] chars;

    /**
     * Constructs a CharsetEnum
     * @param chars the three different parts of an {@code lspp} charset: the pipe, the connector, and the tail. The
     *              pipe is the charset part that bridges connectors and tails, the connector is the charset part that
     *              connects pipes and other connectors to nodes, and tails are the charset part that signify the last
     *              child node of a specific branch in the FileTree
     */
    private CharsetEnum(String[] chars) {
        this.chars = chars;
    }

    /**
     * Gets the pipe part of a charset, which bridges charset connectors and tails
     * @return the pipe part of the charset
     */
    public String getPipe() {
        return this.chars[0];
    }

    /**
     * Gets the connector part of a charset, which precedes a node being printed in a FileTree and connects with pipes
     * and other connectors
     * @return the connector part of the charset
     */
    public String getConnector() {
        return this.chars[1];
    }

    /**
     * Gets the tail part of a charset, which precedes the last child node of a branch in the FileTree
     * @return the tail part of the charset
     */
    public String getTail() {
        return this.chars[2];
    }
}