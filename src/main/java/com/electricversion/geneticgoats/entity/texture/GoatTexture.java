package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class GoatTexture {

    private static final String[] TX_AGOUTI_BLACK = new String[]{
            "agouti/wildtype.png", "", "agouti/buckskin_light.png", "agouti/chamoisee_light.png",
            "agouti/swiss_light.png", "agouti/cou_clair_light.png", "agouti/sundgau_light.png",
            "agouti/tanhead_light.png", "agouti/caramel_light.png",
    };

    // This method handles the logic of how individual texture components and genes should
    // interact to create a single, "compiled" texture.
    public static void calculateTexture(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        calculateColor(goat, gene, uuidArry);

        TextureGrouping rootGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        // Red Layer
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        redGroup.addGrouping(makeAgoutiRed(goat, gene, uuidArry));

        TextureGrouping redNoiseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(redNoiseGroup, TexturingType.APPLY_RED, "misc/noise.png");
        redGroup.addGrouping(redNoiseGroup);

        rootGroup.addGrouping(redGroup);

        // Black Layer
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        blackGroup.addGrouping(makeAgoutiBlack(goat, gene, uuidArry));

        TextureGrouping blackNoiseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(blackNoiseGroup, TexturingType.APPLY_BLACK, "misc/noise.png");
        blackGroup.addGrouping(blackNoiseGroup);

        rootGroup.addGrouping(blackGroup);

        goat.setTextureGrouping(rootGroup);
    }

    private static TextureGrouping makeAgoutiRed(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(agoutiGroup, "goat_base.png");

        return agoutiGroup;
    }

    private static TextureGrouping makeAgoutiBlack(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int agouti1 = gene[0];
        int agouti2 = gene[1];

        if (agouti1 == 1 || agouti2 == 1) {
            // Gold agouti. Fully dominant and has no black, so we can stop here
            return agoutiGroup;
        }
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        goat.addTextureToAnimalTextureGrouping(blackGroup, TX_AGOUTI_BLACK, agouti1, true);
        goat.addTextureToAnimalTextureGrouping(blackGroup, TX_AGOUTI_BLACK, agouti2, agouti1 != agouti2);
        agoutiGroup.addGrouping(blackGroup);

        return agoutiGroup;
    }

    private static void calculateColor(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        float[] melanin = {0.0427F, 0.227F, 0.071F};
        float[] pheomelanin = {0.05F, 0.52F, 0.42F};

        goat.colouration.setMelaninColour(Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]));
        goat.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));
    }
}