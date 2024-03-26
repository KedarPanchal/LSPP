package com.kpanchal.lspp.args;

import com.beust.jcommander.IStringConverter;

public class CharsetConverter implements IStringConverter<CharsetEnum> {
    @Override
    public CharsetEnum convert(String value) {
        return switch(value.toUpperCase()) {
            case "ASCII" -> CharsetEnum.ASCII;
            default -> CharsetEnum.BOX;
        };
    }
}