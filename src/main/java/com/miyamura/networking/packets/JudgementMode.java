package com.miyamura.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.mojang.text2speech.Narrator.LOGGER;

public class JudgementMode {

    private static int mode;

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // receives an integer between 0 and 2 to change the judgement mode
        mode = buf.readInt();
    }

    public static int getMode(){
        return mode;
    }
}
