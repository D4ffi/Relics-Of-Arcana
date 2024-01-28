package com.miyamura.Item.Cards;

import com.miyamura.Interfaces.IEmperorItemStack;
import com.miyamura.Interfaces.IPlayerManagement;
import net.minecraft.advancement.criterion.ItemDurabilityChangedCriterion;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TheEmperor extends CardManager{
    public static List<ItemStack> unbreakableStacks = new ArrayList<>();
    public TheEmperor(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.theEmperor"));
    }
    @Override
    public void activateAbility(PlayerEntity player){
        IPlayerManagement customPlayer = (IPlayerManagement) player;
        for (ItemStack stack : player.getInventory().main) {
            if (customPlayer.player$EmperorGoldItems().contains(stack.getItem())) {
                unbreakableStacks.add(stack);
            }
        }
    }
    @Override
    public void deactivateAbility(PlayerEntity player) {
        unbreakableStacks.clear();
    }
}
