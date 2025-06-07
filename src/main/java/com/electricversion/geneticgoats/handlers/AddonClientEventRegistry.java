package com.electricversion.geneticgoats.handlers;

import com.electricversion.geneticgoats.GeneticGoats;
import com.electricversion.geneticgoats.model.ModelEnhancedGoat;
import com.electricversion.geneticgoats.render.RenderEnhancedGoat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.electricversion.geneticgoats.init.AddonEntities.ENHANCED_GOAT;

@Mod.EventBusSubscriber(modid = GeneticGoats.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AddonClientEventRegistry {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onEntityRenderersRegistry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ENHANCED_GOAT.get(), RenderEnhancedGoat::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(RenderEnhancedGoat.GOAT_LAYER, ModelEnhancedGoat::createBodyLayer);
    }

}