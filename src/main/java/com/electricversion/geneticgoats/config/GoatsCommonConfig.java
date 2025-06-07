package com.electricversion.geneticgoats.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class GoatsCommonConfig {
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static String getFileName() {
        return "genetic-goats-common.toml";
    }

    public static ForgeConfigSpec getConfigSpec() {
        return COMMON_SPEC;
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue wildTypeChance;
        public final ForgeConfigSpec.IntValue growthTime;
        public final ForgeConfigSpec.IntValue birthTime;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("goat");
            wildTypeChance = builder
                    .comment("""
                            How random the genes should be; 100 is all wildtype genes, 0 is completely random
                            Default: 90""")
                    .defineInRange("Wildtype Chance", 90, 0, 100);
            growthTime = builder
                    .comment("""
                            How many ticks it will take a goat to reach adulthood
                            Default: 48000""")
                    .defineInRange("Growth Time", 48000, 1, Integer.MAX_VALUE);
            birthTime = builder
                    .comment("""
                            How many ticks it will take a goat to be born
                            Default: 48000""")
                    .defineInRange("Birth Time", 48000, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }
}
