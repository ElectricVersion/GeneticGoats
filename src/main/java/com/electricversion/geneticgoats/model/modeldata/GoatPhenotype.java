package com.electricversion.geneticgoats.model.modeldata;

import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import net.minecraft.util.Mth;

import java.util.List;

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

    public boolean isShortMuzzled() {
        return shortMuzzled;
    }

    public float getMouthXRot() {
        return mouthXRot;
    }

    public List<Float> getBodyScalings() {
        return bodyScalings;
    }

    public List<Float> getFullBodyScalings() {
        return fullBodyScalings;
    }

    public List<Float> getUpperLegScalings() {
        return upperLegScalings;
    }

    public List<Float> getHeadScalings() {
        return headScalings;
    }

    public List<Float> getUpperMouthScalings() {
        return upperMouthScalings;
    }

    public List<Float> getMouthScalings() {
        return mouthScalings;
    }

    public List<Float> getNeckScalings() {
        return neckScalings;
    }

    public boolean isBearded() {
        return bearded;
    }

    public enum EarLength {
        GOPHER,
        ELF,
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
    private boolean shortMuzzled;
    private float mouthXRot;

    //Beard Settings
    private boolean bearded;

    //Body Settings
    private float fatness;
    private float bodyWidth;
    private float bodyHeight;
    private float neckDepth;
    private float neckWidth;
    private float headWidth;
    private List<Float> bodyScalings;
    private List<Float> upperLegScalings;
    private List<Float> fullBodyScalings;
    private List<Float> headScalings;
    private List<Float> neckScalings;
    private List<Float> upperMouthScalings;
    private List<Float> mouthScalings;

    private void calculateEars(int[] genes) {
        if (genes[30] == 2 || genes[31] == 2) {
            // Gopher Ears when homozygous, Elf Ears when heterozygous
            earLength = genes[30] == genes[31] ? EarLength.GOPHER : EarLength.ELF;
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

        float earForward = clamp((genes[24] + genes[25] - 2) / 4F, 0F, 1F);  // 0 to 1
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
        earZ = -2.05F;
        smallEarPivotZ = 0F;

        switch (earLength) {
            case GOPHER -> {
                earZRot = -0.1F;
                earX = 3F;
                earY = 0F;
            }
            case ELF -> {
                earZRot = -0.5F;
                earX = 3F;
                earY = 1F;
            }
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
                earZRot = (earZRot * 0.2F) + 0.75F;
                earY += 0.5F + earLowering;
                earZ -= 2F;
                if (earForward > 0F) {
                    earXRot += 0.75F * earForward * earFlop;
                    earYRot += 0.25F * earForward * earFlop;
                    earZRot += 0.1F * earForward * earFlop;
                }
            }
            case LONG2 -> {
                earZRot = (earZRot * 0.1F) + 0.8F;
                earY += 0.5F + earLowering;
                earZ -= 2F;
                if (earForward > 0F) {
                    earXRot += 0.5F * earForward * earFlop;
                    earYRot += 0.125F * earForward * earFlop;
                    earZRot += 0.05F * earForward * earFlop;
                }
            }
            case LONG3 -> {
                earZRot = (earZRot * 0.1F) + 0.85F;
                earY += 0.5F + earLowering;
                earZ -= 2F;
                if (earForward > 0F) {
                    earXRot += 0.25F * earForward * earFlop;
                    earYRot += 0.05F * earForward * earFlop;
                    earZRot += 0.02F * earForward * earFlop;
                }
            }
        }

        if (earLength != EarLength.GOPHER && earLength != EarLength.ELF) {
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

        float romanNose = 0F;
        for (int i = 32; i < 42; i++) {
            // Higher Snout Angle
            if (genes[i] == 2) {
                romanNose += 0.1F;
            }
        }
        for (int i = 42; i < 46; i++) {
            // Lower Snout Angle
            if (genes[i] == 2) {
                romanNose -= 0.0875F;
            }
        }
        float muzzleShortness = 0F;
        for (int i = 46; i < 50; i++) {
            if (genes[i] == 2) {
                muzzleShortness += 0.25F;
            }
        }

        mouthXRot = romanNose < 0F ? (romanNose * Mth.HALF_PI * 0.175F) : 0F;

        if (muzzleShortness > 0F) {
            originalMuzzleLength = 4.2F;
            shortMuzzled = true;
        }

        muzzleXRot = Mth.HALF_PI * 0.125F;
        muzzleY = 1F;
        muzzleY -= romanNose;
        muzzleXRot += Mth.HALF_PI * 0.25F * romanNose;

        float mouthLengthA = originalMuzzleLength * Mth.sin(Mth.HALF_PI - muzzleXRot);
        float mouthLengthB = originalMuzzleHeight * Mth.cos(Mth.HALF_PI - muzzleXRot);
        upperMouthScaleZ = (mouthLengthA - (mouthLengthB + 0.2F))/originalMouthLength; // the 0.2 block difference just looks better
    }

    private void calculateBody(int[] genes) {
        fatness = 0F;
//        fatness = ((genes[38] + genes[39]) - (genes[40] + genes[41]))/4F;
        if (fatness >= 0) {
            headWidth = 1F + (fatness * 0.2F);
            bodyWidth = 1F + (fatness * 0.3F);
            neckDepth = 1F;
            neckWidth = bodyWidth;
            bodyHeight = fatness * 0.5F;
        } else {
            headWidth = 1F + (fatness * 0.025F);
            bodyWidth = 1F + (fatness * 0.1F);
            neckDepth = 1F + (fatness * 0.1F);
            neckWidth = headWidth;
            bodyHeight = fatness * 0.25F;
        }
        earX += (headWidth * 3) - 3; // Move the ears to account for the difference
    }

    private void calculateBeard(int[] genes, boolean isFemale) {
        if (genes[70] == 2 || genes[71] == 2) {
            // At least one copy of the beardless gene
            if (isFemale) {
                // One copy is all we need to suppress the beard in females
                bearded = false;
            } else {
                // If only het for beardless, the male can still have a beard
                bearded = genes[70] != genes[71];
            }
        } else {
            // No copies of beardless, thus the goat has a beard
            bearded = true;
        }
    }

    public GoatPhenotype(int[] genes, boolean isFemale) {
        calculateBeard(genes, isFemale);
        calculateEars(genes);
        calculateMuzzle(genes);
        calculateBody(genes);
        // Generate Scalings
        upperLegScalings = ModelHelper.createScalings(1F, (5F - bodyHeight)/5F, 1F, 0F, 0F, 0F);
        bodyScalings = ModelHelper.createScalings(1F, (9F + bodyHeight)/9F, 1F, 0F, 0F, 0F);
        fullBodyScalings = ModelHelper.createScalings(bodyWidth, 1F, 1F, 0F, 0F, 0F);
        neckScalings = ModelHelper.createScalings(neckWidth, 1F, neckDepth, 0F, 0F, 0F);
        headScalings = ModelHelper.createScalings(headWidth, 1F, 1F, 0F, 0F, 0F);
        upperMouthScalings = ModelHelper.createScalings(0.999F*headWidth, 1F, upperMouthScaleZ, 0F, 0F, 0F);
        mouthScalings = ModelHelper.createScalings(headWidth, 1F, upperMouthScaleZ, 0F, 0F, 0F);
    }
}
