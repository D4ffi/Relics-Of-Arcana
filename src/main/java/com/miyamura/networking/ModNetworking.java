package com.miyamura.networking;

import com.miyamura.RelicsOfArcana;
import com.miyamura.networking.packets.ExampleC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModNetworking {

    public static final Identifier TEST_PACKET = new Identifier(RelicsOfArcana.MOD_ID, "test_packet");
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(TEST_PACKET, ExampleC2SPacket::receive);
    }
    public static void registerS2CPackets() {
    }

}
