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
         * 8 - Tanhead; Incomplete Dom
         * 9 - Caramel; Incomplete Dom
         */
        autosomalGenes[0] = randomizeGene(9);
        autosomalGenes[1] = randomizeGene(9);

        /* EXTENSION/MC1R
         * 1 - Wildtype
         * 2 - Dominant black
         * 3 - Recessive Red
         */
        autosomalGenes[2] = randomizeGene(3);
        autosomalGenes[3] = randomizeGene(3);

        /* WHITE SPOTTING/KIT
         * 1 - Wildtype
         * 2 - Dominant White
         */
        autosomalGenes[4] = randomizeGene(2);
        autosomalGenes[5] = randomizeGene(2);

        /* BROWN/TYRP1
         * 1 - Wildtype
         * 2 - Dark Brown; Incomplete Dom
         * 3 - Light Brown; Dominant
         * 4 - Chocolate; Recessive
         * (Dark Brown > Light Brown > Wildtype > Chocolate)
         */
        autosomalGenes[6] = randomizeGene(4);
        autosomalGenes[7] = randomizeGene(4);

        /* RED COLOR 1 (Incomplete Dominant)
         * 1 - wildtype/tan
         * 2 - cream
         * 3 - off-white
         * 4 - white
         */
        autosomalGenes[8] = randomizeGene(4);
        autosomalGenes[9] = randomizeGene(4);

        /* RED COLOR 2 (Incomplete Dominant)
         * 1 - wildtype/tan
         * 2 - red-brown
         * 3 - darker red
         * 4 - deep red
         */
        autosomalGenes[10] = randomizeGene(4);
        autosomalGenes[11] = randomizeGene(4);

        /* RED DISTRIBUTION MODIFIER (Incomplete Dominant)
         * 1 - Normal Points
         * 2 - Lighter Cream
         */
        autosomalGenes[12] = randomizeGene(2);
        autosomalGenes[13] = randomizeGene(2);

        /* EAR SIZE 1
         * 1 - Smallest (Wildtype)
         * 2 - Medium; Dominant
         * 3 - Large; Incomplete Dominant
         */
        autosomalGenes[14] = randomizeGene(3);
        autosomalGenes[15] = randomizeGene(3);

        /* EAR SIZE 2
         * 1 - Smallest (Wildtype)
         * 2 - Medium; Dominant
         * 3 - Large; Incomplete Dominant
         */
        autosomalGenes[16] = randomizeGene(3);
        autosomalGenes[17] = randomizeGene(3);

        /* EAR FLOP 1 (Incomplete Dominant)
         * 1 - Wildtype
         * 2 - Floppy
         * 3 - Floppier
         */
        autosomalGenes[18] = randomizeGene(3);
        autosomalGenes[19] = randomizeGene(3);

        /* EAR FLOP 2 (Incomplete Dominant)
         * 1 - Wildtype
         * 2 - Floppier
         */
        autosomalGenes[20] = randomizeGene(2);
        autosomalGenes[21] = randomizeGene(2);

        /* EAR FLOP 3 (Incomplete Dominant)
         * 1 - Wildtype
         * 2 - More Upright
         * 3 - Most Upright
         */
        autosomalGenes[22] = randomizeGene(3);
        autosomalGenes[23] = randomizeGene(3);

        /* EAR FLOP MODIFIER (Incomplete Dominant)
         * 1 - Wildtype
         * 2 - Slightly Forward
         * 3 - Most Forward
         */
        autosomalGenes[24] = randomizeGene(3);
        autosomalGenes[25] = randomizeGene(3);

        /* EAR HEIGHT 1
         * 1 - Wildtype
         * 2 - Lower (Dominant)
         */
        autosomalGenes[26] = randomizeGene(3);
        autosomalGenes[27] = randomizeGene(3);

        /* EAR HEIGHT 2
         * 1 - Wildtype
         * 2 - Lower (Dominant)
         */
        autosomalGenes[28] = randomizeGene(3);
        autosomalGenes[29] = randomizeGene(3);

        /* Placeholder Muzzle Length
         * 1 - 6
         */
        autosomalGenes[30] = randomizeGene(6);
        autosomalGenes[31] = randomizeGene(6);
        return new Genes(sexlinkedGenes, autosomalGenes);
    }
}
