package com.electricversion.geneticgoats.entity.genetics;

import com.electricversion.geneticgoats.config.GoatsCommonConfig;
import com.electricversion.geneticgoats.entity.EnhancedGoat;
import mokiyoki.enhancedanimals.entity.genetics.AbstractGeneticsInitialiser;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GoatGeneticsInitializer extends AbstractGeneticsInitialiser {

    int WTC = GoatsCommonConfig.COMMON.wildTypeChance.get();
    List<Breed> breeds = new ArrayList<>();

    public GoatGeneticsInitializer() {
        breeds.add(GoatBreeds.PLACEHOLDER);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean isBreed) {
        return super.generateNewGenetics(world, pos, isBreed, breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breedName) {
        return super.generateWithBreed(world, pos, breeds, breedName);
    }

    // Utility function for generating a single gene. Moving it here just makes the code a little more readable
    private int randomizeGene(int mutations) {
        return ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(mutations) + 1) : 1;
    }

    @Override
    protected Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean b) {
        Biome biome = biomeHolder.value();
        int[] sexlinkedGenes = new int[EnhancedGoat.SEXLINKED_GENES_LENGTH];
        int[] autosomalGenes = new int[EnhancedGoat.AUTOSOMAL_GENES_LENGTH];

        /* Sexlinked Genes */
        sexlinkedGenes[0] = 1;
        sexlinkedGenes[1] = 1;

        /* Autosomal Genes */

        /* AGOUTI/ASIP
         * 1 - Wildtype; Incomplete Dom
         * 2 - Gold; Dominant
         * 3 - Buckskin; Incomplete Dom
         * 4 - Chamoisee; Incomplete Dom
         * 5 - Swiss; Incomplete Dom
         * 6 - Cou Clair; Incomplete Dom
         * 7 - Sundgau; Incomplete Dom
         * 8 - Bezoar; Incomplete Dom
         * 9 - Caramel; Incomplete Dom
         * 10 - Red Cheek; Incomplete Dom
         * 11 - Sable; Incomplete Dom
         * 12 - Dinglu; Incomplete Dom
         * 13 - Serpentina; Incomplete Dom
         * 14 - Non-Agouti/Black; Recessive
         */
        autosomalGenes[0] = randomizeGene(14);
        autosomalGenes[1] = randomizeGene(14);

        return new Genes(sexlinkedGenes, autosomalGenes);
    }
}
