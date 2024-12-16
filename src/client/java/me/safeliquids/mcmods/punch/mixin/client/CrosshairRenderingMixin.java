package me.safeliquids.mcmods.punch.mixin.client;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
}
