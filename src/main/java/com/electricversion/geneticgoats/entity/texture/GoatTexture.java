package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import net.minecraft.util.Mth;

public class GoatTexture {

    /* UUID SLOTS */
//    private final int IDX_SEX = 0;
    private static final int IDX_KIT_BODY = 1;
    private static final int IDX_KIT_HEAD = 2;
    private static final int IDX_BELT_BODY = 3;
    private static final int IDX_FLOWERY = 4;
    private static final int IDX_BLAZE = 5;

    private static final String[] AGOUTIS = new String[] {
            "", "bezoar", "gold", "buckskin", "chamoisee", "swiss", "cou_clair",
            "sundgau", "tanhead", "caramel", "sable", "masked", "redcheek"
    };

    private static final String[] HAIR_PREFIX = new String[] {
            "shorthair/", "longhair/",
    };

    private static final String[] TX_EYES = new String[] {
            "misc/eyes_yellow.png", "misc/eyes_blue.png"
    };

    private static final String[] TX_SHADING = new String[] {
            "misc/short_hair_overlay.png", "misc/long_hair_overlay.png"
    };

    private static final String[] TX_HAIR_TEXTURE = new String[] {
            "", "misc/hair_wavy.png", "misc/hair_curly.png"
    };


    private static final String[] TX_BEARD_LENGTH = new String[] {
            "misc/mask/beard_shortest.png", // Female Exclusive
            "misc/mask/beard_short.png", "misc/mask/beard_medium.png", "misc/mask/beard_long.png",
            "misc/mask/beard_longest.png", // Male Exclusive
    };

    private static final String[] TX_HAIR_LENGTH = new String[] {
            "", "misc/mask/hair_short.png", "misc/mask/hair_medium.png", "misc/mask/hair_long.png", "misc/mask/hair_longest.png",
    };

    private static final String[] TX_AGOUTI_BLACK = new String[] {
            "", "agouti/bezoar_light.png", "", "agouti/buckskin_light.png", "agouti/chamoisee_light.png",
            "agouti/swiss_light.png", "agouti/cou_clair_light.png", "agouti/sundgau_light.png",
            "agouti/tanhead_light.png", "agouti/caramel_light.png", "agouti/sable_light.png",
            "agouti/masked_light.png", "agouti/redcheek_light.png"
    };

    private static final String[] TX_AGOUTI_CREAM = new String[] {
            "", "agouti/bezoar_cream.png", "", "", "",
            "agouti/swiss_cream.png", "", "agouti/sundgau_cream.png",
            "", "", "agouti/sable_cream.png",
            "agouti/masked_cream.png", ""
    };

    private static final String[] TX_AGOUTI_MALE_SHADING = new String[] {
            "", "male_shading_light1.png", "", "male_shading_light1.png", "",
            "", "male_shading_light1.png", "",
            "male_shading_light1.png", "male_shading_light1.png", "male_shading_light1.png",
            "male_shading_light1.png", ""
    };

    private static final String[][] TX_ROAN = new String[][] {
            {
                    "white/roan/roan_low1.png",
            },
            {
                    "white/roan/roan_high1.png",
            },
    };

    private static final String[][] TX_FLOWERY = new String[][] {
            { // LOW
                    "white/flowery/flowery_low1.png", "white/flowery/flowery_low2.png",
                    "white/flowery/flowery_low3.png", "white/flowery/flowery_low4.png",
            },
            { // MEDIUM
                    "white/flowery/flowery_med1.png",
                    //TODO: Replace these with real textures
                    "white/flowery/flowery_med1.png",
                    "white/flowery/flowery_med1.png",
                    "white/flowery/flowery_med1.png",
            },
            { // HIGH
                    "white/flowery/flowery_high1.png",
                    //TODO: Replace these with real textures
                    "white/flowery/flowery_high1.png",
                    "white/flowery/flowery_high1.png",
                    "white/flowery/flowery_high1.png",
            },
    };

    private static final String[][] TX_GOULET = new String[][] {
            { // LOW
                    "white/goulet/goulet_low1.png", "white/goulet/goulet_low2.png", "white/goulet/goulet_low3.png", "white/goulet/goulet_low4.png",
                    "white/goulet/goulet_low5.png", "white/goulet/goulet_low6.png", "white/goulet/goulet_low7.png", "white/goulet/goulet_low8.png",
            },
            { // MEDIUM
                    "white/goulet/goulet_med1.png", "white/goulet/goulet_med2.png", "white/goulet/goulet_med3.png", "white/goulet/goulet_med4.png",
                    "white/goulet/goulet_med5.png", "white/goulet/goulet_med6.png", "white/goulet/goulet_med7.png", "white/goulet/goulet_med8.png",
            },
            { // HIGH
                    "white/goulet/goulet_high1.png", "white/goulet/goulet_high2.png",
                    "white/goulet/goulet_high3.png",
                    //TODO: Replace these with real textures
                    "white/goulet/goulet_high1.png",
            },
    };

    private static final String[][] TX_PIEBALD = new String[][] {
            { // LOW
                    "white/piebald/piebald_low1.png", "white/piebald/piebald_low2.png",
                    "white/piebald/piebald_low3.png", "white/piebald/piebald_low4.png",
                    "white/piebald/piebald_low5.png", "white/piebald/piebald_low6.png",
                    "white/piebald/piebald_low7.png", "white/piebald/piebald_low8.png",
            },
            { // MEDIUM
                    "white/piebald/piebald_med1.png", "white/piebald/piebald_med2.png",
                    "white/piebald/piebald_med3.png", "white/piebald/piebald_med4.png",
                    "white/piebald/piebald_med5.png", "white/piebald/piebald_med6.png",
                    "white/piebald/piebald_med7.png", "white/piebald/piebald_med8.png",
            },
            { // HIGH
                    "white/piebald/piebald_high1.png", "white/piebald/piebald_high2.png",
                    "white/piebald/piebald_high3.png", "white/piebald/piebald_high4.png",
                    "white/piebald/piebald_high5.png", "white/piebald/piebald_high6.png",
                    "white/piebald/piebald_high7.png", "white/piebald/piebald_high8.png",
            }
    };

    private static final String[][] TX_PIEBALD_BELT = new String[][] {
            { // LOW
                    "white/piebald_belt/piebelt_low1.png", "white/piebald_belt/piebelt_low2.png",
                    "white/piebald_belt/piebelt_low3.png", "white/piebald_belt/piebelt_low4.png",
                    "white/piebald_belt/piebelt_low5.png", "white/piebald_belt/piebelt_low6.png",
                    "white/piebald_belt/piebelt_low7.png", "white/piebald_belt/piebelt_low8.png",

            },
            { // MEDIUM
                    "white/piebald_belt/piebelt_med1.png", "white/piebald_belt/piebelt_med2.png",
                    "white/piebald_belt/piebelt_med3.png", "white/piebald_belt/piebelt_med4.png",
                    "white/piebald_belt/piebelt_med5.png", "white/piebald_belt/piebelt_med6.png",
                    "white/piebald_belt/piebelt_med7.png", "white/piebald_belt/piebelt_med8.png",
            },
            { // HIGH
                    "white/piebald_belt/piebelt_high1.png", "white/piebald_belt/piebelt_high2.png",
                    //TODO: Replace these with real textures
                    "white/piebald_belt/piebelt_high1.png", "white/piebald_belt/piebelt_high2.png",
                    "white/piebald_belt/piebelt_high1.png", "white/piebald_belt/piebelt_high2.png",
                    "white/piebald_belt/piebelt_high1.png", "white/piebald_belt/piebelt_high2.png",
            }
    };

    private static final String[][][] TX_BELT = new String[][][] {
            { // LOW
                    {
                            "white/belt/belt_low_poor1.png"
                    },
                    {
                            "white/belt/belt_low_decent1.png"
                    },
                    {
                            "white/belt/belt_low_good1.png"
                    }
            },
            { // MEDIUM
                    {
                            "white/belt/belt_med_poor1.png"
                    },
                    {
                            "white/belt/belt_med_decent1.png"
                    },
                    {
                            "white/belt/belt_med_good1.png"
                    }
            },
            { // HIGH
                    {
                            "white/belt/belt_high_poor1.png"
                    },
                    {
                            "white/belt/belt_high_decent1.png"
                    },
                    {
                            "white/belt/belt_high_good1.png"
                    }
            }
    };

    private static final String[][][] TX_SOCKS_FRONT = new String[][][] {
            {},
            { // LOW
                    {
                            "shared/white/socks/socks_low_broken_front1.png",
                    },
                    {
                            "shared/white/socks/socks_low_clean_front1.png",
                    }
            },
            { // MEDIUM
                    {
                            "shared/white/socks/socks_med_broken_front1.png",
                    },
                    {
                            "shared/white/socks/socks_med_clean_front1.png",
                    }
            },
            { // HIGH
                    {
                            "shared/white/socks/socks_high_broken_front1.png",
                    },
                    {
                            "shared/white/socks/socks_high_clean_front1.png",
                    }
            }
    };

    private static final String[][][] TX_SOCKS_BACK = new String[][][] {
            {},
            { // LOW
                    {
                            "shared/white/socks/socks_low_broken_back1.png",
                    },
                    {
                            "shared/white/socks/socks_low_clean_back1.png",
                    }
            },
            { // MEDIUM
                    {
                            "shared/white/socks/socks_med_broken_back1.png",
                    },
                    {
                            "shared/white/socks/socks_med_clean_back1.png",
                    }
            },
            { // HIGH
                    {
                            "shared/white/socks/socks_high_broken_back1.png",
                    },
                    {
                            "shared/white/socks/socks_high_clean_back1.png",
                    }
            }
    };

    private static final String[][] TX_SCHWARTZAL = new String[][] {
            { // LOW
                    "white/schwartzal/schwartzal_low1.png",
            },
            { // MEDIUM
                    "white/schwartzal/schwartzal_med1.png",
            },
            { // HIGH
                    "white/schwartzal/schwartzal_high1.png",
            },
            { // MAX
                    "white/schwartzal/schwartzal_max1.png",
            }
    };

    private static final String[][] TX_BLAZE = new String[][] {
            { // LOW
                    "shared/white/blaze/blaze_low1.png", "shared/white/blaze/blaze_low2.png",
                    "shared/white/blaze/blaze_low3.png", "shared/white/blaze/blaze_low4.png",
            },
            { // MEDIUM
                    "shared/white/blaze/blaze_med1.png", "shared/white/blaze/blaze_med2.png",
                    "shared/white/blaze/blaze_med3.png", "shared/white/blaze/blaze_med4.png",
                    "shared/white/blaze/blaze_med5.png", "shared/white/blaze/blaze_med6.png",
                    "shared/white/blaze/blaze_med7.png", "shared/white/blaze/blaze_med8.png",
            },
            { // HIGH
                    "shared/white/blaze/blaze_high1.png", "shared/white/blaze/blaze_high2.png",
                    "shared/white/blaze/blaze_high3.png",
                    //TODO: Replace these with real textures
                    "shared/white/blaze/blaze_high1.png",
            }
    };

    private static final String[] TX_WHITE_POLL = new String[] {
            "shared/white/white_poll/white_poll_low.png",
            "shared/white/white_poll/white_poll_med.png",
            "shared/white/white_poll/white_poll_high.png",
    };

    private static final String[] TX_HALFWHITE = new String[] {
            "white/belt/halfwhite/halfwhite_low.png",
            "white/belt/halfwhite/halfwhite_med.png",
            "white/belt/halfwhite/halfwhite_high.png",
    };

    private static final String[] TX_ANGORA_MASK = new String[] {
            "misc/angora_partial_mask.png",
            "misc/angora_full_mask.png",
    };

    private static final String[][] TX_BROCKLING = new String[][] {
            { // LOW
                    "white/brockling/brockling_low1.png",
            },
            { // MEDIUM
                    "white/brockling/brockling_med1.png",
            },
            { // HIGH
                    "white/brockling/brockling_high1.png",
            }
    };


    // This method handles the logic of how individual texture components and genes should
    // interact to create a single, "compiled" texture.
    public static void calculateTexture(EnhancedGoat goat, int[] genes, char[] uuidArry) {

        GoatColors color = GoatColors.calculateColors(goat, genes, uuidArry);

        TextureGrouping rootGroup = new TextureGrouping(TexturingType.MASK_GROUP);

        // Most textures have a longhaired and shorthaired variant, so we'll need to determine which hair type to use first
        int hairType = 0; // Short or long hair. Stored as an int for texture indexing reasons
        int hairLength = 0; // If long-haired, the polygenic length of the hair
        if (genes[50] == 2 && genes[51] == 2) {
            hairType = 1;
            hairLength = 1;
            if (genes[52] == 2 || genes[53] == 2) {
                hairLength++;
            }
            if (genes[54] == 2 || genes[55] == 2) {
                hairLength++;
            }
            if (genes[56] == 2 || genes[57] == 2) {
                hairLength++;
            }
        }

        int beardLength = goat.getOrSetIsFemale() ? 0 : 1; // Males have longer beards

        if (hairType != 0) beardLength++; // Long hair increases beard length a little
        // Beard length modifiers
        if (genes[72] == 2 || genes[73] == 2) beardLength++;
        if (genes[74] == 2 || genes[75] == 2) beardLength++;

        boolean angora = genes[134] == 2 || genes[135] == 2;
        if (angora) {
            hairType = 0;
        }

        // Alpha Mask Layer
        TextureGrouping alphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(alphaGroup, "misc/mask/body.png");
        goat.addTextureToAnimalTextureGrouping(alphaGroup, TX_HAIR_LENGTH, hairLength, hairType != 0);
        goat.addTextureToAnimalTextureGrouping(alphaGroup, TX_BEARD_LENGTH, beardLength, true);
        goat.addTextureToAnimalTextureGrouping(alphaGroup, "misc/mask/hair_angora.png", angora);
        rootGroup.addGrouping(alphaGroup);

        // Red Layer
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        redGroup.addGrouping(makeRedMask(goat, genes, uuidArry));
        redGroup.addGrouping(makeRedColor(goat, genes, uuidArry, color, hairType));

        rootGroup.addGrouping(redGroup);

        // Black Layer
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        blackGroup.addGrouping(makeBlackMask(goat, genes, uuidArry, hairType));
        blackGroup.addGrouping(makeBlackColor(goat, genes, uuidArry, color));

        rootGroup.addGrouping(blackGroup);

        // White Layer & Moonspots, they share a space effectively so they interact closely with each other
        TextureGrouping[] whiteMaskGroups = makeWhiteMask(goat, genes, uuidArry, hairType);
        TextureGrouping[] moonspotMaskGroups = makeMoonspotMask(goat, genes, uuidArry, hairType);

        // TODO: Consider phasing out the color mask system in favor of tinted textures or something.
        TextureGrouping whiteColorGroup = makeWhiteColor(goat, genes, uuidArry, color);
        TextureGrouping moonspotColorGroup = makeMoonspotColor(goat, genes, uuidArry, color);

        TextureGrouping brockledWhiteColorGroup = new TextureGrouping(TexturingType.CUTOUT_GROUP);
        TextureGrouping brocklingGroup = makeBrocklingGroup(goat, genes, uuidArry, hairType);
        if (brocklingGroup.isPopulated()) brockledWhiteColorGroup.addGrouping(makeBrocklingGroup(goat, genes, uuidArry, hairType));
        brockledWhiteColorGroup.addGrouping(whiteColorGroup);

        TextureGrouping whiteBottomGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        if (whiteMaskGroups[0].isPopulated()) {
            whiteBottomGroup.addGrouping(whiteMaskGroups[0]);
            whiteBottomGroup.addGrouping(brockledWhiteColorGroup);
            rootGroup.addGrouping(whiteBottomGroup);
        }

        TextureGrouping moonspotBottomGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        if (moonspotMaskGroups[0].isPopulated()) {
            moonspotBottomGroup.addGrouping(moonspotMaskGroups[0]);
            moonspotBottomGroup.addGrouping(moonspotColorGroup);
            rootGroup.addGrouping(moonspotBottomGroup);
        }

        TextureGrouping whiteMiddleGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        if (whiteMaskGroups[1].isPopulated()) {
            whiteMiddleGroup.addGrouping(whiteMaskGroups[1]);
            whiteMiddleGroup.addGrouping(brockledWhiteColorGroup);
            rootGroup.addGrouping(whiteMiddleGroup);
        }

        TextureGrouping moonspotTopGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        if (moonspotMaskGroups[1].isPopulated()) {
            moonspotTopGroup.addGrouping(moonspotMaskGroups[1]);
            moonspotTopGroup.addGrouping(moonspotColorGroup);
            rootGroup.addGrouping(moonspotTopGroup);
        }

        TextureGrouping whiteTopGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        if (whiteMaskGroups[2].isPopulated()) {
            whiteTopGroup.addGrouping(whiteMaskGroups[2]);
            whiteTopGroup.addGrouping(brockledWhiteColorGroup);
            rootGroup.addGrouping(whiteTopGroup);
        }

        if (whiteMaskGroups[3].isPopulated()) {
            TextureGrouping whiteRoanGroup = new TextureGrouping(TexturingType.MASK_GROUP);
            whiteRoanGroup.addGrouping(whiteMaskGroups[3]);
            whiteRoanGroup.addGrouping(whiteColorGroup);
            rootGroup.addGrouping(whiteRoanGroup);
        }


        boolean curlyOrWavy = genes[48] == 2 || genes[49] == 2;
        // Curly haired/Angora shading and details
        if (angora || curlyOrWavy) {
            rootGroup.addGrouping(makeHairDetailGroup(goat, genes, color));
        }

        // Detail Layer
        TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(detailGroup, TX_SHADING, hairType, true);
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/udder_overlay.png");
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/hooves_black.png");
        goat.addTextureToAnimalTextureGrouping(detailGroup, "misc/horns_black.png");
        rootGroup.addGrouping(detailGroup);

        // White Keratin Layer; only technically separate from the detail layer for masking purposes
        if (whiteBottomGroup.isPopulated() || whiteMiddleGroup.isPopulated() || whiteTopGroup.isPopulated()) {
            TextureGrouping whiteKeratinGroup = new TextureGrouping(TexturingType.MASK_GROUP);
            TextureGrouping whiteCombinedGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            whiteCombinedGroup.addGrouping(whiteBottomGroup);
            whiteCombinedGroup.addGrouping(whiteMiddleGroup);
            whiteCombinedGroup.addGrouping(whiteTopGroup);
            whiteKeratinGroup.addGrouping(whiteCombinedGroup);
            TextureGrouping whiteKeratinColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            goat.addTextureToAnimalTextureGrouping(whiteKeratinColorGroup, "misc/hooves_white.png");
            goat.addTextureToAnimalTextureGrouping(whiteKeratinColorGroup, "misc/horns_white.png");
            whiteKeratinGroup.addGrouping(whiteKeratinColorGroup);
            rootGroup.addGrouping(whiteKeratinGroup);
        }
        rootGroup.addGrouping(makeEyeGroup(goat, genes));

        goat.setTextureGrouping(rootGroup);
    }

    private static TextureGrouping makeEyeGroup(EnhancedGoat goat, int[] genes) {
        int eyeColor = (genes[76] == 2 || genes[77] == 2) ? 1 : 0; // Blue or Yellow eyes
        TextureGrouping eyeGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(eyeGroup, TexturingType.APPLY_EYE_LEFT_COLOUR, TX_EYES, eyeColor, l -> true);
        goat.addTextureToAnimalTextureGrouping(eyeGroup, "misc/eyes_pupil.png");
        return eyeGroup;
    }


    private static TextureGrouping makeRedMask(EnhancedGoat goat, int[] genes, char[] uuidArry) {
        TextureGrouping redGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(redGroup, "misc/solid.png");
        return redGroup;
    }

    private static TextureGrouping makeRedColor(EnhancedGoat goat, int[] genes, char[] uuidArry, GoatColors color, int hairType) {
        TextureGrouping redColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int agouti1 = genes[0];
        int agouti2 = genes[1];
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RED, "misc/solid.png");
        // Gold agouti is dominant and blocks the cream highlights, so require that it's not present.
        // Tentatively same with rec. red, although I still wonder if it might behave differently
        boolean hasCreamLayer = agouti1 != 2 && agouti2 != 2 && (genes[2] != 3 || genes[3] != 3);
        if (hasCreamLayer) {
            if (!TX_AGOUTI_CREAM[agouti1].isEmpty()) {
                goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, HAIR_PREFIX[hairType] + TX_AGOUTI_CREAM[agouti1], ("ac1" + hairType) + agouti1, color.getCreamColor());
            }
            if (!TX_AGOUTI_CREAM[agouti2].isEmpty() && agouti1 != agouti2) {
                // Only add this one if it's a het - otherwise it's the same texture twice and redundant
                goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, HAIR_PREFIX[hairType] + TX_AGOUTI_CREAM[agouti2], ("ac2" + hairType) + agouti2, color.getCreamColor());
            }
        }
        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nr", color.getNoseRedColor());

        goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, "misc/udder.png", "sr", color.getSkinRedColor());

        return redColorGroup;
    }

    private static TextureGrouping makeBlackMask(EnhancedGoat goat, int[] genes, char[] uuidArry, int hairType) {
        TextureGrouping blackGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        int agouti1 = genes[0];
        int agouti2 = genes[1];

        if (genes[2] == 2 || genes[3] == 2) {
            // Dom Black. It's solid black, so agouti doesn't matter
            goat.addTextureToAnimalTextureGrouping(blackGroup, "misc/solid.png");
        } else if (genes[2] == 3 && genes[3] == 3) {
            // Recessive Red. It's solid red, so again, agouti doesn't matter.
            // Currently identical to gold visually but that maaay change, hence the separate branch
            goat.addTextureToAnimalTextureGrouping(blackGroup, "misc/transparent.png");
        } else if (agouti1 == 2 || agouti2 == 2) {
            // Gold agouti. Fully dominant and has no black, so we can stop here
            goat.addTextureToAnimalTextureGrouping(blackGroup, "misc/transparent.png");
        } else {
            // Agouti
            TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            if (agouti1 == agouti2) {
                // Homozygous Agouti
                goat.addDelimiter("a");
                goat.addPrefixedTexture(agoutiGroup, HAIR_PREFIX, hairType, TX_AGOUTI_BLACK, agouti1, true);

                if (!goat.getOrSetIsFemale()) {
                    // Male Shading
                    goat.addDelimiter("m");
                    goat.addPrefixedTexture(agoutiGroup, HAIR_PREFIX, hairType, TX_AGOUTI_MALE_SHADING, agouti1, !TX_AGOUTI_MALE_SHADING[agouti1].isEmpty());
                }
            } else {
                // Heterozygous Agouti
                goat.addDelimiter("aa");
                if (agouti1 < AGOUTIS.length && agouti2 < AGOUTIS.length) { // Should always be true but just in case
                    String agouti1Name = AGOUTIS[agouti1];
                    String agouti2Name = AGOUTIS[agouti2];
                    String hetTextureString;
                    if (agouti1Name.compareToIgnoreCase(agouti2Name) < 0) {
                        hetTextureString = HAIR_PREFIX[hairType] + "agouti/hets/" + agouti1Name + "_" + agouti2Name + "_light.png";
                    } else {
                        hetTextureString = HAIR_PREFIX[hairType] + "agouti/hets/" + agouti2Name + "_" + agouti1Name + "_light.png";
                    }
                    goat.addTextureToAnimalTextureGrouping(agoutiGroup, hetTextureString, hairType + "-" + agouti1 + "-" + agouti2);
                }
            }
            blackGroup.addGrouping(agoutiGroup);
        }
        return blackGroup;
    }

    private static TextureGrouping makeBlackColor(EnhancedGoat goat, int[] genes, char[] uuidArry, GoatColors color) {
        TextureGrouping blackColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_BLACK, "misc/solid.png");
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nb", color.getNoseBlackColor());
        goat.addTextureToAnimalTextureGrouping(blackColorGroup, TexturingType.APPLY_RGB, "misc/udder.png", "sb", color.getSkinBlackColor());
        return blackColorGroup;
    }

    private static TextureGrouping[] makeWhiteMask(EnhancedGoat goat, int[] genes, char[] uuidArry, int hairType) {
        // Three different groups representing different moonspots interactions, plus an additional one for roan
        // It's confusing it felt like the best way to represent the way that white and moonspots share space.
        // Bottom group is below all moonspots.
        TextureGrouping whiteBottomGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        // Middle group is above small moonspots, but below large and medium moonspots.
        TextureGrouping whiteMiddleGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        // Top group is above all moonspots.
        TextureGrouping whiteTopGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        // Roan is also above all moonspots but is additionally unaffected by brockling
        TextureGrouping whiteRoanGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

        boolean whiteExt1 = genes[62] == 2 || genes[63] == 2;
        boolean whiteExt2 = genes[64] == 2 || genes[65] == 2;
        int whiteSize = 0;
        if (whiteExt1) whiteSize++;
        if (whiteExt2) whiteSize++;

        // EDNRA. Because it potentially boosts white level, it has to be processed before KIT etc.
        if (genes[82] != 1 || genes[83] != 1) {
            int schwartzalSize = genes[82] + genes[83] - 3; // Since WT is 1, homozygous WT = 2. Min expression = 3
            // Clamp value to prevent crashes in case of user error (aka gene data modification)
            schwartzalSize = Mth.clamp(schwartzalSize, 0, 3);
            if (schwartzalSize > 0) whiteSize = Math.min(2, whiteSize+1); // Anything above low expression boosts overall white
            int schwartzalRandom = 0;
            goat.addDelimiter("sz");
            goat.addPrefixedTexture(whiteTopGroup, HAIR_PREFIX, hairType, TX_SCHWARTZAL, schwartzalSize, schwartzalRandom, true);
        }

        // KIT locus
        if (genes[4] == 2 || genes[5] == 2) {
            // Dominant White
            goat.addTextureToAnimalTextureGrouping(whiteTopGroup, "misc/solid.png", "dw");
        } else {
            // Not Dom White
            if (genes[4] == 3 || genes[5] == 3) {
                // Goulet
                goat.addDelimiter("g");
                int gouletRandom = uuidArry[IDX_KIT_BODY] % (whiteSize == 2 ? 4 : 8);
                goat.addPrefixedTexture(whiteSize == 2 ? whiteTopGroup : whiteMiddleGroup, HAIR_PREFIX, hairType, TX_GOULET, whiteSize, gouletRandom, true);

            } else if (genes[4] == 4 && genes[5] == 4) {
                // Piebald
                if (genes[58] != 1 || genes[59] != 1) {
                    // Piebald AND belt
                    int piebaldBeltRandom = uuidArry[IDX_KIT_BODY] % 8;

                    goat.addDelimiter("pbe");
                    goat.addPrefixedTexture(whiteSize > 0 ? whiteTopGroup : whiteMiddleGroup, HAIR_PREFIX, hairType, TX_PIEBALD_BELT, whiteSize, piebaldBeltRandom, true);
                } else {
                    // Piebald without belt
                    int piebaldRandom = uuidArry[IDX_KIT_BODY] % 8;

                    goat.addDelimiter("pb");
                    goat.addPrefixedTexture(whiteSize == 2 ? whiteTopGroup : whiteMiddleGroup, HAIR_PREFIX, hairType, TX_PIEBALD, whiteSize, piebaldRandom, true);
                }
            }
            // Can't use an else-if because that would make it mutually exclusive with goulet - just have to make sure it's not piebald!
            if ((genes[58] != 1 || genes[59] != 1) && (genes[4] != 4 || genes[5] != 4)) {
                // Belted without piebald

                int beltQuality = 0;
                int beltRandom = 0;
                int sockFrontSize = 0;
                int sockBackSize = 0;
                int sockQuality = 0;
                int sockFrontRandom = 0;
                int sockBackRandom = 0;

                if (Math.min(genes[60], genes[61]) == 2) {
                    beltQuality = 1;
                } else if (Math.min(genes[60], genes[61]) == 3) {
                    beltQuality = 2;
                }
                if (genes[58] == 2 || genes[59] == 2) {
                    // Socked Belted
                    sockFrontSize = 1;
                    sockBackSize = 1;

                    if (whiteSize == 2) {
                        // Max white level increases sock size
                        sockFrontSize++;
                        sockBackSize++;
                    }

                    if (genes[66] == 2 || genes[67] == 2) {
                        // Sock Enhancer also increases sock size
                        sockFrontSize++;
                        sockBackSize++;
                    }

                    if (genes[68] == 2 && genes[69] == 2) sockQuality = 1; // Sock Quality Modifier
                }

                if (genes[130] == 2 && genes[131] == 2) {
                    // Half-white modifier
                    goat.addDelimiter("hw");
                    goat.addPrefixedTexture(whiteTopGroup, HAIR_PREFIX, hairType, TX_HALFWHITE, whiteSize,true);
                } else {
                    // Normal belt
                    goat.addDelimiter("be");
                    goat.addPrefixedTexture(whiteTopGroup, HAIR_PREFIX, hairType, TX_BELT, whiteSize, beltQuality, beltRandom, true);
                }

                goat.addTextureToAnimalTextureGrouping(whiteTopGroup, TexturingType.NONE, TX_SOCKS_FRONT, sockFrontSize, sockQuality, sockFrontRandom, sockFrontSize != 0);
                goat.addTextureToAnimalTextureGrouping(whiteTopGroup, TexturingType.NONE, TX_SOCKS_BACK, sockBackSize, sockQuality, sockBackRandom, sockBackSize != 0);
            }

            // Blaze or White Poll
            if (genes[106] == 2 || genes[107] == 2) {
                // Blaze
                int blazeRandom = uuidArry[IDX_BLAZE] % (whiteSize == 1 ? 8 : 4);
                goat.addDelimiter("bz");
                goat.addTextureToAnimalTextureGrouping(whiteBottomGroup, TexturingType.NONE, TX_BLAZE, whiteSize, blazeRandom, true);
            } else if (genes[106] == 3 || genes[107] == 3) {
                // White Poll
                goat.addDelimiter("wp");
                goat.addTextureToAnimalTextureGrouping(whiteBottomGroup, TX_WHITE_POLL, whiteSize, true);
            }
        }

        // KITLG Locus
        // Note the lack of "else if"s.
        // All of these genes can coexist in het form except roan/flowery since roan overrides/includes flowery.
        if (genes[80] == 2 || genes[81] == 2) {
            // Silver
            goat.addDelimiter("sv");
        }
        if (genes[80] == 4 || genes[81] == 4) {
            // Flowery
            goat.addDelimiter("f");
            int floweryRandom = uuidArry[IDX_FLOWERY] % 4;
            goat.addPrefixedTexture(whiteSize == 2 ? whiteTopGroup : whiteBottomGroup, HAIR_PREFIX, hairType, TX_FLOWERY, whiteSize, floweryRandom, true);
        }
        if (genes[80] == 3 || genes[81] == 3) {
            // Roan
            int roan = 0;
            if (genes[80] == genes[81]) {
                roan = 1;
            }
            int roanRandom = 0;
            goat.addTextureToAnimalTextureGrouping(whiteTopGroup, "shared/frosting.png", "rn");
            goat.addPrefixedTexture(whiteRoanGroup, HAIR_PREFIX, hairType, TX_ROAN, roan, roanRandom, true);
        } else if (genes[80] == 5 || genes[81] == 5) {
            // Frosting
            goat.addTextureToAnimalTextureGrouping(whiteTopGroup, "shared/frosting.png", "fr");
        }

        return new TextureGrouping[]{whiteBottomGroup, whiteMiddleGroup, whiteTopGroup, whiteRoanGroup};
    }

    private static TextureGrouping makeBrocklingGroup(EnhancedGoat goat, int[] genes, char[] uuidArry, int hairType) {
        TextureGrouping whiteCutoutGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (genes[138] == 2 || genes[139] == 2) {
            // Brockling
            int brocklingSize = 1;
            if (genes[140] == 2 || genes[141] == 2) {
                // Less brockling
                brocklingSize--;
            }
            if (genes[140] == 3 || genes[141] == 3) {
                // More brockling
                brocklingSize++;
            }
            int brocklingRandom = 0;
            goat.addDelimiter("br");
            goat.addPrefixedTexture(whiteCutoutGroup, HAIR_PREFIX, hairType, TX_BROCKLING, brocklingSize, brocklingRandom, true);
        }
        return whiteCutoutGroup;
    }

    private static TextureGrouping makeWhiteColor(EnhancedGoat goat, int[] genes, char[] uuidArry, GoatColors color) {
        TextureGrouping whiteColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(whiteColorGroup, TexturingType.APPLY_RGB, "misc/solid.png", "w", color.getWhiteColor());
        goat.addTextureToAnimalTextureGrouping(whiteColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nw", color.getNoseWhiteColor());
        goat.addTextureToAnimalTextureGrouping(whiteColorGroup, TexturingType.APPLY_RGB, "misc/udder.png", "sw", color.getSkinWhiteColor());
        return whiteColorGroup;
    }

    private static TextureGrouping[] makeMoonspotMask(EnhancedGoat goat, int[] genes, char[] uuidArry, int hairType) {
        // In the current state of things, only one moonspot size can exist at a time, making the two groups unnecessary.
        // But it's very likely we'll at some point try to implement multiple sizes on a single goat, so I figure it's
        // better to plan ahead a bit and not make my life harder than necessary when the time comes.
        TextureGrouping moonspotBottomGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        TextureGrouping moonspotTopGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        if (genes[84] == 2 || genes[85] == 2) {
            // Because of the sheer quantity of moonspots textures, making a 3-dimensional array of them all feels excessive.
            // Instead, just piece together the texture name based on fixed descriptors
            String[] quantityDescriptors = { "few", "some", "many" };
            String[] sizeDescriptors = { "small", "med", "large" };
            int moonspotQuantity = 1;
            int moonspotSize = 1;
            int moonspotRandom = 0;
            if (genes[86] == 2 || genes[87] == 2) {
                // Many Moonspots
                moonspotQuantity++;
            }
            if (genes[86] == 3 | genes[87] == 3) { // Note that many and some are not mutually exclusive
                // Some Moonspots
                moonspotQuantity--;
            }

            if (genes[88] == 2 || genes[89] == 2) {
                // Larger Moonspots
                moonspotSize++;
            }
            if (genes[88] == 3 | genes[89] == 3) { // Again, not mutually exclusive
                // Smaller Moonspots
                moonspotSize--;
            }

            String texturePath = HAIR_PREFIX[hairType] + "moonspots/moonspots_" +
                    quantityDescriptors[moonspotQuantity] + "_" + sizeDescriptors[moonspotSize] + (moonspotRandom + 1) + ".png";

            String textureName = "ms" + hairType + "-" + moonspotQuantity +  "-" + moonspotSize +  "-" + moonspotRandom;

            goat.addTextureToAnimalTextureGrouping(moonspotSize == 0 ? moonspotBottomGroup : moonspotTopGroup, texturePath, textureName);

        }
        return new TextureGrouping[]{moonspotBottomGroup, moonspotTopGroup};
    }

    private static TextureGrouping makeMoonspotColor(EnhancedGoat goat, int[] genes, char[] uuidArry, GoatColors color) {
        TextureGrouping moonspotColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(moonspotColorGroup, TexturingType.APPLY_RGB, "misc/solid.png", "ms", color.getMoonspotColor());
        return moonspotColorGroup;
    }

    private static TextureGrouping makeHairDetailGroup(EnhancedGoat goat, int[] genes, GoatColors color) {
        TextureGrouping hairDetailGroup = new TextureGrouping(TexturingType.MASK_GROUP);
        boolean angora = genes[134] == 2 || genes[135] == 2;
        boolean headWool = angora && genes[136] == 2 && genes[137] == 2;
        int hairType = 0;
        if (genes[48] == 2 || genes[49] == 2) {
            hairType = genes[48] == genes[49] ? 2 : 1; // Curly if homozygous, wavy otherwise
        }
        goat.addDelimiter("h");
        goat.addTextureToAnimalTextureGrouping(hairDetailGroup, TX_ANGORA_MASK, headWool ? 1 : 0, true);
        if (angora) goat.addTextureToAnimalTextureGrouping(hairDetailGroup, TexturingType.APPLY_RGB, "misc/angora_lightness.png", "an", color.getWhiteColor());
        goat.addTextureToAnimalTextureGrouping(hairDetailGroup, TX_HAIR_TEXTURE, hairType, hairType != 0);
        return hairDetailGroup;
    }
}