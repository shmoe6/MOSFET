package com.musicallyanna.mosfet.calendar;

/**
 * Enum representing each of the four rooms in the makerspace.
 * @author Anna Bontempo
 */
public enum MakerspaceRoom {
    IDEATION_SPACE,
    ELECTRONICS_LAB,
    WOOD_SHOP,
    METAL_SHOP;

    /**
     * Converts a {@code MakerspaceRoom} value to a {@code String} and formats it.
     * @param mr the makerspace room to get the name of
     * @return a {@code String} containing the formatted name of the specified {@code MakerspaceRoom}
     */
    public static String toFormattedString(MakerspaceRoom mr) {

        // split value into two processable parts
        String[] s = mr.toString().split("_");

        // keep first letter of each word capitalized, make the rest lowercase
        s[0] = s[0].charAt(0) + s[0].substring(1).toLowerCase();
        s[1] = s[1].charAt(0) + s[1].substring(1).toLowerCase();

        // combine the two formatted strings, separated by a space
        return s[0] + " " + s[1];
    }
}
