package com.miyamura.mixin.CardAbilities;

import com.miyamura.Interfaces.IPlayerManagement;
import com.miyamura.Item.Cards.*;
import com.miyamura.RelicsOfArcana;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerAttackMixin implements IPlayerManagement {
    @Unique
    private void activateMagicianIfCardActive(Entity target) {
        if (this.player$isCardActive(TheMagician.class)) {
            target.setOnFireFor(10);
        }
    }

    @Unique
    private void activateJusticeIfCardActive(DamageSource source) {
        if (this.player$isCardActive(Justice.class)) {
            // spawn lightning
            generateLightningBoltOnActiveCard(source);
        }
    }

    @Unique
    private void activateDeathIfCardActive(Entity target, PlayerEntity player) {
        if (!player.getWorld().isClient) {
            if (this.player$isCardActive(Death.class)) {
                try {
                    if (target instanceof LivingEntity) {
                        System.out.println("Target health: " + ((LivingEntity) target).getHealth() + " Max health: " + ((LivingEntity) target).getMaxHealth());
                        if (((LivingEntity) target).getHealth() <= ((LivingEntity) target).getMaxHealth() * .30) {
                            target.kill();
                        }
                    }
                } catch (Exception e) {
                    RelicsOfArcana.LOGGER.error("Not a LivingEntity error: " + e);
                }
            }
        }

    }
    @Unique
    private void activateHangedManIfCardActive(Entity target){
        if (this.player$isCardActive(TheHangedMan.class)){
            if (target instanceof LivingEntity) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, 1, true, true));
            } else if (target instanceof MobEntity) {
                ((MobEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, 1, true, true));
            }
        }
    }

    @Unique
    private void generateLightningBoltOnActiveCard(@NotNull DamageSource source) {
        Entity attacker = source.getAttacker();
        if (attacker instanceof LivingEntity || attacker instanceof MobEntity) {
            World world = attacker.getEntityWorld();
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.refreshPositionAfterTeleport(attacker.getX(), attacker.getY(), attacker.getZ());
            lightning.setCosmetic(true);
            attacker.damage(world.getDamageSources().magic(), randomDamage(0.10, 0.25, 15, 10, 8, 5));
            world.spawnEntity(lightning);
            attacker.setOnFireFor(8);
        }
    }

    @Unique
    private float randomDamage(double firstTier, double secondTier, float firstDamage, float secondDamage, float thirdDamage, float defaultDamage) {
        double probability = Math.random();
        if (probability <= firstTier) {
            return firstDamage;
        } else if (probability < secondTier) {
            return secondDamage;
        } else if (probability >= secondTier & probability < 0.5) {
            return thirdDamage;
        } else {
            return defaultDamage;
        }
    }
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void playerAttacked(Entity target, CallbackInfo ci) {
        activateMagicianIfCardActive(target);
        activateHangedManIfCardActive(target);
        activateDeathIfCardActive(target, (PlayerEntity) (Object) this);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    public void playerDamaged(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        activateJusticeIfCardActive(source);
    }
}