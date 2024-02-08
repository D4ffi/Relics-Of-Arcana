package com.miyamura.Item.Cards;

import com.miyamura.Interfaces.IPlayerManagement;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TheFool extends CardManager {
    Set<Entity> entities = new HashSet<>();
    Set<Entity> players = new HashSet<>();

    public TheFool(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.theFool"));
        tooltip.add(Text.translatable("description.theFool2"));
    }

    @Override
    public void activateAbility(PlayerEntity player) {
        Box box = createBox(player, 10);
        applyEffect(player, box);

    }

    @Override
    public void applyEffect(PlayerEntity player, Box box) {
        entities.clear();
        entities.addAll(player.getWorld().getEntitiesByClass(Entity.class, box, entity -> entity instanceof LivingEntity));

        for (Entity entity : entities) {
            // Skip the card holder
            if (entity.equals(player)) {
                continue;
            }

            if (entity instanceof PlayerEntity){
                IPlayerManagement playerManagement = (IPlayerManagement) entity;
                playerManagement.player$setFoolCancellation(true);
            }

            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 2, true, true));
                Objects.requireNonNull(((LivingEntity) entity).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(12);
                if (((LivingEntity) entity).getHealth() > 12) {
                    ((LivingEntity) entity).setHealth(12);
                }
            }
        }
        for (Entity entity : entities) {
            if (!box.contains(entity.getPos())) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).removeStatusEffect(StatusEffects.GLOWING);
                    Objects.requireNonNull(((LivingEntity) entity).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(((LivingEntity) entity).defaultMaxHealth);
                }
                if (entity instanceof PlayerEntity){
                    IPlayerManagement playerManagement = (IPlayerManagement) entity;
                    playerManagement.player$setFoolCancellation(false);
                }
            }
        }
    }
}
