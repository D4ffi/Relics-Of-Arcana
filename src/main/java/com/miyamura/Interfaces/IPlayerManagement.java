package com.miyamura.Interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IPlayerManagement {
    void player$checkInventory(PlayerEntity player);
    void player$activateOrDeactivateCards(PlayerEntity player);
    List<ItemStack> player$getActiveCards(PlayerEntity player);
    void player$setGoldItems();
    List<Item> player$EmperorGoldItems();
    boolean player$isCardActive(Class<?> card);
    boolean player$isEmperorInInventory();
    void player$clearEmperorEffect();
    void player$increaseHealthOrXp(PlayerEntity player, int type);
    void player$resetHealth(PlayerEntity player);
    void player$setHealthOrXp(PlayerEntity player,int type);

}
