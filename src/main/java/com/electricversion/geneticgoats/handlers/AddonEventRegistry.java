package com.electricversion.geneticgoats.handlers;

import com.electricversion.geneticgoats.GeneticGoats;
import com.electricversion.geneticgoats.entity.EnhancedGoat;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.electricversion.geneticgoats.init.AddonEntities.ENHANCED_GOAT;

@Mod.EventBusSubscriber(modid = GeneticGoats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AddonEventRegistry {

    @SubscribeEvent
    public static void onEntityAttributeCreationRegistry(EntityAttributeCreationEvent event) {
        event.put(ENHANCED_GOAT.get(), EnhancedGoat.prepareAttributes().build());
    }
}
