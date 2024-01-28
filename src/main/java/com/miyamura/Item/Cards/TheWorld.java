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

import java.util.List;

public class TheWorld extends CardManager{
    public TheWorld(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.theWorld"));
    }
    @Override
    public void activateAbility(PlayerEntity player){
        Box areaOfEffect = createBox(player, CardManager.RANGE);
        applyEffect(player, areaOfEffect);
    }
    @Override
    public void applyEffect(PlayerEntity player, Box areaOfEffect){
        List<Entity> entities = player.getWorld().getOtherEntities(player, areaOfEffect);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 100, true, true));
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 100, 100, true, true));
            } else if (entity instanceof MobEntity) {
                ((MobEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 100, true, true));
                ((MobEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 100, 100, true, true));
            }
        }
    }
}
