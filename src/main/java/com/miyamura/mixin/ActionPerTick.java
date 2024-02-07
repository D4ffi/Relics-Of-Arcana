package com.miyamura.mixin;

import com.miyamura.Interfaces.IPlayerManagement;
import com.miyamura.RelicsOfArcana;
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
    int temperance_interval = 0;
    @Unique
    int hierophant_interval = 0;
    @Unique
    PlayerEntity player = (PlayerEntity) (Object) this;
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        try {
            if (tickCounter % UPDATE_INTERVAL == 0) {
                    this.player$checkInventory(player);
                    this.player$clearEmperorEffect();
                    this.player$resetHealth((player));
                    this.player$increaseArmor(player);
                    temperance_interval++;
                    hierophant_interval++;
                    if (temperance_interval == 3){
                        this.player$increaseHealthOrXp(player, 0);
                        temperance_interval = 0;
                    }
                    if (hierophant_interval == 20){
                        this.player$increaseHealthOrXp(player, 1);
                        hierophant_interval = 0;
                    }
                    tickCounter = 0;
                }
        } catch (Exception e) {
            RelicsOfArcana.LOGGER.error("Error in tick method: " + e);
            tickCounter = 0;
        }
        tickCounter++;
    }
}
