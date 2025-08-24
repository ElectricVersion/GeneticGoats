package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class GoatTexture {

    private static final String[] TX_HAIR_PREFIX = new String[] {
            "shorthair/", "longhair/",
    };

    private static final String[] TX_HAIR_LENGTH = new String[] {
            "", "misc/mask/hair_short.png", "misc/mask/hair_medium.png", "misc/mask/hair_long.png", "misc/mask/hair_longest.png",
    };

    private static final String[] TX_AGOUTI_BLACK = new String[] {
            "", "agouti/bezoar_light.png", "", "agouti/buckskin_light.png", "agouti/chamoisee_light.png",
            "agouti/swiss_light.png", "agouti/cou_clair_light.png", "agouti/sundgau_light.png",
            "agouti/tanhead_light.png", "agouti/caramel_light.png",
    };

    private static final String[] TX_AGOUTI_CREAM = new String[] {
            "", "agouti/bezoar_cream.png", "", "", "",
            "agouti/swiss_cream.png", "", "agouti/sundgau_cream.png",
            "", "",
    };

    private static final String[] TX_KIT = new String[] {
            "misc/transparent.png", "misc/solid.png"
    };

    // This method handles the logic of how individual texture components and genes should
    // interact to create a single, "compiled" texture.
    public static void calculateTexture(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        GoatColors color = GoatColors.calculateColors(goat, gene, uuidArry);

        TextureGrouping rootGroup = new TextureGrouping(TexturingType.MASK_GROUP);

        // Most textures have a longhaired and shorthaired variant, so we'll need to determine which hair type to use first
        int hairType = 0; // Long or short hair. Stored as an int for texture indexing reasons
        int hairLength = 0; // If long-haired, the polygenic length of the hair
        if (gene[50] == 2 && gene[51] == 2) {
            hairType = 1;
            hairLength = 1;
            if (gene[52] == 2 || gene[53] == 2) {
                hairLength++;
            }
            if (gene[54] == 2 || gene[55] == 2) {
                hairLength++;
            }
            if (gene[56] == 2 || gene[57] == 2) {
                hairLength++;
            }
        }

        // Alpha Mask Layer
        TextureGrouping alphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(alphaGroup, "misc/mask/body.png");
        goat.addTextureToAnimalTextureGrouping(alphaGroup, TX_HAIR_LENGTH, hairLength, hairType != 0);
        rootGroup.addGrouping(alphaGroup);

        // Red Layer
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        redGroup.addGrouping(makeRedMask(goat, gene, uuidArry));
        redGroup.addGrouping(makeRedColor(goat, gene, uuidArry, color, hairType));

        rootGroup.addGrouping(redGroup);

        // Black Layer
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        blackGroup.addGrouping(makeBlackMask(goat, gene, uuidArry, hairType));
        blackGroup.addGrouping(makeBlackColor(goat, gene, uuidArry, color));

        rootGroup.addGrouping(blackGroup);

        // White Layer
        TextureGrouping whiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        whiteGroup.addGrouping(makeWhiteMask(goat, gene, uuidArry, hairType));
        whiteGroup.addGrouping(makeWhiteColor(goat, gene, uuidArry, color));

        rootGroup.addGrouping(whiteGroup);

        // Detail Layer
        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/noise.png");
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/long_hair_overlay.png", hairType == 1);
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/udder.png");
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/hooves.png");
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/eyes.png");
        rootGroup.addGrouping(detailGroup);

        goat.setTextureGrouping(rootGroup);
    }

    private static TextureGrouping makeRedMask(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(redGroup, "misc/solid.png");
        return redGroup;
    }

    private static TextureGrouping makeRedColor(EnhancedGoat goat, int[] gene, char[] uuidArry, GoatColors color, int hairType) {
        TextureGrouping redColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int agouti1 = gene[0];
        int agouti2 = gene[1];
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RED, "misc/solid.png");
        if (agouti1 != 2 && agouti2 != 2) {
            // Gold agouti is dominant and blocks the cream highlights, so require that it's not present
            if (!TX_AGOUTI_CREAM[agouti1].isEmpty()) {
                goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, TX_HAIR_PREFIX[hairType] + TX_AGOUTI_CREAM[agouti1], ("ac1" + hairType) + agouti1, color.getCreamColor());
            }
            if (!TX_AGOUTI_CREAM[agouti2].isEmpty()) {
                goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, TX_HAIR_PREFIX[hairType] + TX_AGOUTI_CREAM[agouti2], ("ac2" + hairType) + agouti2, color.getCreamColor());
            }
        }
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nr", color.getNoseRedColor());

        return redColorGroup;
    }

    private static TextureGrouping makeBlackMask(EnhancedGoat goat, int[] gene, char[] uuidArry, int hairType) {
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int agouti1 = gene[0];
        int agouti2 = gene[1];

        if (agouti1 == 2 || agouti2 == 2) {
            // Gold agouti. Fully dominant and has no black, so we can stop here
            goat.addTextureToAnimalTextureGrouping(blackGroup, "misc/transparent.png");
            return blackGroup;
        }
        TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        goat.addTextureToAnimalTextureGrouping(agoutiGroup, TX_HAIR_PREFIX, hairType, TX_AGOUTI_BLACK, agouti1, true);
        goat.addTextureToAnimalTextureGrouping(agoutiGroup, TX_HAIR_PREFIX, hairType, TX_AGOUTI_BLACK, agouti2, agouti1 != agouti2);
        blackGroup.addGrouping(agoutiGroup);

        return blackGroup;
    }

    private static TextureGrouping makeBlackColor(EnhancedGoat goat, int[] gene, char[] uuidArry, GoatColors color) {
        TextureGrouping blackColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_BLACK, "misc/solid.png");
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nb", color.getNoseBlackColor());
        return blackColorGroup;
    }

    private static TextureGrouping makeWhiteMask(EnhancedGoat goat, int[] gene, char[] uuidArry, int hairType) {
        TextureGrouping whiteGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int white = 0;
        if (gene[4] == 2 || gene[5] == 2) {
            // Dominant White
            white = 1;
        } else if (gene[4] == 3 || gene[5] == 3) {
            // TODO: Flowery
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

}