package com.miyamura.Item.Cards;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Justice extends CardManager {
    public Justice(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.justice"));
        NbtCompound nbt = stack.getOrCreateNbt();
        String arcanePowerString = Integer.toString(nbt.getInt("ArcanePower"));
        MutableText mutableText = Text.translatable("amount.arcanePower").formatted(Formatting.ITALIC, Formatting.GRAY);
        mutableText.append(arcanePowerString);
        tooltip.add(mutableText);
    }
}

