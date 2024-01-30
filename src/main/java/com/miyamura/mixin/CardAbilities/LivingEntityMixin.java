package com.miyamura.mixin.CardAbilities;

import com.miyamura.Interfaces.IPlayerManagement;
import com.miyamura.Item.Cards.TheHighPriestess;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract EntityGroup getGroup();
    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    public void cancelEffects(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (this.getGroup() == EntityGroup.DEFAULT) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            IPlayerManagement customPlayer = (IPlayerManagement) player;

            StatusEffect statusEffect = effect.getEffectType();

            if (customPlayer.player$isCardActive(TheHighPriestess.class)) {
                if (statusEffect == StatusEffects.POISON || statusEffect == StatusEffects.WEAKNESS || statusEffect == StatusEffects.WITHER
                ||  statusEffect == StatusEffects.SLOWNESS || statusEffect == StatusEffects.MINING_FATIGUE || statusEffect == StatusEffects.NAUSEA
                ||  statusEffect == StatusEffects.BLINDNESS || statusEffect == StatusEffects.HUNGER || statusEffect == StatusEffects.UNLUCK
                ||  statusEffect == StatusEffects.INSTANT_DAMAGE || statusEffect == StatusEffects.BAD_OMEN){
                cir.setReturnValue(false);
                }
            }
        }
    }
}
