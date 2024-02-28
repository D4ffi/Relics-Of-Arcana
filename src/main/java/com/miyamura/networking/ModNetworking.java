package com.miyamura.networking;

import com.miyamura.RelicsOfArcana;
import com.miyamura.networking.packets.ChangeDevilBuffPacket;
import com.miyamura.networking.packets.JudgementMode;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier JUDGEMENT_MODE = new Identifier(RelicsOfArcana.MOD_ID, "judgement_mode");
    public static final Identifier DEVILS_BUFF = new Identifier(RelicsOfArcana.MOD_ID, "devils_buff");
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(JUDGEMENT_MODE, JudgementMode::receive);
        ServerPlayNetworking.registerGlobalReceiver(DEVILS_BUFF, ChangeDevilBuffPacket::receive);
    }
    public static void registerS2CPackets() {
    }

}
