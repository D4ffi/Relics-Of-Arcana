package com.miyamura.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ChangeDevilBuffPacket {
    private static boolean wasPressed;

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        wasPressed = buf.readBoolean();

        player.damage(player.getWorld().getDamageSources().magic(), 2.0F);

        double chance = Math.random();
        // player has 1% chance to get wither and blindness
        if (chance < 0.01) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20*10, 2));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20*15, 0));
        }
        // player has additional 4% chance to get darkness
        else if (chance < 0.04) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 20*30, 0));
        }
        // player has additional 5% chance to get resistance
        else if (chance < 0.05) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20*30, 1));
        }
        // player has additional 1% chance to get health boost
        else if (chance < 0.1) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 20*30, 4));
        }
        // player has additional 1% chance to get strength
        else if (chance < 0.15) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20*30, 0));
        }
        // player has additional 25% chance to get speed
        else if (chance < 0.25) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20*30, 1));
        } else if (chance < 0.5) {
            // player has additional 50% chance to get fire resistance
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20*30, 0));
        } else {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20*30, 1));
        }
    }

    public static boolean getWasPressed() {
        return wasPressed;
    }
    public static void setWasPressed(boolean wasPressed) {
        ChangeDevilBuffPacket.wasPressed = wasPressed;
    }
}
