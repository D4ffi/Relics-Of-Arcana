package com.miyamura.Interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Set;

public interface IPlayerManagement {

    // Methods for players inventory management
    void player$checkInventory(PlayerEntity player);
    void player$activateOrDeactivateCards(PlayerEntity player);
    List<ItemStack> player$getActiveCards(PlayerEntity player);
    boolean player$isCardActive(Class<?> card);

    // Emperor methods
    void player$setGoldItems();
    Set<Item> player$EmperorGoldItems();
    boolean player$isEmperorInInventory();
    void player$clearEmperorEffect();

    // Temperance and Hierophant methods
    void player$increaseHealthOrXp(PlayerEntity player, int type);
    void player$resetHealth(PlayerEntity player);

    // Mana methods
    void player$increaseMana(PlayerEntity player);
    void player$decreaseMana(PlayerEntity player);



}