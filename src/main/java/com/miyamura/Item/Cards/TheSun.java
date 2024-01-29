package com.miyamura.Item.Cards;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheSun extends CardManager{
    public TheSun(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.theSun"));
    }
    @Override
    public  void activateAbility(PlayerEntity player){
        Box box = createBox(player, CardManager.RANGE);
        applyEffect(player,box);
    }
    @Override
    public void applyEffect(PlayerEntity player, Box areaOfEffect){
        List<Entity> entities = player.getWorld().getOtherEntities(player, areaOfEffect);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity || entity instanceof MobEntity){
                entity.setOnFireFor(10);
            }
        }
    }
}
