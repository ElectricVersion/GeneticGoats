package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class GoatTexture {

    private static final String[] TX_AGOUTI_BLACK = new String[]{
            "", "agouti/wildtype.png", "", "agouti/buckskin_light.png", "agouti/chamoisee_light.png",
            "agouti/swiss_light.png", "agouti/cou_clair_light.png", "agouti/sundgau_light.png",
            "agouti/tanhead_light.png", "agouti/caramel_light.png",
    };

    // This method handles the logic of how individual texture components and genes should
    // interact to create a single, "compiled" texture.
    public static void calculateTexture(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        GoatColors color = calculateColor(goat, gene, uuidArry);

        TextureGrouping rootGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        // Red Layer
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        redGroup.addGrouping(makeRedPattern(goat, gene, uuidArry));

        TextureGrouping redColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RED, "misc/noise.png");
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nr", color.getNoseRedColor());
        redGroup.addGrouping(redColorGroup);

        rootGroup.addGrouping(redGroup);

        // Black Layer
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        blackGroup.addGrouping(makeBlackPattern(goat, gene, uuidArry));

        TextureGrouping blackColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_BLACK, "misc/noise.png");
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nb", color.getNoseBlackColor());
        blackGroup.addGrouping(blackColorGroup);

        rootGroup.addGrouping(blackGroup);

        // Detail Layer
        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/hooves.png");
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/eyes.png");
        rootGroup.addGrouping(detailGroup);


        goat.setTextureGrouping(rootGroup);
    }

    private static TextureGrouping makeRedPattern(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(agoutiGroup, "goat_base.png");

        return agoutiGroup;
    }

    private static TextureGrouping makeBlackPattern(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int agouti1 = gene[0];
        int agouti2 = gene[1];

        if (agouti1 == 1 || agouti2 == 1) {
            // Gold agouti. Fully dominant and has no black, so we can stop here
            goat.addTextureToAnimalTextureGrouping(agoutiGroup, "misc/transparent.png");
            return agoutiGroup;
        }
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        goat.addTextureToAnimalTextureGrouping(blackGroup, TX_AGOUTI_BLACK, agouti1, true);
        goat.addTextureToAnimalTextureGrouping(blackGroup, TX_AGOUTI_BLACK, agouti2, agouti1 != agouti2);
        agoutiGroup.addGrouping(blackGroup);

        return agoutiGroup;
    }

    private static GoatColors calculateColor(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        GoatColors color = new GoatColors();

        float[] melanin = {0.0427F, 0.227F, 0.071F};
        float[] pheomelanin = {0.05F, 0.52F, 0.42F};
        float[] noseRed = {0.047F, 0.52F, 0.20F};
        float[] noseBlack = {0.047F, 0.52F, 0.04F};

        //Universal Colouration Values
        goat.colouration.setMelaninColour(Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]));
        goat.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));
        //Goat Specific Colors
        color.setNoseRedColor(Colouration.HSBtoARGB(noseRed[0], noseRed[1], noseRed[2]));
        color.setNoseBlackColor(Colouration.HSBtoARGB(noseBlack[0], noseBlack[1], noseBlack[2]));

        return color;
    }
}