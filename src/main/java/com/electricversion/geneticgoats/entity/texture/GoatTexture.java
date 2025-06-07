package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class GoatTexture {
    public static void calculateTexture(EnhancedGoat goat, int[] gene, char[] uuidArry) {
        TextureGrouping rootGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        goat.setTextureGrouping(rootGroup);
    }
}