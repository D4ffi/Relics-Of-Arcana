package com.miyamura.effect;

import com.miyamura.RelicsOfArcana;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final StatusEffect MOON_GRACE = registerStatusEffects("moon_grace",
            new MoonGrace(StatusEffectCategory.BENEFICIAL, 0x00FF00)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            "c2f5c9c0-8b7a-11eb-8dcd-0242ac130003", 0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    private static StatusEffect registerStatusEffects(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(RelicsOfArcana.MOD_ID, name), effect);
    }
    public static void registerEffects() {
    }
}
