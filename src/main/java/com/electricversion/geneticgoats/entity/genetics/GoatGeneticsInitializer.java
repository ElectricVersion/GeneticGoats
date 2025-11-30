package com.electricversion.geneticgoats.entity.genetics;

import com.electricversion.geneticgoats.config.GoatsCommonConfig;
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

    public static final int AUTOSOMAL_GENES_LENGTH = 108;
    public static final int SEXLINKED_GENES_LENGTH = 2;

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
        int[] sexlinkedGenes = new int[SEXLINKED_GENES_LENGTH];
        int[] autosomalGenes = new int[AUTOSOMAL_GENES_LENGTH];

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
         * 10 - Sable; Incomplete Dom
         * 11 - Masked; Incomplete Dom
         * 12 - Red Cheek; Incomplete Dom
         */
        autosomalGenes[0] = randomizeGene(12);
        autosomalGenes[1] = randomizeGene(12);

        /* EXTENSION/MC1R
         * 1 - Wildtype
         * 2 - Black; Dominant
         * 3 - Red; Recessive
         */
        autosomalGenes[2] = randomizeGene(3);
        autosomalGenes[3] = randomizeGene(3);

        /* WHITE SPOTTING/KIT
         * 1 - Wildtype
         * 2 - White; Dominant
         * 3 - Goulet; Dominant
         * 4 - Piebald; Recessive
         */
        autosomalGenes[4] = randomizeGene(4);
        autosomalGenes[5] = randomizeGene(4);

        /* BROWN/TYRP1
         * 1 - Wildtype
         * 2 - Dark Brown; Incomplete Dom
         * 3 - Chocolate; Recessive
         * (Dark Brown > Wildtype > Chocolate)
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
         * 2 - Lower; Dominant
         */
        autosomalGenes[26] = randomizeGene(3);
        autosomalGenes[27] = randomizeGene(3);

        /* EAR HEIGHT 2
         * 1 - Wildtype
         * 2 - Lower; Dominant
         */
        autosomalGenes[28] = randomizeGene(3);
        autosomalGenes[29] = randomizeGene(3);

        /* GOPHER EAR
         * 1 - Wildtype
         * 2 - Gopher; Incomplete Dominant
         */
        autosomalGenes[30] = randomizeGene(2);
        autosomalGenes[31] = randomizeGene(2);

        /* HIGHER MUZZLE ANGLE
         * Total Range: 0 - 5
         */
        for (int i = 32; i < 42; i++) {
            autosomalGenes[i] = randomizeGene(2);
        }

        /* LOWER MUZZLE ANGLE
         * Total Range: 0 - 2
         */
        for (int i = 42; i < 46; i++) {
            autosomalGenes[i] = randomizeGene(2);
        }

        /* MUZZLE SHORTNESS
         * Total Range: 0 - 2
         */
        for (int i = 46; i < 50; i++) {
            autosomalGenes[i] = randomizeGene(2);
        }

        /* LONG HAIR
         * 1 - Wildtype
         * 2 - Long Hair; Recessive
         */
        autosomalGenes[50] = randomizeGene(2);
        autosomalGenes[51] = randomizeGene(2);

        /* LONG HAIR MODIFIERS
         * 0 - 3
         */
        for (int i = 52; i < 58; i++) {
            autosomalGenes[i] = randomizeGene(2);
        }

        /* BELTED
         * 1 - Wildtype
         * 2 - Belted with Socks; Dominant
         * 3 - Belted; Dominant
         * (Belted w/ Socks > Belted > Wildtype)
         */
        autosomalGenes[58] = randomizeGene(3);
        autosomalGenes[59] = randomizeGene(3);

        /* BELT QUALITY
         * 1 - 3
         * Lower is more dominant
         */
        autosomalGenes[60] = randomizeGene(3);
        autosomalGenes[61] = randomizeGene(3);

        /* WHITE EXTENSION 1
         * 1 - Wildtype
         * 2 - Higher White; Dominant
         */
        autosomalGenes[62] = randomizeGene(2);
        autosomalGenes[63] = randomizeGene(2);

        /* WHITE EXTENSION 2
         * 1 - Wildtype
         * 2 - Higher White; Dominant
         */
        autosomalGenes[64] = randomizeGene(2);
        autosomalGenes[65] = randomizeGene(2);

        /* SOCKS ENHANCER
         * 1 - Wildtype
         * 2 - Higher White; Dominant
         */
        autosomalGenes[66] = randomizeGene(2);
        autosomalGenes[67] = randomizeGene(2);

        /* SOCKS QUALITY
         * 1 - Wildtype
         * 2 - Higher Quality; Recessive
         */
        autosomalGenes[68] = randomizeGene(2);
        autosomalGenes[69] = randomizeGene(2);

        /* BEARDED/BEARDLESS
         * 1 - Bearded (Wildtype)
         * 2 - Non-Bearded; Dominant in Females, Recessive in Males
         */
        autosomalGenes[70] = randomizeGene(2);
        autosomalGenes[71] = randomizeGene(2);

        /* BEARD LENGTH MODIFIER 1
         * 1 - Wildtype
         * 2 - Longer Beard (Dominant)
         */
        autosomalGenes[72] = randomizeGene(2);
        autosomalGenes[73] = randomizeGene(2);

        /* BEARD LENGTH MODIFIER 2
         * 1 - Wildtype
         * 2 - Longer Beard (Dominant)
         */
        autosomalGenes[74] = randomizeGene(2);
        autosomalGenes[75] = randomizeGene(2);

        /* BLUE EYES
         * 1 - Yellow Eyes (Wildtype)
         * 2 - Blue Eyes
         */
        autosomalGenes[76] = randomizeGene(2);
        autosomalGenes[77] = randomizeGene(2);

        /* EYE SHADING
         * 1 - Wildtype
         * 2 - Darker; Dominant
         * 3 - Lighter; Dominant
         * (Darker > Lighter > Wildtype)
         */
        autosomalGenes[78] = randomizeGene(3);
        autosomalGenes[79] = randomizeGene(3);

        /* FROSTING AND POSSIBLY ROAN
         * 1 - Wildtype
         * 2 - Silver; Dominant
         * 3 - Roan; Dominant
         * 4 - Flowery; Dominant
         * 5 - Frosting; Dominant
         * (Silver <> Roan <> Flowery <> Frosting > Wildtype)
         */
        autosomalGenes[80] = randomizeGene(4);
        autosomalGenes[81] = randomizeGene(4);

        /* SCHWARTZAL (EDNRA?)
         * 1 - Wildtype
         * 2 - Medium Schwartzal; Incomplete Dominant
         * 3 - High Schwartzal; Incomplete Dominant
         */
        autosomalGenes[82] = randomizeGene(3);
        autosomalGenes[83] = randomizeGene(3);

        /* MOONSPOTS
         * 1 - Wildtype
         * 2 - Moonspots (Dominant)
         */
        autosomalGenes[84] = randomizeGene(2);
        autosomalGenes[85] = randomizeGene(2);

        /* MOONSPOT QUANTITY MODIFIER
         * 1 - Some Moonspots
         * 2 - Many Moonspots; Incomplete Dominant
         * 3 - Few Moonspots; Incomplete Dominant
         * (Many/Some will produce Many; Few/Some will produce Few; Many/Few will produce Some)
        */
        autosomalGenes[86] = randomizeGene(3);
        autosomalGenes[87] = randomizeGene(3);

        /* MOONSPOT SIZE MODIFIER
         * 1 - Medium Size (Wildtype)
         * 2 - Large Moonspots; Incomplete Dominant
         * 3 - Small Moonspots; Incomplete Dominant
         * (Heterozygotes produce a mixture of sizes - e.g. normal/large will produce goats with both normal and large spots)
         * Implementation of the above currently undecided - may need to be adjusted or simplified somehow
         */
        autosomalGenes[88] = randomizeGene(3);
        autosomalGenes[89] = randomizeGene(3);

        /* HORN ROOT ROTATION
         * 1-99
         */
        autosomalGenes[90] = randomizeGene(99);
        autosomalGenes[91] = randomizeGene(99);

        /* HORN MIDDLE 1 ROTATION
         * 1-99
         */
        autosomalGenes[92] = randomizeGene(99);
        autosomalGenes[93] = randomizeGene(99);

        /* HORN MIDDLE 2 ROTATION
         * 1-99
         */
        autosomalGenes[94] = randomizeGene(99);
        autosomalGenes[95] = randomizeGene(99);

        /* HORN MIDDLE 3 ROTATION
         * 1-99
         */
        autosomalGenes[96] = randomizeGene(99);
        autosomalGenes[97] = randomizeGene(99);

        /* HORN TIP ROTATION
         * 1-99
         */
        autosomalGenes[98] = randomizeGene(99);
        autosomalGenes[99] = randomizeGene(99);

        /* HORN SHORTENER 1
         * 1 - Wildtype
         * 2 - Shorter Horns (Dominant)
         */
        autosomalGenes[100] = randomizeGene(2);
        autosomalGenes[101] = randomizeGene(2);

        /* HORN SHORTENER 2
         * 1 - Wildtype
         * 2 - Shorter Horns (Dominant)
         * 3 - Shorter Thicker Horns (Dominant)
         */
        autosomalGenes[102] = randomizeGene(3);
        autosomalGenes[103] = randomizeGene(3);

        /* HORN SHORTENER 3
         * 1 - Wildtype
         * 2 - Shorter Horns (Dominant)
         * 3 - Shorter Thicker Horns (Dominant)
         */
        autosomalGenes[104] = randomizeGene(2);
        autosomalGenes[105] = randomizeGene(2);

        // TODO: Implement body fat and dairy polygenes
//        /* Placeholder Body Fat
//         * 1 - 3
//         */
//        autosomalGenes[38] = randomizeGene(3);
//        autosomalGenes[39] = randomizeGene(3);
//
//        /* Placeholder Dairy
//         * 1 - 3
//         */
//        autosomalGenes[40] = randomizeGene(3);
//        autosomalGenes[41] = randomizeGene(3);



        return new Genes(sexlinkedGenes, autosomalGenes);
    }
}
