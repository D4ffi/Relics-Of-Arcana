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
import java.util.Set;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract EntityGroup getGroup();

    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    public void cancelEffects(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        // extraction of effects into a Set.
        Set<StatusEffect> effectsToCancel = Set.of(
                StatusEffects.POISON,
                StatusEffects.WEAKNESS,
                StatusEffects.WITHER,
                StatusEffects.SLOWNESS,
                StatusEffects.MINING_FATIGUE,
                StatusEffects.NAUSEA,
                StatusEffects.BLINDNESS,
                StatusEffects.HUNGER,
                StatusEffects.UNLUCK,
                StatusEffects.INSTANT_DAMAGE,
                StatusEffects.BAD_OMEN
        );

        if (this.getGroup() == EntityGroup.DEFAULT) {
            assert (Object) this instanceof PlayerEntity;
            PlayerEntity player = (PlayerEntity) (Object) this;
            IPlayerManagement customPlayer = (IPlayerManagement) player;
            StatusEffect statusEffect = effect.getEffectType();

            // easier to add or remove effects from set without much code modification.
            if (customPlayer.player$isCardActive(TheHighPriestess.class) && effectsToCancel.contains(statusEffect)) {
                cir.setReturnValue(false);
            }
        }
    }
}
