package com.miyamura.mixin;

import com.miyamura.Interfaces.IEmperorItemStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.miyamura.Item.Cards.TheEmperor.unbreakableStacks;

@Mixin(ItemStack.class)
public class UnbreakableMixin implements IEmperorItemStack {
    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    public void makeUnbreakable(CallbackInfoReturnable<Boolean> cir) {
        for (ItemStack stack : emperor$unbreakableStacks()) {
            cir.setReturnValue(false);
        }
    }
    @Override
    public List<ItemStack> emperor$unbreakableStacks() {
        return unbreakableStacks;
    }
}
