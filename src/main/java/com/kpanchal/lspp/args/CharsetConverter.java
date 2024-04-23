package com.kpanchal.lspp.args;

import picocli.CommandLine.ITypeConverter;

public class CharsetConverter implements ITypeConverter<CharsetEnum> {
    public CharsetEnum convert(String value) throws Exception {
        switch (value.toLowerCase()) {
            case "box":
                return CharsetEnum.BOX;
            case "round":
                return CharsetEnum.ROUND;
            case "tube":
            case "tubular":
            case "totally-tubular":
            case "toob":
            case "toobyoolar":
            case "totalee-toobyoolar": // I got a little carried away
                return CharsetEnum.TUBE;
            default:
                return CharsetEnum.ASCII;
        }
    }
}
