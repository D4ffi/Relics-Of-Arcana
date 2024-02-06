package com.miyamura.Item.Cards;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

;

public class TheLovers extends CardManager {
    public static List<Entity> affectedEntities = new ArrayList<>();
    public static List<Entity> whiteList = new ArrayList<>();
    public static PlayerEntity immunePlayer;

    public TheLovers(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.theLovers"));
        tooltip.add(Text.translatable("description.theLovers2"));
    }
    @Override
    public void activateAbility(PlayerEntity player) {
        Box box = createBox(player,RANGE);
        applyEffect(player,box);
    }
    @Override
    public void applyEffect(PlayerEntity player, Box box) {
        immunePlayer = player;
        List<Entity> entities = player.getWorld().getOtherEntities(player, box);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 100, true, true));
            } else if (entity instanceof MobEntity) {
                ((MobEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 100, true, true));
            }
        }
    }
}
