package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class GoatTexture {


    private static final String[] TX_AGOUTI_BLACK = new String[]{
            "", "agouti/bezoar_light.png", "", "agouti/buckskin_light.png", "agouti/chamoisee_light.png",
            "agouti/swiss_light.png", "agouti/cou_clair_light.png", "agouti/sundgau_light.png",
            "agouti/tanhead_light.png", "agouti/caramel_light.png",
    };

    private static final String[] TX_AGOUTI_CREAM = new String[]{
            "", "agouti/bezoar_cream.png", "", "", "",
            "agouti/swiss_cream.png", "", "agouti/sundgau_cream.png",
            "", "",
    };

    private static final String[] TX_KIT = new String[]{
            "misc/transparent.png", "misc/solid.png"
    };

    // This method handles the logic of how individual texture components and genes should
    // interact to create a single, "compiled" texture.
    public static void calculateTexture(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        GoatColors color = calculateColors(goat, gene, uuidArry);

        TextureGrouping rootGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        // Red Layer
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        redGroup.addGrouping(makeRedMask(goat, gene, uuidArry));
        redGroup.addGrouping(makeRedColor(goat, gene, uuidArry, color));

        rootGroup.addGrouping(redGroup);

        // Black Layer
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        blackGroup.addGrouping(makeBlackMask(goat, gene, uuidArry));
        blackGroup.addGrouping(makeBlackColor(goat, gene, uuidArry, color));

        rootGroup.addGrouping(blackGroup);

        // White Layer
        TextureGrouping whiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        whiteGroup.addGrouping(makeWhiteMask(goat, gene, uuidArry));
        whiteGroup.addGrouping(makeWhiteColor(goat, gene, uuidArry, color));

        rootGroup.addGrouping(whiteGroup);

        // Detail Layer
        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/noise.png");
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/hooves.png");
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/eyes.png");
        rootGroup.addGrouping(detailGroup);

        goat.setTextureGrouping(rootGroup);
    }

    private static TextureGrouping makeRedMask(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(redGroup, "misc/solid.png");
        int agouti1 = gene[0];
        int agouti2 = gene[1];
        goat.addTextureToAnimalTextureGrouping(redGroup, TX_AGOUTI_CREAM, agouti1, !TX_AGOUTI_CREAM[agouti1].isEmpty());
        goat.addTextureToAnimalTextureGrouping(redGroup, TX_AGOUTI_CREAM, agouti2, !TX_AGOUTI_CREAM[agouti2].isEmpty());

        return redGroup;
    }

    private static TextureGrouping makeRedColor(EnhancedGoat goat, int[] gene, char[] uuidArry, GoatColors color) {
        TextureGrouping redColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int agouti1 = gene[0];
        int agouti2 = gene[1];
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RED, "misc/solid.png");
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TX_AGOUTI_CREAM, agouti1, !TX_AGOUTI_CREAM[agouti1].isEmpty());
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TX_AGOUTI_CREAM, agouti2, !TX_AGOUTI_CREAM[agouti2].isEmpty());
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nr", color.getNoseRedColor());

        return redColorGroup;
    }

    private static TextureGrouping makeBlackMask(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int agouti1 = gene[0];
        int agouti2 = gene[1];

        if (agouti1 == 2 || agouti2 == 2) {
            // Gold agouti. Fully dominant and has no black, so we can stop here
            goat.addTextureToAnimalTextureGrouping(blackGroup, "misc/transparent.png");
            return blackGroup;
        }
        TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        goat.addTextureToAnimalTextureGrouping(agoutiGroup, TX_AGOUTI_BLACK, agouti1, true);
        goat.addTextureToAnimalTextureGrouping(agoutiGroup, TX_AGOUTI_BLACK, agouti2, agouti1 != agouti2);
        blackGroup.addGrouping(agoutiGroup);

        return blackGroup;
    }

    private static TextureGrouping makeBlackColor(EnhancedGoat goat, int[] gene, char[] uuidArry, GoatColors color) {
        TextureGrouping blackColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_BLACK, "misc/solid.png");
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nb", color.getNoseBlackColor());
        return blackColorGroup;
    }

    private static TextureGrouping makeWhiteMask(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping whiteGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int white = 0;
        if (gene[4] == 2 || gene[5] == 2) {
            // Dominant White
            white = 1;
        }

        goat.addTextureToAnimalTextureGrouping(whiteGroup, TX_KIT, white, true);

        return whiteGroup;
    }

    private static TextureGrouping makeWhiteColor(EnhancedGoat goat, int[] gene, char[] uuidArry, GoatColors color) {
        TextureGrouping whiteColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(whiteColorGroup, TexturingType.APPLY_RGB, "misc/solid.png", "w", color.getWhiteColor());
        goat.addTextureToAnimalTextureGrouping(whiteColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nw", color.getNoseWhiteColor());
        return whiteColorGroup;
    }

    private static GoatColors calculateColors(EnhancedGoat goat, int[] gene, char[] uuidArry) {

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
        color.setWhiteColor(Colouration.HSBtoARGB(white[0], white[1], white[2]));

        color.setNoseRedColor(Colouration.HSBtoARGB(noseRed[0], noseRed[1], noseRed[2]));
        color.setNoseBlackColor(Colouration.HSBtoARGB(noseBlack[0], noseBlack[1], noseBlack[2]));
        color.setNoseWhiteColor(Colouration.HSBtoARGB(noseWhite[0], noseWhite[1], noseWhite[2]));

        return color;
    }
}