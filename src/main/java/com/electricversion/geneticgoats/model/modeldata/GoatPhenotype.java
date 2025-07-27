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

    public float getMuzzleXRot() {
        return muzzleXRot;
    }

    public float getMuzzleY() {
        return muzzleY;
    }

    public float getUpperMouthScaleZ() {
        return upperMouthScaleZ;
    }

    public float getUpperMouthHeight() {
        return upperMouthHeight;
    }

    public boolean isShortMuzzled() {
        return shortMuzzled;
    }

    public float getMouthXRot() {
        return mouthXRot;
    }

    public enum EarLength {
        GOPHER,
        SMALL,
        NORMAL,
        LONG1,
        LONG2,
        LONG3,
    }

    // Ear Settings
    private EarLength earLength;
    private float earXRot;
    private float earYRot;
    private float earZRot; //Ear angle from -1 (upwards) to 1 (lopped)
    private float earX;
    private float earY;
    private float earZ;
    private float smallEarPivotZ;

    //Muzzle Settings
    private float upperMouthScaleZ;
    private float muzzleXRot;
    private float muzzleY;
    private float upperMouthHeight;
    private boolean shortMuzzled;
    private float mouthXRot;

    private void calculateEars(int[] genes) {
        if (genes[30] == 2 || genes[31] == 2) {
            // Gopher Ears
            earLength = EarLength.GOPHER;
        } else {
            int earLengthCounter = 0;
            // Ear Size 1
            if (genes[14] == 3 || genes[15] == 3) {
                earLengthCounter += (genes[14] == genes[15]) ? 2 : 1;
            } else if (genes[14] == 2 || genes[15] == 2) {
                earLengthCounter += 1;
            }
            // Ear Size 2
            if (genes[16] == 3 || genes[17] == 3) {
                earLengthCounter += (genes[16] == genes[17]) ? 2 : 1;
            } else if (genes[16] == 2 || genes[17] == 2) {
                earLengthCounter += 1;
            }
            switch (earLengthCounter) {
                case 4 -> earLength = EarLength.LONG3;
                case 3 -> earLength = EarLength.LONG2;
                case 2 -> earLength = EarLength.LONG1;
                case 1 -> earLength = EarLength.NORMAL;
                default -> earLength = EarLength.SMALL;
            }
        }
        // Ear Flop
        float earFlop = 0.25F;
        earFlop += 0.75F * ((genes[18] + genes[19] + genes[20] + genes[21] - 4)/6F);
        earFlop -= 0.25F * ((genes[22] + genes[23] - 2)/4F);
        earFlop = clamp(earFlop, 0F, 1F); // 0 to 1

        float earForward = clamp((genes[18] + genes[19] - 2) / 4F, 0F, 1F);  // 0 to 1
        float earLowering = 0;
        if (genes[26] == 2 || genes[27] == 2) {
            // Ear Height 1
            earLowering = 0.5F;
        }
        if (genes[28] == 2 || genes[29] == 2) {
            // Ear Height 2
            earLowering += 0.5F;
        }

        earXRot = 0F;
        earYRot = 0F;
        earZRot = (earFlop * 2F) - 1;
        earY = 0F;
        earZ = -1.05F;
        smallEarPivotZ = 0F;

        if (earLength == EarLength.GOPHER) {
            earZRot = 0F;
            earX = 2F;
        } else {
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
                    } else if (earZRot > -0.25F) {
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
        }

        earXRot *= Mth.HALF_PI;
        earYRot *= Mth.HALF_PI;
        earZRot *= Mth.HALF_PI;
    }

    private void calculateMuzzle(int[] genes) {
        float originalMuzzleLength = 4.8F;
        float originalMuzzleHeight = 2.4F;
        float originalMouthLength = 4F;

        float romanNose = (Math.max(genes[32], genes[33]) - 1) / 5F;
        romanNose -= 0.35F * ((Math.max(genes[34], genes[35]) - 1) / 2F);
        float muzzleShortness = (Math.max(genes[36], genes[37]) - 1) / 2F;

        mouthXRot = romanNose < 0F ? (romanNose * Mth.HALF_PI * 0.175F) : 0F;

        if (muzzleShortness > 0F) {
            originalMuzzleLength = 4.2F;
            shortMuzzled = true;
        }

        muzzleXRot = Mth.HALF_PI * 0.125F;
        muzzleY = 1F;
        upperMouthHeight = 1F + (romanNose / 2F);
        muzzleY -= romanNose;
        muzzleXRot += Mth.HALF_PI * 0.25F * romanNose;

        float mouthLengthA = originalMuzzleLength * Mth.sin(Mth.HALF_PI - muzzleXRot);
        float mouthLengthB = originalMuzzleHeight * Mth.cos(Mth.HALF_PI - muzzleXRot);
        upperMouthScaleZ = (mouthLengthA - (mouthLengthB + 0.2F))/originalMouthLength; // the 0.2 block difference just looks better
    }

    public GoatPhenotype(int[] genes, boolean isFemale) {
        calculateEars(genes);
        calculateMuzzle(genes);
    }
}
