package com.electricversion.geneticgoats.init;

import com.electricversion.geneticgoats.entity.EnhancedGoat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.electricversion.geneticgoats.GeneticGoats.MOD_ID;

public class AddonEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

    public static final RegistryObject<EntityType<EnhancedGoat>> ENHANCED_GOAT = ENTITIES.register("enhanced_goat", () ->
            EntityType.Builder.of(EnhancedGoat::new, MobCategory.CREATURE).sized(1F, 0.5F).build(MOD_ID + ":enhanced_betta"));

    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}
