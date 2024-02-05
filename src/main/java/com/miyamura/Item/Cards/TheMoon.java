package com.miyamura.Item.Cards;

import com.miyamura.effect.ModEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheMoon extends CardManager {
    public TheMoon(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.theMoon"));
    }

    @Override
    public void activateAbility(PlayerEntity player) {
        if (player.isSubmergedInWater() && player.isWet()) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 220, 1));
            player.addStatusEffect(new StatusEffectInstance(ModEffects.MOON_GRACE, 220, 0));
        }
    }
}
