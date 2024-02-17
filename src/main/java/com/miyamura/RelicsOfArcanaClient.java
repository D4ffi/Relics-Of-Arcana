package com.miyamura;

import com.miyamura.event.KeyInputHandler;
import com.miyamura.networking.ModNetworking;
import net.fabricmc.api.ClientModInitializer;

public class RelicsOfArcanaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModNetworking.registerS2CPackets();
    }
}
