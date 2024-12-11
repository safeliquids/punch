package me.safeliquids.mcmods.punch.mixin.client;

import me.safeliquids.mcmods.punch.PunchClient;

import net.minecraft.client.gui.hud.InGameHud;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(InGameHud.class)
public abstract class CrosshairRenderingMixin {
    /**
     * Allows showing the "full crosshair" texture regardless of attack speed.
     *
     * The client renders the crosshair in a special way if
     * 1. the player is looking at a living entity,
     * 2. this entity is currently alive,
     * 3. the attack cooldown is fully recovered, and
     * 4. the player's current attack speed is less than 4.
     *
     * The last condition corresponds to checking if the time T (in ticks) it takes for the attack cooldown to fully
     * recover is more than 5. (NB that, in the yarn mappings, this time is queried using the incorrectly named method
     * `getAttackCooldownProgressPerTick`.) This callback removes that last condition by replacing the constant with -1.
     *
     * @param original original value of T
     * @return substitute value of T if rendering should be modified, or the original
     */
    @ModifyConstant(
        method="Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/gui/DrawContext;)V",
        constant=@Constant(floatValue=5.0f),
        allow=1,
        require=1)
    private float inRenderCrosshair(float original) {
        // as of now this method always returns true
        if (PunchClient.shouldModifyCrosshairRendering()) {
            return -1.0f;
        }
        return original;
    }
}
