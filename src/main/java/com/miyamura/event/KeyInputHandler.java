package com.miyamura.event;

import com.miyamura.networking.ModNetworking;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String CATEGORY = "key.categories.relicsofarcana";
    public static final String ACTION = "key.relicsofarcana.action";

    public static KeyBinding keyBinding;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                assert client.player != null;
                client.player.sendMessage(Text.of("Key pressed!"), true);
                ClientPlayNetworking.send(ModNetworking.TEST_PACKET, PacketByteBufs.create());
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
