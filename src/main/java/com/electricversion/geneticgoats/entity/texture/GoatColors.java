package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.entity.util.Colouration;

import static net.minecraft.util.Mth.clamp;
import static com.electricversion.geneticgoats.util.AddonUtils.clamp;

public class GoatColors {
    private int whiteColor = -1;
    private int creamColor = -1;

    private int noseRedColor = -1;
    private int noseBlackColor = -1;
    private int noseWhiteColor = -1;

    private int skinRedColor = -1;
    private int skinBlackColor = -1;
    private int skinWhiteColor = -1;

    private int moonspotColor = -1;

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

    public int getMoonspotColor() {
        return moonspotColor;
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

        float[] moonspot = {0.067F, 0.33F, 0.737F};

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

        // MLPH
        if (genes[132] == 2 && genes[133] == 2) {
            // Blue Dilute
            pheomelanin[0] += 0.0024F;
            pheomelanin[1] += -0.156F;
            pheomelanin[2] += 0.127F;

            cream[0] += 0.0024F;
            cream[1] += -0.156F;
            cream[2] += 0.127F;

            melanin[1] += -0.25F;
            melanin[2] += 0.2F;
        }
        
        GoatColors.modifyRed(pheomelanin, white, redModifier/6F);
        GoatColors.modifyRed(cream, white, (redModifier+tanModifier+4)/6F);

        //Universal Colouration Values (Uses ABGR)
        goat.colouration.setMelaninColour(
                Colouration.HSBtoABGR(clamp(melanin[0]), clamp(melanin[1]), clamp(melanin[2]))
        );
        goat.colouration.setPheomelaninColour(
                Colouration.HSBtoABGR(clamp(pheomelanin[0]), clamp(pheomelanin[1]), clamp(pheomelanin[2])
        ));
        // ...except for this one. For some reason eyes use ARGB
        // Only need to set one eye because goats don't really have heterochromia. Just using left eye for everything
        goat.colouration.setLeftEyeColour(
                Colouration.HSBtoARGB(clamp(eyes[0]), clamp(eyes[1]), clamp(eyes[2]))
        );

        //Goat Specific Colors (Uses ARGB)
        color.whiteColor = Colouration.HSBtoARGB(clamp(white[0]), clamp(white[1]), clamp(white[2]));
        color.creamColor = Colouration.HSBtoARGB(clamp(cream[0]), clamp(cream[1]), clamp(cream[2]));

        color.noseRedColor = Colouration.HSBtoARGB(clamp(noseRed[0]), clamp(noseRed[1]), clamp(noseRed[2]));
        color.noseBlackColor = Colouration.HSBtoARGB(clamp(noseBlack[0]), clamp(noseBlack[1]), clamp(noseBlack[2]));
        color.noseWhiteColor = Colouration.HSBtoARGB(clamp(noseWhite[0]), clamp(noseWhite[1]), clamp(noseWhite[2]));

        color.skinRedColor = Colouration.HSBtoARGB(clamp(skinRed[0]), clamp(skinRed[1]), clamp(skinRed[2]));
        color.skinBlackColor = Colouration.HSBtoARGB(clamp(skinBlack[0]), clamp(skinBlack[1]), clamp(skinBlack[2]));
        color.skinWhiteColor = Colouration.HSBtoARGB(clamp(skinWhite[0]), clamp(skinWhite[1]), clamp(skinWhite[2]));

        color.moonspotColor = Colouration.HSBtoARGB(clamp(moonspot[0]), clamp(moonspot[1]), clamp(moonspot[2]));

        return color;
    }
}
