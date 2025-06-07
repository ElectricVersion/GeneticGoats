package com.electricversion.geneticgoats;

import com.electricversion.geneticgoats.config.GoatsCommonConfig;
import com.electricversion.geneticgoats.init.AddonEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("geneticgoats")
public class GeneticGoats
{
    public static final String MOD_ID = "geneticgoats";
    public GeneticGoats()
    {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        AddonEntities.register(modEventBus);

        // Register the addon-specific config file
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GoatsCommonConfig.getConfigSpec(), GoatsCommonConfig.getFileName());
    }
}
