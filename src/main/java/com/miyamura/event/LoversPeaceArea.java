package com.miyamura.event;

import com.miyamura.Item.Cards.TheLovers;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class LoversPeaceArea implements ServerLivingEntityEvents.AllowDamage {
    @Override
    public boolean allowDamage(LivingEntity entity, DamageSource source, float amount) {
        for (Entity affectedEntity : TheLovers.affectedEntities) {
            if (affectedEntity.equals(entity)) {
                return true;
            }
            if (entity.equals(TheLovers.immunePlayer)) {
                return false;
            }
        }
        return true;
    }
}
