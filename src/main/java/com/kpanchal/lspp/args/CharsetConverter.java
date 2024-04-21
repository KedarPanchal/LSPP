package com.kpanchal.lspp.args;

import picocli.CommandLine.ITypeConverter;

public class CharsetConverter implements ITypeConverter<CharsetEnum> {
    public CharsetEnum convert(String value) throws Exception {
        switch (value.toLowerCase()) {
            case "box":
                return CharsetEnum.BOX;
            default:
                return CharsetEnum.ASCII;
        }
    }
}
