package com.electricversion.geneticgoats.entity.genetics;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class GoatBreeds {
    public static final Breed BOER = new Breed(new Breed.Properties().setData("Boer", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                    .add(0, "2") // Gold agouti - technically inaccurate but phenotypically close enough, the real gene isn't in yet
                    .add(10, "3|4") // Darker Red Coloration
                    .add(32, "2", "2") // Roman Nose
                    .add(82, "2") // Schwartzal
                    .add(108, "5", "5", "5") // Meat Genes
            ));
    // Non-spawning Angora "base" breed
    public static final Breed ANGORA = new Breed(new Breed.Properties().setData("Angora", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                    .add(134, "2") // Angora Hair
                    .add(136, "2") // Head Wool
                    .add(142, "2", "2", "2", "2", "2") // Higher Wool Production
            ));
    public static final Breed WHITEANGORA = new Breed(ANGORA, new Breed.Properties().setName("WhiteAngora")
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                    .add(0, "2") // Dom Tan
                    .add(2, "2|1") // Optional Dom Black
                    .add(4, "2,2|1") // At least one copy of Dom White
                    .add(6, "3|1") // Optional Chocolate
            ));
    public static final Breed COLOREDANGORA = new Breed(ANGORA, new Breed.Properties().setName("ColoredAngora")
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                    .add(0, "1|5|9|11|12") // Many different agoutis!
                    .add(4, "1") // No Dom White
            ));
    public static final Breed LAMANCHA = new Breed(new Breed.Properties().setData("Lamancha", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                    // TODO: Add nonagouti/recessive black when it exists
                    .add(0, "2|4|5|7") // Many different agoutis!
                    .add(30, "2") // Gopher Ears
                    .add(58, "2|3") // Belted
                    .add(70, "1,2") // Het Bearded
                    .add(114, "4|5", "4|5", "4|5", "10", "10") // High Dairy
                    .add(130, "1|2") // Optional Half-White modifier
            ));
}
