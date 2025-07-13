package com.electricversion.geneticgoats.model.modeldata;

import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import net.minecraft.util.Mth;

import static net.minecraft.util.Mth.clamp;

// The phenotype class is used for storing model data related to immutable genetic traits that need to be accessed by the model
public class GoatPhenotype implements Phenotype {
    public int getEarPlacement() {
        return earPlacement;
    }

    public EarLength getEarLength() {
        return earLength;
    }

    public float getEarFlop() {
        return earFlop;
    }

    public float getEarX() {
        return earX;
    }

    public enum EarLength {
        SMALL,
        NORMAL,
        LONG1,
        LONG2,
        LONG3,
    }

    private EarLength earLength;
    private int earPlacement; //Range 0-2, 0 is highest 2 is lowest
    private float earFlop; //Ear angle from -1 (upwards) to 1 (lopped)
    private float earX;

    public GoatPhenotype(int[] gene, boolean isFemale) {
        earLength = EarLength.SMALL;
        if (gene[16] == 5 || gene[17] == 5) {
            earLength = EarLength.LONG3;
        } else if (gene[16] == 4 || gene[17] == 4) {
            earLength = EarLength.LONG2;
        } else if (gene[16] == 3 || gene[17] == 3) {
            earLength = EarLength.LONG1;
        } else if (gene[16] == 2 || gene[17] == 2) {
            earLength = EarLength.NORMAL;
        }
        earPlacement = 0;
        earFlop = (gene[14] + gene[15] - 7)/5F;

        switch (earLength) {
            case SMALL -> {
                earFlop = (earFlop * 0.8F) - 0.2F;
            }
            case NORMAL -> {
                earFlop = (earFlop * 0.7F) - 0.1F;
            }
            case LONG1 -> {
                earFlop = (earFlop * 0.3F) + 0.5F;
            }
            case LONG2 -> {
                earFlop = (earFlop * 0.2F) + 0.7F;
            }
            case LONG3 -> {
                earFlop = (earFlop * 0.1F) + 0.8F;
            }
        }

        earX = earFlop < -0.5 ? 1F : earFlop < 0 ? 2F : 3F;
        earFlop = clamp(earFlop, -1, 1) * Mth.HALF_PI;
    }
}
