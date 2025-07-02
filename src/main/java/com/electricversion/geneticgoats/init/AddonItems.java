package com.electricversion.geneticgoats.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.electricversion.geneticgoats.GeneticGoats.MOD_ID;

public class AddonItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<ForgeSpawnEggItem> ENHANCED_GOAT_EGG = ITEMS.register("enhanced_goat_spawn_egg", () ->  new ForgeSpawnEggItem(AddonEntities.ENHANCED_GOAT, 0x6b4433, 0x1f1f1f, new Item.Properties()));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
