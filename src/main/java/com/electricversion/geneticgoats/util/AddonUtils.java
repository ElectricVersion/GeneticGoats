package com.electricversion.geneticgoats.util;

public class AddonUtils {
    public static int getDigit(int number, int index) {
        return (int) (number / Math.pow(10, index)) % 10;
    }
}
