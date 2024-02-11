package com.miyamura.mixin;

import com.miyamura.Interfaces.IPlayerManagement;
import com.miyamura.Item.Cards.*;
import com.miyamura.Item.ModItems;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;

@Mixin(PlayerEntity.class)
public class CustomPlayerMixin implements IPlayerManagement {

    @Unique
    List<ItemStack> cardsInInventory = new ArrayList<>();
    @Unique
    boolean underTheFoolEffect = false;
    @Unique
    List<ItemStack> activeCards = new ArrayList<>();
    @Unique
    Set<Item> goldItems = new HashSet<>();
    @Unique
    boolean firstHealthLoop, wasEmpressActive = false;
    @Unique
    final double DEFAULT_MAX_HEALTH = 20.0, HEALTH_INCREMENT = 1.0, HEALTH_MAX = 40.0;
    @Unique
    double currentHealthMaxHealth;
    @Unique
    final int XP_MAX = 30, EMPRESS_ARMOR = 14;
    @Unique
    int currentXp;

    @Unique
    void healthCaseIncrement(PlayerEntity player) {
        if (player$isCardActive(Temperance.class)) {
            if (firstHealthLoop) {
                if (player.getMaxHealth() > DEFAULT_MAX_HEALTH) {
                    currentHealthMaxHealth = player.getMaxHealth();
                } else {
                    currentHealthMaxHealth = DEFAULT_MAX_HEALTH;
                }
                firstHealthLoop = false;
            }
            if (currentHealthMaxHealth < HEALTH_MAX) {
                currentHealthMaxHealth += HEALTH_INCREMENT;
                Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(currentHealthMaxHealth);
            }
        }
    }

    @Unique
    void xpCaseIncrement(PlayerEntity player) {
        if (player$isCardActive(TheHierophant.class)) {
            currentXp = player.experienceLevel;
            if (currentXp < XP_MAX) {
                player.experienceLevel++;
            }
        }
    }

    @Unique
    void resetHealth(PlayerEntity player) {
        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(DEFAULT_MAX_HEALTH);
        currentHealthMaxHealth = DEFAULT_MAX_HEALTH;
        if (player.getHealth() > DEFAULT_MAX_HEALTH) {
            player.setHealth((float) DEFAULT_MAX_HEALTH);
        }
    }

    @Override
    public void player$increaseArmor(PlayerEntity player) {
        if (player$isCardActive(TheEmpress.class)) {
            if (player.getArmor() <= 10) {
                Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR)).setBaseValue(EMPRESS_ARMOR);
                wasEmpressActive = true;
            }
        } else if (wasEmpressActive) {
            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR)).setBaseValue(0);
            wasEmpressActive = false;
        }
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
            if (!player$getFoolEffect()) {
                if (stack.getOrCreateNbt().getBoolean("isActive")) {
                    activeCards.add(stack);
                    if (stack.getItem() instanceof TheFool || stack.getItem() instanceof TheLovers ||
                            stack.getItem() instanceof TheSun || stack.getItem() instanceof TheWorld ||
                            stack.getItem() instanceof TheMagician || stack.getItem() instanceof TheHangedMan ||
                            stack.getItem() instanceof Justice || stack.getItem() instanceof Judgement) {
                        if (stack.getOrCreateNbt().getInt("ArcanePower") == 0){
                            stack.getOrCreateNbt().putBoolean("isActive", false);
                            // agrega un cooldown
                            player.getItemCooldownManager().set(stack.getItem(), 20*50);
                        } else{
                            ((CardManager) stack.getItem()).activateAbility(player);
                        }
                    } else {
                        ((CardManager) stack.getItem()).activateAbility(player);
                    }
                } else {
                    ((CardManager) stack.getItem()).deactivateAbility(player);
                }
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
            firstHealthLoop = true;
        }
    }

    @Override
    public void player$setFirstHealthLoopOnConnect(boolean firstHealthLoop) {
        this.firstHealthLoop = firstHealthLoop;
    }

    @Override
    public boolean player$getFoolEffect() {
        return underTheFoolEffect;
    }

    @Override
    public void player$setFoolCancellation(boolean underTheFool) {
        underTheFoolEffect = underTheFool;
    }

    @Override
    public void player$decreaseMana() {
        for (ItemStack stack : activeCards) {
            if (stack.getItem() instanceof TheFool || stack.getItem() instanceof TheLovers ||
                    stack.getItem() instanceof TheSun || stack.getItem() instanceof TheWorld ||
                    stack.getItem() instanceof TheMagician || stack.getItem() instanceof TheHangedMan ||
                    stack.getItem() instanceof Justice || stack.getItem() instanceof Judgement) {
                if (stack.getOrCreateNbt().getInt("ArcanePower") < 0) {
                    stack.getOrCreateNbt().putInt("ArcanePower", 0);
                }
                if (stack.getOrCreateNbt().getInt("ArcanePower") > 0) {
                    stack.getOrCreateNbt().putInt("ArcanePower", stack.getOrCreateNbt().getInt("ArcanePower") - 1);
                    System.out.println(stack.getOrCreateNbt().getInt("ArcanePower"));
                }
            }
        }
    }

    @Override
    public void player$increaseMana() {
        for (ItemStack stack : cardsInInventory) {
            if (stack.getItem() instanceof TheFool || stack.getItem() instanceof TheLovers ||
                    stack.getItem() instanceof TheSun || stack.getItem() instanceof TheWorld ||
                    stack.getItem() instanceof TheMagician || stack.getItem() instanceof TheHangedMan ||
                    stack.getItem() instanceof Justice || stack.getItem() instanceof Judgement) {
                if (stack.getOrCreateNbt().getInt("ArcanePower") > 100) {
                    stack.getOrCreateNbt().putInt("ArcanePower", 100);
                }
                if (!stack.getOrCreateNbt().getBoolean("isActive") && stack.getOrCreateNbt().getInt("ArcanePower") < 100) {
                    stack.getOrCreateNbt().putInt("ArcanePower", stack.getOrCreateNbt().getInt("ArcanePower") + 2);
                }
            }
        }
    }
}
