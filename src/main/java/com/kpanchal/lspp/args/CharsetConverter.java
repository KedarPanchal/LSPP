package com.kpanchal.lspp.args;

/**
 * A class for converting a String command-line input into a CharsetEnum for usage in the {@code lspp} command
 * @author Kedar Panchal
 */
public class CharsetConverter {
    /**
     * Converts a String command-line input into a CharsetEnum for usage in the {@code lspp} command.<br>Valid values
     * are (case-insensitive):
     * <ul>
     *     <li>box -- BOX charset</li>
     *     <li>round -- ROUND charset</li>
     *     <li>tube, tubular, totally-tubular, toob, toobyoolar, totalee-toobyoolar - TUBE charset</li>
     *     <li>ascii -- ASCII charset (also the default value)</li>
     *  </ul>
     * @param value the command-line argument String value that will be converted into a CharsetEnum
     * @return the CharsetEnum equivalent of the String command-line argument
     * @throws Exception if {@code lspp} cannot recognize the parameter
     */
    public static CharsetEnum convert(String value) {
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
