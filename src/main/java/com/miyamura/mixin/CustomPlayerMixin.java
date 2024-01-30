package com.miyamura.mixin;

import com.miyamura.Interfaces.IPlayerManagement;
import com.miyamura.Item.Cards.CardManager;
import com.miyamura.Item.Cards.Temperance;
import com.miyamura.Item.Cards.TheEmperor;
import com.miyamura.Item.Cards.TheHierophant;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mixin(PlayerEntity.class)
public class CustomPlayerMixin implements IPlayerManagement {

    @Unique
    List<ItemStack> cardsInInventory = new ArrayList<>();
    @Unique
    List<ItemStack> activeCards = new ArrayList<>();
    @Unique
    Set<Item> goldItems = new java.util.HashSet<>();
    @Unique
    boolean firstLoop = true;
    @Unique
    final double DEFAULT_MAX_HEALTH = 20.0, HEALTH_INCREMENT = 1.0, HEALTH_MAX = 40.0;
    @Unique
    double currentHealth;

    @Unique
    void healthCaseIncrement(PlayerEntity player) {
        if (player$isCardActive(Temperance.class)) {
            if (firstLoop) {
                currentHealth = DEFAULT_MAX_HEALTH;
                firstLoop = false;
            }
            if (currentHealth < HEALTH_MAX) {
                currentHealth += HEALTH_INCREMENT;
                Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(currentHealth);
            }
        }
    }

    @Unique
    void xpCaseIncrement(PlayerEntity player) {
        if (player$isCardActive(TheHierophant.class)) {
            player.experienceLevel++;
        }
    }

    @Unique
    void resetHealth(PlayerEntity player) {
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(DEFAULT_MAX_HEALTH);
        currentHealth = DEFAULT_MAX_HEALTH;
        if (player.getHealth() > DEFAULT_MAX_HEALTH) {
            player.setHealth((float) DEFAULT_MAX_HEALTH);
        }
    }

    @Unique
    void setXpCase(PlayerEntity player, int level) {
        player.experienceLevel = level;
    }

    @Override
    public void player$checkInventory(PlayerEntity player) {
        cardsInInventory.clear();
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() instanceof CardManager) {
                cardsInInventory.add(stack);
            }
        }
        player$activateOrDeactivateCards(player);
    }

    @Override
    public void player$activateOrDeactivateCards(PlayerEntity player) {
        activeCards.clear();
        for (ItemStack stack : cardsInInventory) {
            if (stack.getOrCreateNbt().getBoolean("isActive")) {
                activeCards.add(stack);
                ((CardManager) stack.getItem()).activateAbility(player);
            } else {
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
    public Set<Item> player$EmperorGoldItems() {
        return goldItems;
    }

    @Override
    public boolean player$isCardActive(Class<?> card) {
        boolean isCardActive = false;
        for (ItemStack stack : activeCards) {
            if (stack.getItem().getClass().equals(card)) {
                isCardActive = true;
                break;
            }
        }
        return isCardActive;
    }

    @Override
    public boolean player$isEmperorInInventory() {
        boolean isEmperorInInventory = false;
        for (ItemStack stack : cardsInInventory) {
            if (stack.getItem().getClass().equals(TheEmperor.class)) {
                isEmperorInInventory = true;
                break;
            }
        }
        return isEmperorInInventory;
    }

    @Override
    public void player$clearEmperorEffect() {
        if (!player$isEmperorInInventory()) {
            TheEmperor.unbreakableStacks.clear();
        }
    }

    @Override
    public void player$increaseHealthOrXp(PlayerEntity player, int type) {
        switch (type) {
            case 0:
                healthCaseIncrement(player);
                break;
            case 1:
                xpCaseIncrement(player);
                break;
        }
    }

    @Override
    public void player$resetHealth(PlayerEntity player) {
        if (player.isDead() || !player$isCardActive(Temperance.class)) {
            resetHealth(player);
            firstLoop = true;
        }
    }

    @Override
    public void player$setHealthOrXp(PlayerEntity player, int type) {
        int level = player.experienceLevel = 7;
        switch (type) {
            case 0:
                resetHealth(player);
                break;
            case 1:
                setXpCase(player, level);
                break;
        }
    }
}
