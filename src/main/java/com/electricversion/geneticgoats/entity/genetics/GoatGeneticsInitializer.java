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

    @Override
    protected Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean b) {
        Biome biome = biomeHolder.value();
        int[] sexlinkedGenes = new int[EnhancedGoat.SEXLINKED_GENES_LENGTH];
        int[] autosomalGenes = new int[EnhancedGoat.AUTOSOMAL_GENES_LENGTH];

        /* Sexlinked Genes */
        sexlinkedGenes[0] = 1;
        sexlinkedGenes[1] = 1;

        /* Autosomal Genes */
        autosomalGenes[0] = 1;
        autosomalGenes[1] = 1;

        return new Genes(sexlinkedGenes, autosomalGenes);
    }
}
