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
}
