package me.safeliquids.mcmods.punch.mixin.client;

import me.safeliquids.mcmods.punch.PunchClient;
import me.safeliquids.mcmods.punch.config.ConfigData;
import me.safeliquids.mcmods.punch.config.CustomIndicatorSizes;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class CrosshairRenderingMixin {
    private ConfigData mConfig = null;
    private CustomIndicatorSizes mCis;
    private CustomIndicatorSizes.Size mTargetPartial;
    private CustomIndicatorSizes.Size mTargetFull;
    private CustomIndicatorSizes.Size mNormalPartial;

    /**
     * Caches current mod configuration.
     */
    @Inject(
        method="renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V",
        at=@At("HEAD"))
    private void onRenderCrosshair(DrawContext context, CallbackInfo ci) {
        mConfig = PunchClient.getConfig();
        mCis = mConfig.getCustomIndicatorSizes();
        mTargetPartial = mCis.getTargetPartial();
        mTargetFull = mCis.getTargetFull();
        mNormalPartial = mCis.getNormalPartial();
    }

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
        if (mConfig != null && mConfig.getFastWeapon()) {
            return 100f;
        }
        return original.call(instance);
    }

    /**
     * Custom size of full attack indicator.
     *
     * If enabled, changes the size of the "full attack indicator" texture as it is drawn on the HUD. This texture is
     * only drawn when an entity is targeted so the TargetedIndicatorWidth is always used.
     */
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
        if (mConfig == null || !mConfig.getEnabled()) {
            original.call(instance, texture, x, y, width, height);
            return;
        }
        if (!mCis.getEnabled()) {
            original.call(instance, texture, x, y, width, height);
            return;
        }
        width = mTargetFull.getWidth();
        height = mTargetFull.getHeight();
        // The x-position of the attack indicator is moved to the left of the center of the screen by half its
        // width. This line removes the hard-coded offset in Minecraft's code (8 because the vanilla width is 16)
        // and replaces it with a corresponding offset based on the custom width. (Here that is
        // TargetedIndicatorWidth since the "full" texture is only shown when targeting something. In the other
        // methods it is either the normal or targeted width.)
        x = x + 8 - (width / 2);
        original.call(instance, texture, x, y, width, height);
    }

    /**
     * Custom drawing of the background of the partial attack indicator.
     *
     * If enabled, changes the size of the partial attack indicator texture on the HUD, specifically its background.
     * The size also depends on if an entity is targeted.
     */
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
        if (mConfig == null || !mConfig.getEnabled()) {
            original.call(instance, texture, x, y, width, height);
            return;
        }
        if (!mCis.getEnabled()) {
            original.call(instance, texture, x, y, width, height);
            return;
        }

        CustomIndicatorSizes.Size size;
        final MinecraftClient client = ((IMinecraftClientAccessor) this).getClient();
        if (client.targetedEntity != null && client.targetedEntity instanceof LivingEntity) {
            size = mTargetPartial;
        }
        else {
            size = mNormalPartial;
        }
        width = size.getWidth();
        height = size.getHeight();
        x = x + 8 - (width / 2);
        original.call(instance, texture, x, y, width, height);
    }

    /**
     * Custom drawing of the partial attack indicator.
     *
     * If enabled, changes the size of the partial attack indicator texture on the HUD. The overall size depends on
     * if an entity is targeted. The width of the part that is drawn relative to the width of the background also
     * depends on the attack cooldown progress.
     */
    @WrapOperation(
        method="renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V",
        at=@At(
            value="INVOKE",
            target="Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIII)V",
            ordinal=0),
        require=1,
        allow=1)
    private void onDrawAttackIndicatorForeground(
        DrawContext instance, Identifier texture, int texture_width, int texture_height, int start_u, int start_v,
        int x, int y, int width, int height, Operation<Void> original)
    {
        if (mConfig == null || !mConfig.getEnabled()) {
            original.call(instance, texture, texture_width, texture_height, start_u, start_v, x, y, width, height);
            return;
        }
        if (!mCis.getEnabled()) {
            original.call(instance, texture, texture_width, texture_height, start_u, start_v, x, y, width, height);
            return;
        }

        CustomIndicatorSizes.Size size;
        final MinecraftClient client = ((IMinecraftClientAccessor) this).getClient();
        if (client.targetedEntity != null && client.targetedEntity instanceof LivingEntity) {
            size = mTargetPartial;
        }
        else {
            size = mNormalPartial;
        }
        texture_width = size.getWidth();
        texture_height = size.getHeight();
        x = x + 8 - (texture_width / 2);
        final float f = client.player.getAttackCooldownProgress(0.0F);
        width = (int) (f * texture_width);
        height = texture_height;
        original.call(instance, texture, texture_width, texture_height, start_u, start_v, x, y, width, height);
    }
}
