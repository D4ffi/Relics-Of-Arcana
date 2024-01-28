package com.miyamura.Item.Cards;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheChariot extends CardManager{
    public TheChariot(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.theChariot"));
    }
    @Override
    public void activateAbility(PlayerEntity player){
        player.removeStatusEffect(StatusEffects.SPEED);
        Block blockUnderPlayer = player.getWorld().getBlockState(player.getBlockPos().down()).getBlock();
        if(blockUnderPlayer == Blocks.IRON_BLOCK){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));
        } else {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0));
        }
    }
}
