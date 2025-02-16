package me.safeliquids.mcmods.punch.mixin.client;

import me.safeliquids.mcmods.punch.PunchClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(InGameHud.class)
public abstract class CrosshairRenderingMixin {
    /**
     * Allows showing the "full crosshair" texture regardless of attack speed.
     * <p>
     * The client renders the crosshair in a special way if
     * 1. the player is looking at a living entity,
     * 2. this entity is currently alive,
     * 3. the attack cooldown is fully recovered, and
     * 4. the player's current attack speed is less than 4.
     * <p>
     * The last condition corresponds to checking if the time T (in ticks) it takes for the attack cooldown to fully
     * recover is more than 5. (NB that, in the yarn mappings, this time is queried using the incorrectly named method
     * `getAttackCooldownProgressPerTick`.) This callback removes that last condition by replacing the obtained time T
     * with 100 (which is always more than 5).
     *
     * @param instance the player entity
     * @param original something idk
     * @return always 100.0f
     */
    @WrapOperation(
        method="renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgressPerTick()F"),
        allow=1,
        require=1)
    private float onGetAttackCooldownProgressPerTick(ClientPlayerEntity instance, Operation<Float> original) {
        return 100f;
    }

    @WrapOperation(
        method="renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
            ordinal=1),
        require=1,
        allow=1)
    private void onDrawFullAttackIndicator(
        DrawContext instance, Identifier texture, int x, int y, int width,  int height, Operation<Void> original)
    {
        final int w = PunchClient.getTargetAttackIndicatorWidth();
        final int adjusted_x = x + 8 - (w / 2);
        original.call(instance, texture, adjusted_x, y, w, w);
    }

    @WrapOperation(
        method="renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
            ordinal=2),
        require=1,
        allow=1)
    private void onDrawAttackIndicatorBackground(
        DrawContext instance, Identifier texture, int x, int y, int width,  int height, Operation<Void> original)
    {
        final MinecraftClient client = ((IMinecraftClientAccessor) this).getClient();
        if (client.targetedEntity != null && client.targetedEntity instanceof LivingEntity) {
            width = PunchClient.getTargetAttackIndicatorWidth();
            height = PunchClient.getTargetAttackIndicatorHeight();
            x = x + 8 - (width / 2);
        }
        original.call(instance, texture, x, y, width, height);
    }

    @WrapOperation(
        method="renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V",
            ordinal=0),
        require=1,
        allow=1)
    private void onDrawAttackIndicatorForeground(
        DrawContext instance, Identifier texture, int i, int j, int k, int l, int x, int y, int width, int height,
        Operation<Void> original)
    {
        final MinecraftClient client = ((IMinecraftClientAccessor) this).getClient();
        if (client.targetedEntity != null && client.targetedEntity instanceof LivingEntity) {
            final int w = PunchClient.getTargetAttackIndicatorWidth();
            final int h = PunchClient.getTargetAttackIndicatorHeight();
            x = x + 8 - (w / 2);
            i = w;
            j = h;
            final float f = client.player.getAttackCooldownProgress(0.0F);
            width = (int) (f * w);
            height = h;
        }
        original.call(instance, texture, i, j, k, l, x, y, width, height);
    }
}
