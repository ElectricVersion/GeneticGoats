package com.electricversion.geneticgoats.entity.texture;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;

import java.util.Arrays;

public class GoatTexture {

    private static final String[] AGOUTIS = new String[] {
            "", "bezoar", "gold", "buckskin", "chamoisee", "swiss", "cou_clair", "sundgau", "tanhead", "caramel", "sable", "masked"
    };

    private static final String[] HAIR_PREFIX = new String[] {
            "shorthair/", "longhair/",
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
            "agouti/masked_light.png"
    };

    private static final String[] TX_AGOUTI_CREAM = new String[] {
            "", "agouti/bezoar_cream.png", "", "", "",
            "agouti/swiss_cream.png", "", "agouti/sundgau_cream.png",
            "", "", "agouti/sable_cream.png",
            "agouti/masked_cream.png"
    };

    private static final String[][] TX_PIEBALD = new String[][] {
            { // LOW
                    "white/piebald/piebald_low_1.png"
            },
            { // MEDIUM
                    "white/piebald/piebald_med_1.png"
            },
            { // HIGH
                    "white/piebald/piebald_high_1.png"
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

    // This method handles the logic of how individual texture components and genes should
    // interact to create a single, "compiled" texture.
    public static void calculateTexture(EnhancedGoat goat, int[] gene, char[] uuidArry) {

        GoatColors color = GoatColors.calculateColors(goat, gene, uuidArry);

        TextureGrouping rootGroup = new TextureGrouping(TexturingType.MASK_GROUP);

        // Most textures have a longhaired and shorthaired variant, so we'll need to determine which hair type to use first
        int hairType = 0; // Short or long hair. Stored as an int for texture indexing reasons
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

        int beardLength = goat.getOrSetIsFemale() ? 0 : 1; // Males have longer beards

        if (hairType != 0) beardLength++; // Long hair increases beard length a little
        // Beard length modifiers
        if (gene[72] == 2 || gene[73] == 2) beardLength++;
        if (gene[74] == 2 || gene[75] == 2) beardLength++;

        // Alpha Mask Layer
        TextureGrouping alphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(alphaGroup, "misc/mask/body.png");
        goat.addTextureToAnimalTextureGrouping(alphaGroup, TX_HAIR_LENGTH, hairLength, hairType != 0);
        goat.addTextureToAnimalTextureGrouping(alphaGroup, TX_BEARD_LENGTH, beardLength, true);
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
                goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, HAIR_PREFIX[hairType] + TX_AGOUTI_CREAM[agouti1], ("ac1" + hairType) + agouti1, color.getCreamColor());
            }
            if (!TX_AGOUTI_CREAM[agouti2].isEmpty()) {
                goat.addTextureToAnimalTextureGrouping(redColorGroup, TexturingType.APPLY_RGB, HAIR_PREFIX[hairType] + TX_AGOUTI_CREAM[agouti2], ("ac2" + hairType) + agouti2, color.getCreamColor());
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
        if (agouti1 == agouti2) {
            // Homozygous Agouti
            goat.addDelimiter("a");
            goat.addTextureToAnimalTextureGrouping(agoutiGroup, HAIR_PREFIX, hairType, TX_AGOUTI_BLACK, agouti1, true);
        } else {
            // Heterozygous Agouti
            goat.addDelimiter("aa");
            if (agouti1 < AGOUTIS.length && agouti2 < AGOUTIS.length) { // Should always be true but just in case
                String agouti1Name = AGOUTIS[agouti1];
                String agouti2Name = AGOUTIS[agouti2];
                String hetTextureString;
                if (agouti1Name.compareToIgnoreCase(agouti2Name) < 0) {
                    hetTextureString = HAIR_PREFIX[hairType]+"agouti/hets/"+agouti1Name+"_"+agouti2Name+"_light.png";
                } else {
                    hetTextureString = HAIR_PREFIX[hairType]+"agouti/hets/"+agouti2Name+"_"+agouti1Name+"_light.png";
                }
                goat.addTextureToAnimalTextureGrouping(agoutiGroup, hetTextureString, hairType+"-"+agouti1+"-"+agouti2);
            }
        }
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

        // Blank texture to avoid issues when group is empty
        goat.addTextureToAnimalTextureGrouping(whiteGroup, "misc/transparent.png");

        boolean whiteExt1 = gene[62] == 2 || gene[63] == 2;
        boolean whiteExt2 = gene[64] == 2 || gene[65] == 2;
        int whiteSize = 0;
        if (whiteExt1) whiteSize++;
        if (whiteExt2) whiteSize++;

        if (gene[4] == 2 || gene[5] == 2) {
            // Dominant White
            goat.addDelimiter("w");
            goat.addTextureToAnimalTextureGrouping(whiteGroup, "misc/solid.png");
        } else {
            // Not Dom White
            if (gene[4] == 3 || gene[5] == 3) {
                // TODO: Flowery
                goat.addDelimiter("f");
            }
            else if (gene[4] == 4 || gene[5] == 4) {
                // Piebald
                if (gene[58] != 1 || gene[59] != 1) {
                    // Piebald AND belt
                    goat.addDelimiter("pbe");
                }
                else {
                    // Piebald without belt
                    int piebaldRandom = 0;

                    goat.addDelimiter("pb");
                    goat.addTextureToAnimalTextureGrouping(whiteGroup, HAIR_PREFIX, hairType, TX_PIEBALD, whiteSize, piebaldRandom, true);
                }
            }
            else if (gene[58] != 1 || gene[59] != 1) {
                // Belted without piebald

                int beltQuality = 0;
                int beltRandom = 0;
                int sockFrontSize = 0;
                int sockBackSize = 0;
                int sockQuality = 0;
                int sockFrontRandom = 0;
                int sockBackRandom = 0;

                if (Math.min(gene[60], gene[61]) == 2) {
                    beltQuality = 1;
                }
                else if (Math.min(gene[60], gene[61]) == 3) {
                    beltQuality = 2;
                }
                if (gene[58] == 2 || gene[59] == 2) {
                    // Socked Belted
                    sockFrontSize = 1;
                    sockBackSize = 1;

                    if (whiteSize == 2) {
                        // Max white level increases sock size
                        sockFrontSize++;
                        sockBackSize++;
                    }

                    if (gene[66] == 2 || gene[67] == 2) {
                        // Sock Enhancer also increases sock size
                        sockFrontSize++;
                        sockBackSize++;
                    }

                    if (gene[68] == 2 && gene[69] == 2) sockQuality = 1; // Sock Quality Modifier
                }

                goat.addDelimiter("be");
                goat.addTextureToAnimalTextureGrouping(whiteGroup, HAIR_PREFIX, hairType, TX_BELT, whiteSize, beltQuality, beltRandom, true);

                goat.addTextureToAnimalTextureGrouping(whiteGroup, TexturingType.NONE, TX_SOCKS_FRONT, sockFrontSize, sockQuality, sockFrontRandom, sockFrontSize != 0);
                goat.addTextureToAnimalTextureGrouping(whiteGroup, TexturingType.NONE, TX_SOCKS_BACK, sockBackSize, sockQuality, sockBackRandom, sockBackSize != 0);
            }
        }
        return whiteGroup;
    }

    private static TextureGrouping makeWhiteColor(EnhancedGoat goat, int[] gene, char[] uuidArry, GoatColors color) {
        TextureGrouping whiteColorGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
        goat.addTextureToAnimalTextureGrouping(whiteColorGroup, TexturingType.APPLY_RGB, "misc/solid.png", "w", color.getWhiteColor());
        goat.addTextureToAnimalTextureGrouping(whiteColorGroup, TexturingType.APPLY_RGB, "misc/nose.png", "nw", color.getNoseWhiteColor());
        return whiteColorGroup;
    }

}