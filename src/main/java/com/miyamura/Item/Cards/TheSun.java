package com.miyamura.Item.Cards;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TheSun extends CardManager{
    public TheSun(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("description.theSun"));
        NbtCompound nbt = stack.getOrCreateNbt();
        String arcanePowerString = Integer.toString(nbt.getInt("ArcanePower"));
        MutableText mutableText = Text.translatable("amount.arcanePower").formatted(Formatting.ITALIC, Formatting.GRAY);
        mutableText.append(arcanePowerString);
        tooltip.add(mutableText);
    }
    @Override
    public  void activateAbility(PlayerEntity player){
        Box box = createBox(player, CardManager.RANGE);
        applyEffect(player,box);
    }
    @Override
    public void applyEffect(PlayerEntity player, Box areaOfEffect){
        List<Entity> entities = player.getWorld().getOtherEntities(player, areaOfEffect);
        for (Entity entity : entities) {

            // Check if there is a clear path between the player and the entity
            Vec3d playerEyePos = player.getEyePos();
            Vec3d entityEyePos = entity.getEyePos();
            HitResult hitResult = player.getWorld().raycast(new RaycastContext(playerEyePos, entityEyePos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));

            // If the raycast hit a block, skip this entity
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                continue;
            }

            if (entity instanceof LivingEntity || entity instanceof MobEntity){
                entity.setOnFireFor(10);
            }
        }
    }
}
