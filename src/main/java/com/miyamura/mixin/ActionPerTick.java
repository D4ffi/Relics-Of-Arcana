package com.miyamura.mixin;

import com.miyamura.Interfaces.IPlayerManagement;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ActionPerTick implements IPlayerManagement {
    @Unique
    int tickCounter = 0;
    @Unique
    final int UPDATE_INTERVAL = 20;
    @Unique
    final int TEMPERANCE_INTERVAL = 200;
    @Unique
    IPlayerManagement player = (IPlayerManagement) this;
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (tickCounter >= UPDATE_INTERVAL) {
            tickCounter = 0;
            player.player$checkInventory((PlayerEntity) (Object) this);
        }
        tickCounter++;
    }
}
