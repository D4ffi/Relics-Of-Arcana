package com.miyamura.Item;

import com.miyamura.RelicsOfArcana;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class ModItemGroup {

    public static final ItemGroup MAJOR_ARCANA = Registry.register(Registries.ITEM_GROUP,
            new Identifier(RelicsOfArcana.MOD_ID,"major_arcana_tab"), FabricItemGroup.builder().displayName(Text.translatable("itemGroup.major_arcana_tab"))
                    .icon(() -> new ItemStack(ModItems.THE_MOON)).entries((displayContext, entries) -> {

                        entries.add(initCards(new ItemStack(ModItems.THE_FOOL)));
                        entries.add(initCards(new ItemStack(ModItems.THE_MAGICIAN)));
                        entries.add(initCards(new ItemStack(ModItems.THE_HIGH_PRIESTESS)));
                        entries.add(initCards(new ItemStack(ModItems.THE_EMPRESS)));
                        entries.add(initCards(new ItemStack(ModItems.THE_EMPEROR)));
                        entries.add(initCards(new ItemStack(ModItems.THE_HIEROPHANT)));
                        entries.add(initCards(new ItemStack(ModItems.THE_LOVERS)));
                        entries.add(initCards(new ItemStack(ModItems.THE_CHARIOT)));
                        entries.add(initCards(new ItemStack(ModItems.STRENGTH)));
                        entries.add(initCards(new ItemStack(ModItems.THE_HERMIT)));
                        entries.add(initCards(new ItemStack(ModItems.WHEEL_OF_FORTUNE)));
                        entries.add(initCards(new ItemStack(ModItems.JUSTICE)));
                        entries.add(initCards(new ItemStack(ModItems.THE_HANGED_MAN)));
                        entries.add(initCards(new ItemStack(ModItems.DEATH)));
                        entries.add(initCards(new ItemStack(ModItems.TEMPERANCE)));
                        entries.add(initCards(new ItemStack(ModItems.THE_DEVIL)));
                        entries.add(initCards(new ItemStack(ModItems.THE_TOWER)));
                        entries.add(initCards(new ItemStack(ModItems.THE_STAR)));
                        entries.add(initCards(new ItemStack(ModItems.THE_MOON)));
                        entries.add(initCards(new ItemStack(ModItems.THE_SUN)));
                        entries.add(initCards(new ItemStack(ModItems.JUDGEMENT)));
                        entries.add(initCards(new ItemStack(ModItems.THE_WORLD)));

                    }).build());

    public static void registerItemGroups(){
    }
    private static ItemStack initCards(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putBoolean("isActive", true);
        return stack;
    }
}