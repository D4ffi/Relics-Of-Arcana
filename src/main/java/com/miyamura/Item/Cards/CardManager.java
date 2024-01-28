package com.miyamura.Item.Cards;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class CardManager extends Item {
    static final int RANGE = 5;

    public CardManager(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.getStackInHand(hand).getOrCreateNbt().putBoolean("isActive", !player.getStackInHand(hand).getOrCreateNbt().getBoolean("isActive"));
        return super.use(world, player, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean("isActive");
    }
    public void activateAbility(PlayerEntity player) {
    }
    public void deactivateAbility(PlayerEntity player) {
    }
    public Box createBox(PlayerEntity player, int range) {
        return new Box(player.getPos().getX() - range,
                player.getPos().getY() - range,
                player.getPos().getZ() - range,
                player.getPos().getX() + range,
                player.getPos().getY() + range,
                player.getPos().getZ() + range);
    }
    public void applyEffect(PlayerEntity player, Box areaOfEffect){}
}
