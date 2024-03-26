package com.kpanchal.lspp.args;

public enum CharsetEnum {
    BOX(new String[]{"│   ", "├── ", "└── "}),
    ASCII(new String[]{"|   ", "+-- ", "\\-- "});

    private final String[] chars;

    private CharsetEnum(String[] chars) {
        this.chars = chars;
    }

    public String getPipe() {
        return this.chars[0];
    }

    public String getConnector() {
        return this.chars[1];
    }

    public String getTail() {
        return this.chars[2];
    }
}