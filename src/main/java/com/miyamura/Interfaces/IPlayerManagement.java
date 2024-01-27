package com.miyamura.Interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IPlayerManagement {
    void player$checkInventory(PlayerEntity player);
    void player$activateOrDeactivateCards(PlayerEntity player);
    List<ItemStack> player$getActiveCards(PlayerEntity player);
    boolean player$isCardActive(Class<?> card);

}
