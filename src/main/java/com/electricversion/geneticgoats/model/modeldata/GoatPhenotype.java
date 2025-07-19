package com.electricversion.geneticgoats.model.modeldata;

import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import net.minecraft.util.Mth;

import static net.minecraft.util.Mth.clamp;

// The phenotype class is used for storing model data related to immutable genetic traits that need to be accessed by the model
public class GoatPhenotype implements Phenotype {
    public EarLength getEarLength() {
        return earLength;
    }

    public float getEarZRot() {
        return earZRot;
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

    public float getEarYRot() {
        return earYRot;
    }

    public float getSmallEarPivotZ() {
        return smallEarPivotZ;
    }

    public enum EarLength {
        SMALL,
        NORMAL,
        LONG1,
        LONG2,
        LONG3,
    }

    private EarLength earLength;
    private float earXRot;
    private float earYRot;
    private float earZRot; //Ear angle from -1 (upwards) to 1 (lopped)
    private float earX;
    private float earY;
    private float earZ;
    private float smallEarPivotZ;

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
        float earForward = ((Math.max(gene[18], gene[19]) * 2) - 2) / 4F;
        float earFlop = ((Math.max(gene[14], gene[15]) * 2) - 2) / 10F;
        earXRot = 0F;
        earYRot = 0F;
        earZRot = (earFlop * 2F) - 1;
        earY = 0F;
        earZ = -1.05F;
        smallEarPivotZ = 0F;

        switch (earLength) {
            case SMALL, NORMAL -> {
                earZRot = (earZRot * 0.8F);
                if (earZRot > 0.5F) {
                    // High Flop
                    earXRot = 1F;
                    earZ -= 1F;
                    smallEarPivotZ = 1F - earForward;
                    if (earForward > 0F) {
                        earXRot += 0.75F * earForward;
                        earYRot += 0.35F * earForward * earFlop;
                        earZRot += 0.1F * earForward;
                    }
                }
                else if (earZRot > -0.25F) {
                    // Medium Flop
                    earXRot = 0.5F;
                    earZ -= 1F;
                    if (earForward > 0F) {
                        earXRot += (0.2F * earFlop) + earForward * earFlop;
                        earYRot += 0.25F * earForward * earFlop;
                        earZRot += 0.1F;
                    }
                }
            }
            case LONG1 -> {
                earZRot = (earZRot * 0.2F) + 0.6F;
            }
            case LONG2 -> {
                earZRot = (earZRot * 0.1F) + 0.7F;
            }
            case LONG3 -> {
                earZRot = (earZRot * 0.1F) + 0.8F;
            }
        }

        earX = earZRot < -0.5F ? 1F : earZRot < 0F ? 2F : 3F;
        earXRot *= Mth.HALF_PI;
        earYRot *= Mth.HALF_PI;
        earZRot *= Mth.HALF_PI;
    }
}
