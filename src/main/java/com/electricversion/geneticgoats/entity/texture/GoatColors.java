package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.entity.util.Colouration;

import static net.minecraft.util.Mth.clamp;

public class GoatColors {
    private int whiteColor = -1;
    private int creamColor = -1;

    private int noseRedColor = -1;
    private int noseBlackColor = -1;
    private int noseWhiteColor = -1;

    private int skinRedColor = -1;
    private int skinBlackColor = -1;
    private int skinWhiteColor = -1;

    public int getWhiteColor() {
        return whiteColor;
    }

    public int getCreamColor() {
        return creamColor;
    }

    public int getNoseRedColor() {
        return noseRedColor;
    }

    public int getNoseBlackColor() {
        return noseBlackColor;
    }

    public int getNoseWhiteColor() {
        return noseWhiteColor;
    }

    public int getSkinRedColor() {
        return skinRedColor;
    }

    public int getSkinBlackColor() {
        return skinBlackColor;
    }

    public int getSkinWhiteColor() {
        return skinWhiteColor;
    }

    private static void modifyRed(float[] red, float[] white, float percent) {
        if (percent > 0F) {
            red[0] += 0.008F * percent;
            red[1] += -0.520F * percent;
            red[2] += 0.424F * percent;
        } else {
            red[0] += 0.033F * percent;
            red[1] += -0.091F * percent;
            red[2] += 0.271F * percent;
        }
        // Clamp values
        red[0] = clamp(red[0], 0.031F, white[0]);
        red[1] = clamp(red[1], white[1], 0.62F);
        red[2] = clamp(red[2], 0.29F, white[2]);
    }

    protected static GoatColors calculateColors(EnhancedGoat goat, int[] genes, char[] uuidArry) {

        GoatColors color = new GoatColors();

        float[] melanin = {0.0427F, 0.227F, 0.071F};
        float[] pheomelanin = {0.064F, 0.525F, 0.578F};
        float[] cream = pheomelanin.clone();

        float[] white = {0.094F, 0.08F, 0.96F};

        float[] noseRed = {0.043F, 0.52F, 0.20F};
        float[] noseBlack = {0.047F, 0.32F, 0.21F};
        float[] noseWhite = {0.016F, 0.28F, 0.81F};

        float[] skinRed = {0.035F, 0.30F, 0.66F};
        float[] skinBlack = {0.025F, 0.32F, 0.48F};
        float[] skinWhite = {0.004F, 0.17F, 0.79F};

        float[] eyes = {0.084F, 0.76F, 0.671F};

        // Brown Locus/TYRP1
        if (genes[6] == 2 || genes[7] == 2) {
            // Dark Brown
            if (genes[6] != genes[7])  {
                // Heterozygous Dark Brown
                melanin[0] = 0.044F;
                melanin[1] = 0.333F;
                melanin[2] = 0.153F;
                
                noseBlack[2] += 0.075F;
            }
            else {
                // Homozygous Dark Brown
                melanin[0] = 0.044F;
                melanin[1] = 0.351F;
                melanin[2] = 0.239F;

                noseBlack[2] += 0.1F;
            }
        } else if (genes[6] == 3 || genes[7] == 3) {
            // Chocolate
            melanin[0] = 0.039F;
            melanin[1] = 0.563F;
            melanin[2] = 0.278F;

            noseBlack[1] = 0.57F;
        }

        int redModifier = (genes[8] + genes[9] - 2) - (genes[10] + genes[11] - 2);
        int tanModifier = 0;
        if (genes[12] == 2 || genes[13] == 2) {
            // "Red Distribution Modifier" - Hypothesized gene to lighten the tan points that appear on some agoutis
            tanModifier = genes[12] == genes[13] ? 4 : 2;
        }
        if (genes[0] == 5 || genes[1] == 5) {
            // Swiss specifically seems to have lighter cream points than other agoutis. This includes heterozygotes
            tanModifier = tanModifier+4;
        }

        int eyeDarkness = 0;
        if (genes[78] == 2 || genes[79] == 2) {
            // Darker Eyes
            eyeDarkness++;
        } else if (genes[78] == 3 || genes[79] == 3) {
            // Lighter Eyes
            eyeDarkness--;
        }

        if (genes[76] == 2 || genes[77] == 2) {
            // Blue Eyes
            eyes[0] = 0.572F;
            eyes[1] = 0.202F + (eyeDarkness * 0.15F);
            eyes[2] = 0.883F - (eyeDarkness * 0.15F);
        } else {
            // Yellow Eyes
            eyes[1] += (eyeDarkness * 0.15F);
            eyes[2] -= (eyeDarkness * 0.10F);
        }

        GoatColors.modifyRed(pheomelanin, white, redModifier/6F);
        GoatColors.modifyRed(cream, white, (redModifier+tanModifier+4)/6F);

        //Universal Colouration Values (Uses ABGR)
        goat.colouration.setMelaninColour(Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]));
        goat.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));
        // ...except for this one. For some reason eyes use ARGB
        // Only need to set one eye because goats don't really have heterochromia. Just using left eye for everything
        goat.colouration.setLeftEyeColour(Colouration.HSBtoARGB(eyes[0], eyes[1], eyes[2]));

        //Goat Specific Colors (Uses ARGB)
        color.whiteColor = Colouration.HSBtoARGB(white[0], white[1], white[2]);
        color.creamColor = Colouration.HSBtoARGB(cream[0], cream[1], cream[2]);

        color.noseRedColor = Colouration.HSBtoARGB(noseRed[0], noseRed[1], noseRed[2]);
        color.noseBlackColor = Colouration.HSBtoARGB(noseBlack[0], noseBlack[1], noseBlack[2]);
        color.noseWhiteColor = Colouration.HSBtoARGB(noseWhite[0], noseWhite[1], noseWhite[2]);

        color.skinRedColor = Colouration.HSBtoARGB(skinRed[0], skinRed[1], skinRed[2]);
        color.skinBlackColor = Colouration.HSBtoARGB(skinBlack[0], skinBlack[1], skinBlack[2]);
        color.skinWhiteColor = Colouration.HSBtoARGB(skinWhite[0], skinWhite[1], skinWhite[2]);

        return color;
    }
}
