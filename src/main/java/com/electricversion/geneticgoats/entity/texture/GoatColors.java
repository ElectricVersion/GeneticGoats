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

    protected static GoatColors calculateColors(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        GoatColors color = new GoatColors();

        float[] melanin = {0.0427F, 0.227F, 0.071F};
        float[] pheomelanin = {0.064F, 0.525F, 0.578F};
        float[] cream = pheomelanin.clone();

        float[] white = {0.094F, 0.10F, 0.93F};

        float[] noseRed = {0.047F, 0.52F, 0.20F};
        float[] noseBlack = {0.047F, 0.32F, 0.21F};
        float[] noseWhite = {0.016F, 0.28F, 0.81F};

        // Brown Locus/TYRP1
        if (gene[6] == 2 || gene[7] == 2) {
            // Dark Brown
            if (gene[6] != gene[7])  {
                // Heterozygous Dark Brown
                melanin[0] = 0.044F;
                melanin[1] = 0.333F;
                melanin[2] = 0.153F;
            }
            else {
                // Homozygous Dark Brown
                melanin[0] = 0.044F;
                melanin[1] = 0.351F;
                melanin[2] = 0.239F;
            }
        } else if (gene[6] == 3 || gene[7] == 3) {
            // Light Brown
            melanin[0] = 0.060F;
            melanin[1] = 0.421F;
            melanin[2] = 0.671F;
        } else if (gene[6] == 4 && gene[7] == 4) {
            // Chocolate
            melanin[0] = 0.039F;
            melanin[1] = 0.563F;
            melanin[2] = 0.278F;
        }

        int redModifier = (gene[8] + gene[9] - 2) - (gene[10] + gene[11] - 2);
        int tanModifier = 0;
        if (gene[12] == 2 || gene[13] == 2) {
            // "Red Distribution Modifier" - Hypothesized gene to lighten the tan points that appear on some agoutis
            tanModifier = gene[12] == gene[13] ? 4 : 2;
        }
        if (gene[0] == 5 || gene[1] == 5) {
            // Swiss specifically seems to have lighter cream points than other agoutis. This includes heterozygotes
            tanModifier = tanModifier+4;
        }

        GoatColors.modifyRed(pheomelanin, white, redModifier/6F);
        GoatColors.modifyRed(cream, white, (redModifier+tanModifier+4)/6F);

        //Universal Colouration Values (Uses ABGR)
        goat.colouration.setMelaninColour(Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]));
        goat.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));
        //Goat Specific Colors (Uses ARGB)
        color.whiteColor = Colouration.HSBtoARGB(white[0], white[1], white[2]);
        color.creamColor = Colouration.HSBtoARGB(cream[0], cream[1], cream[2]);

        color.noseRedColor = Colouration.HSBtoARGB(noseRed[0], noseRed[1], noseRed[2]);
        color.noseBlackColor = Colouration.HSBtoARGB(noseBlack[0], noseBlack[1], noseBlack[2]);
        color.noseWhiteColor = Colouration.HSBtoARGB(noseWhite[0], noseWhite[1], noseWhite[2]);

        return color;
    }
}
