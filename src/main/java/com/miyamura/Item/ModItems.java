package com.miyamura.Item;

import com.miyamura.RelicsOfArcana;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final Item TEST_ITEM = registerNewItems("test_item", new Item(new FabricItemSettings()));

    private static Item registerNewItems(String name, Item item) {
        return Registry.register(Registries.ITEM, RelicsOfArcana.MOD_ID + ":" + name, item);
    }

    public static void registerItems() {
        RelicsOfArcana.LOGGER.info("Registering items for Relics of Arcana...");
    }
}
