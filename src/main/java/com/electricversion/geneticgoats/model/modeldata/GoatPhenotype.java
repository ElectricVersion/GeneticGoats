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
        int earLengthCounter = 0;
        // Ear Size 1
        if (gene[14] == 3 || gene[15] == 3) {
            earLengthCounter += (gene[14] == gene[15]) ? 2 : 1;
        } else if (gene[14] == 2 || gene[15] == 2) {
            earLengthCounter += 1;
        }
        // Ear Size 2
        if (gene[16] == 3 || gene[17] == 3) {
            earLengthCounter += (gene[16] == gene[17]) ? 2 : 1;
        } else if (gene[16] == 2 || gene[17] == 2) {
            earLengthCounter += 1;
        }
        switch (earLengthCounter) {
            case 4 -> earLength = EarLength.LONG3;
            case 3 -> earLength = EarLength.LONG2;
            case 2 -> earLength = EarLength.LONG1;
            case 1 -> earLength = EarLength.NORMAL;
            default -> earLength = EarLength.SMALL;
        }

        // Ear Flop
        float earFlop = 0.25F;
        earFlop += 0.75F * ((gene[18] + gene[19] + gene[20] + gene[21] - 4)/6F);
        earFlop -= 0.25F * ((gene[22] + gene[23] - 2)/4F);

//        float earForward = ((Math.max(gene[18], gene[19]) * 2) - 2) / 4F;  // 0 to 1
//        float earLowering = Math.max(gene[20], gene[21]) - 1; // 0 to 1
        float earForward = 0;
        float earLowering = 0;
        earXRot = 0F;
        earYRot = 0F;
        earZRot = (earFlop * 2F) - 1;
        earY = 0F;
        earZ = -1.05F;
        smallEarPivotZ = 0F;

        switch (earLength) {
            case SMALL, NORMAL -> {
                earZRot = (earZRot * 0.8F);
                earY += earLowering / 2F;
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
                earY += earLowering / 2F;
                earZ -= 2F;
                if (earForward > 0F) {
                    earXRot += 0.75F * earForward * earFlop;
                    earYRot += 0.25F * earForward * earFlop;
                    earZRot += 0.1F * earForward * earFlop;
                }
            }
            case LONG2 -> {
                earZRot = (earZRot * 0.1F) + 0.7F;
                earY += earLowering;
                earZ -= 2F;
                if (earForward > 0F) {
                    earXRot += 0.5F * earForward * earFlop;
                    earYRot += 0.125F * earForward * earFlop;
                    earZRot += 0.05F * earForward * earFlop;
                }
            }
            case LONG3 -> {
                earZRot = (earZRot * 0.1F) + 0.8F;
                earY += earLowering;
                earZ -= 2F;
                if (earForward > 0F) {
                    earXRot += 0.25F * earForward * earFlop;
                    earYRot += 0.05F * earForward * earFlop;
                    earZRot += 0.02F * earForward * earFlop;
                }
            }
        }

        earX = earZRot < -0.5F ? 1F : earZRot < 0F ? 2F : 3F;

        earXRot *= Mth.HALF_PI;
        earYRot *= Mth.HALF_PI;
        earZRot *= Mth.HALF_PI;
    }
}
