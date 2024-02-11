package com.miyamura.mixin;

import com.miyamura.Interfaces.IPlayerManagement;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class JoinMixin implements IPlayerManagement{
    @Inject(method = "onPlayerConnect", at = @At("HEAD"))
    private void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        IPlayerManagement playerManagement = (IPlayerManagement) player;
        playerManagement.player$setGoldItems();
        playerManagement.player$setFirstHealthLoopOnConnect(true);
    }
}
