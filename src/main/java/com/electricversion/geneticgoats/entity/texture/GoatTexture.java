package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

public class GoatTexture {

    private static final String[] TX_AGOUTI_BLACK = new String[]{
        "", "agouti/wildtype.png", "misc/transparent.png",
            "agouti/buckskin_light.png", "agouti/chamoisee_light.png", "agouti/swiss_light.png", "agouti/cou_clair_light.png",
            "agouti/sundgau_light.png", "agouti/bezoar_light.png", "agouti/caramel_light.png", "agouti/caramel_light.png",
            "agouti/red_cheek_light.png", "agouti/sable_light.png",  "agouti/dinglu_light.png", "agouti/serpentina_light.png"
    };

    // This method handles the logic of how individual texture components and genes should
    // interact to create a single, "compiled" texture.
    public static void calculateTexture(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        int agouti1 = gene[0];
        int agouti2 = gene[1];

        TextureGrouping rootGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        // Red Layer
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(redGroup, "goat_base.png");
        rootGroup.addGrouping(redGroup);

        // Black Layer
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(blackGroup, TX_AGOUTI_BLACK, agouti1, l -> l != 0);
        rootGroup.addGrouping(blackGroup);

        goat.setTextureGrouping(rootGroup);
    }
}