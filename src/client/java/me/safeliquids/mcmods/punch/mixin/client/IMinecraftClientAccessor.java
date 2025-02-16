package me.safeliquids.mcmods.punch.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;

@Mixin(
    value=InGameHud.class)
public interface IMinecraftClientAccessor {
    @Accessor(
        value="client")
    MinecraftClient getClient();

}
