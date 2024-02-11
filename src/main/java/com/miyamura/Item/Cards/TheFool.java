package com.miyamura.Item.Cards;

import com.miyamura.Interfaces.IPlayerManagement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TheFool extends CardManager {
    Set<Entity> entities = new HashSet<>();
    Set<Entity> players = new HashSet<>();

    public TheFool(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null) {
            if(!stack.isEmpty()) {
                tooltip.add(Text.translatable("description.theFool"));
                tooltip.add(Text.translatable("description.theFool2"));
                NbtCompound nbt = stack.getOrCreateNbt();
                    String arcanePowerString = Integer.toString(nbt.getInt("ArcanePower"));
                    MutableText mutableText = Text.translatable("amount.arcanePower").formatted(Formatting.ITALIC, Formatting.GRAY);
                    mutableText.append(arcanePowerString);
                    tooltip.add(mutableText);
            }
        }
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

            // Check if there is a clear path between the player and the entity
            Vec3d playerEyePos = player.getEyePos();
            Vec3d entityEyePos = entity.getEyePos();
            HitResult hitResult = player.getWorld().raycast(new RaycastContext(playerEyePos, entityEyePos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));

            // If the raycast hit a block, skip this entity
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                continue;
            }

            if (entity instanceof PlayerEntity) {
                IPlayerManagement playerManagement = (IPlayerManagement) entity;
                playerManagement.player$setFoolCancellation(true);
            }

            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 2, true, true));
                Objects.requireNonNull(((LivingEntity) entity).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(((LivingEntity) entity).defaultMaxHealth * .70);
                if (((LivingEntity) entity).getHealth() > ((LivingEntity) entity).defaultMaxHealth * .70) {
                    ((LivingEntity) entity).setHealth((float) (((LivingEntity) entity).defaultMaxHealth * .70));
                }
            }

            if (!box.contains(entity.getPos())) {
                if (entity instanceof LivingEntity) {
                    ((LivingEntity) entity).removeStatusEffect(StatusEffects.GLOWING);
                    Objects.requireNonNull(((LivingEntity) entity).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(((LivingEntity) entity).defaultMaxHealth);
                }
                if (entity instanceof PlayerEntity) {
                    IPlayerManagement playerManagement = (IPlayerManagement) entity;
                    playerManagement.player$setFoolCancellation(false);
                }
            }
        }
    }
}
