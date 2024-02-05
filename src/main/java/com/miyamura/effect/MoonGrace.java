package com.miyamura.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class MoonGrace extends StatusEffect {

    private static final double SPEED_BOOST = 0.08;

    protected MoonGrace(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.isSwimming() || entity.isSprinting()) {
            if (entity.isTouchingWaterOrRain()) {
                Vec3d lookVec = entity.getRotationVector();
                Vec3d boost = lookVec.multiply(SPEED_BOOST);
                entity.addVelocity(boost.x, boost.y, boost.z);
            }
        }
    }
    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
    }
}

