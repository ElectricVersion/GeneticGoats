package com.electricversion.geneticgoats.model.modeldata;

import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import net.minecraft.util.Mth;

import static net.minecraft.util.Mth.clamp;

// The phenotype class is used for storing model data related to immutable genetic traits that need to be accessed by the model
public class GoatPhenotype implements Phenotype {
    public EarLength getEarLength() {
        return earLength;
    }

    public float getEarFlop() {
        return earFlop;
    }

    public float getEarX() {
        return earX;
    }

    public float getEarXRot() {
        return earXRot;
    }

    public float getEarZ() {
        return earZ;
    }

    public float getEarY() {
        return earY;
    }

    public enum EarLength {
        SMALL,
        NORMAL,
        LONG1,
        LONG2,
        LONG3,
    }

    private EarLength earLength;
    private float earFlop; //Ear angle from -1 (upwards) to 1 (lopped)
    private float earXRot;
    private float earX;
    private float earY;
    private float earZ;

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
        earFlop = ((Math.max(gene[14], gene[15]) * 2) - 7) / 5F;
        earXRot = 0;
        earY = 0F;
        earZ = -1.05F;

        switch (earLength) {
            case SMALL -> {
                earFlop = (earFlop * 0.7F) - 0.1F;
                if (earFlop > 0.25F) {
                    earXRot = 1F;
                    earY += 1F;
                    earZ -= 1F;
                }
                else if (earFlop > -0.25F) {
                    earXRot = 0.5F;
                    earY += 1F;
                    earZ -= 1F;
                }
            }
            case NORMAL -> {
                earFlop = (earFlop * 0.7F) - 0.1F;
                if (earFlop > 0.25F) {
                    earXRot = 1F;
                    earY += 0F;
                    earZ -= 2F;
                }
                else if (earFlop > -0.25F) {
                    earXRot = 0.5F;
                    earY += 0F;
                    earZ -= 1F;
                }
            }
            case LONG1 -> {
                earFlop = (earFlop * 0.2F) + 0.6F;
            }
            case LONG2 -> {
                earFlop = (earFlop * 0.1F) + 0.7F;
            }
            case LONG3 -> {
                earFlop = (earFlop * 0.1F) + 0.8F;
            }
        }

        earXRot *= Mth.HALF_PI;
        earX = earFlop < -0.5F ? 1F : earFlop < 0F ? 2F : 3F;
        earFlop = clamp(earFlop, -1, 1) * Mth.HALF_PI;
    }
}
