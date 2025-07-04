package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.entity.util.Colouration;

public class GoatColors {
    private int whiteColor = -1;

    private int noseRedColor = -1;
    private int noseBlackColor = -1;
    private int noseWhiteColor = -1;

    public int getWhiteColor() {
        return whiteColor;
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

    protected static GoatColors calculateColors(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        GoatColors color = new GoatColors();

        float[] melanin = {0.0427F, 0.227F, 0.071F};
        float[] pheomelanin = {0.05F, 0.52F, 0.42F};

        float[] white = {0.094F, 0.10F, 0.93F};

        float[] noseRed = {0.047F, 0.52F, 0.20F};
        float[] noseBlack = {0.047F, 0.52F, 0.04F};
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
            melanin[0] = 0.072F;
            melanin[1] = 0.306F;
            melanin[2] = 0.616F;
        } else if (gene[6] == 4 && gene[7] == 4) {
            // Chocolate
            melanin[0] = 0.039F;
            melanin[1] = 0.563F;
            melanin[2] = 0.278F;
        }

        //Universal Colouration Values (Uses ABGR)
        goat.colouration.setMelaninColour(Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]));
        goat.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));
        //Goat Specific Colors (Uses ARGB)
        color.whiteColor = Colouration.HSBtoARGB(white[0], white[1], white[2]);

        color.noseRedColor = Colouration.HSBtoARGB(noseRed[0], noseRed[1], noseRed[2]);
        color.noseBlackColor = Colouration.HSBtoARGB(noseBlack[0], noseBlack[1], noseBlack[2]);
        color.noseWhiteColor = Colouration.HSBtoARGB(noseWhite[0], noseWhite[1], noseWhite[2]);

        return color;
    }
}
