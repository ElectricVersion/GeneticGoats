package com.electricversion.geneticgoats.entity.genetics;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class GoatBreeds {
    public static final Breed PLACEHOLDER = new Breed(new Breed.Properties().setData("Placeholder", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()));
}
