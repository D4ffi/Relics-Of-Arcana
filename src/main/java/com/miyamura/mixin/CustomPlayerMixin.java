package com.miyamura.mixin;

import com.miyamura.Interfaces.IPlayerManagement;
import com.miyamura.Item.Cards.CardManager;
import com.miyamura.Item.Cards.TheEmperor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerEntity.class)
public class CustomPlayerMixin implements IPlayerManagement {

    @Unique
    List<ItemStack> cardsInInventory = new ArrayList<ItemStack>();
    @Unique
    List<ItemStack> activeCards = new ArrayList<ItemStack>();
    @Unique
    List<Item> goldItems = new ArrayList<Item>();
    @Override
    public void player$checkInventory(PlayerEntity player) {
        cardsInInventory.clear();
        for (ItemStack stack : player.getInventory().main){
            if(stack.getItem() instanceof CardManager){
                cardsInInventory.add(stack);
            }
        }
        player$activateOrDeactivateCards(player);
    }
    @Override
    public void player$activateOrDeactivateCards(PlayerEntity player) {
        activeCards.clear();
        for(ItemStack stack : cardsInInventory){
            if(stack.getOrCreateNbt().getBoolean("isActive")){
                activeCards.add(stack);
                ((CardManager) stack.getItem()).activateAbility(player);
            } else{
                ((CardManager) stack.getItem()).deactivateAbility(player);
            }
        }
    }
    @Override
    public List<ItemStack> player$getActiveCards(PlayerEntity player) {
        return activeCards;
    }
    @Override
    public void player$setGoldItems() {
        goldItems.clear();

        goldItems.add(Items.GOLDEN_HELMET);
        goldItems.add(Items.GOLDEN_CHESTPLATE);
        goldItems.add(Items.GOLDEN_LEGGINGS);
        goldItems.add(Items.GOLDEN_BOOTS);

        goldItems.add(Items.GOLDEN_SWORD);
        goldItems.add(Items.GOLDEN_PICKAXE);
        goldItems.add(Items.GOLDEN_AXE);
        goldItems.add(Items.GOLDEN_SHOVEL);
        goldItems.add(Items.GOLDEN_HOE);
    }
    @Override
    public List<Item> player$EmperorGoldItems() {
        return goldItems;
    }
    @Override
    public boolean player$isCardActive(Class<?> card) {
        boolean isCardActive = false;
        for (ItemStack stack : activeCards){
            if (stack.getItem().getClass() == card) {
                isCardActive = true;
                break;
            }
        }
        return isCardActive;
    }

    @Override
    public boolean player$isEmperorInInventory() {
        boolean isEmperorInInventory = false;
        for (ItemStack stack : cardsInInventory){
            if (stack.getItem().getClass() == TheEmperor.class) {
                isEmperorInInventory = true;
                break;
            }
        }
        return isEmperorInInventory;
    }

    @Override
    public void player$clearEmperorEffect() {
        if (!player$isEmperorInInventory()){
            TheEmperor.unbreakableStacks.clear();
        }
    }
}
