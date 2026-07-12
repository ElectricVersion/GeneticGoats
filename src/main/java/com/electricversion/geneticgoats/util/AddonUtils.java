package com.electricversion.geneticgoats.util;

import net.minecraft.util.Mth;

public class AddonUtils {
    public static int getDigit(int number, int index) {
        return (int) (number / Math.pow(10, index)) % 10;
    }

    // Same as Mth.clamp but defaulting to 0, 1 range
    public static float clamp(float number) {
        return Mth.clamp(number, 0F, 1F);
    }

    // Convert a hexadecimal character into an integer within a certain range. For texture "randomization".
    public static int hexToInt(char uuidChar, int range) {
        return switch (uuidChar) {
            case '1' -> 1 % range;
            case '2' -> 2 % range;
            case '3' -> 3 % range;
            case '4' -> 4 % range;
            case '5' -> 5 % range;
            case '6' -> 6 % range;
            case '7' -> 7 % range;
            case '8' -> 8 % range;
            case '9' -> 9 % range;
            case 'a' -> 10 % range;
            case 'b' -> 11 % range;
            case 'c' -> 12 % range;
            case 'd' -> 13 % range;
            case 'e' -> 14 % range;
            case 'f' -> 15 % range;
            default -> 0;
        };
    }
}
