package com.miyamura.event;

import com.miyamura.Interfaces.IPlayerManagement;
import com.miyamura.Item.Cards.Judgement;
import com.miyamura.Item.Cards.TheDevil;
import com.miyamura.networking.ModNetworking;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.intellij.lang.annotations.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String CATEGORY = "key.categories.relicsofarcana";
    public static final String ACTION = "key.relicsofarcana.action";

    public static KeyBinding keyBinding;

    public static void registerKeyInputs() {

        Text[] judgementModes = {Text.translatable("message0.judgement"), Text.translatable("message1.judgement"), Text.translatable("message2.judgement")};
        int[] currentJudgementMode = {0};

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                assert client.player != null;

                IPlayerManagement customPlayer = (IPlayerManagement) client.player;

                if (client.player.getInventory().getMainHandStack().getItem() instanceof Judgement){
                    client.player.sendMessage(judgementModes[currentJudgementMode[0]], true);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeInt(currentJudgementMode[0]);
                    ClientPlayNetworking.send(ModNetworking.JUDGEMENT_MODE, buf);
                    if (currentJudgementMode[0] != 2){
                        currentJudgementMode[0]++;
                    } else {
                        currentJudgementMode[0] = 0;
                    }
                }
                if (customPlayer.player$isCardActive(TheDevil.class) && !(client.player.getInventory().getMainHandStack().getItem() instanceof Judgement)){
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBoolean(true);
                    //play sound
                    client.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, -2.0F);
                    ClientPlayNetworking.send(ModNetworking.DEVILS_BUFF, buf);
                }
            }
        });
    }

    public static void register(){
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            ACTION,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            CATEGORY
        ));
        registerKeyInputs();
    }
}
