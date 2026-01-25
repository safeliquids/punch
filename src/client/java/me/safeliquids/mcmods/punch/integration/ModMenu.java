package me.safeliquids.mcmods.punch.integration;

import me.safeliquids.mcmods.punch.config.ConfigData;
import me.safeliquids.mcmods.punch.config.CustomIndicatorSizes;
import me.safeliquids.mcmods.punch.PunchClient;

import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;

import net.fabricmc.loader.api.FabricLoader;

public class ModMenu implements ModMenuApi {
    // === INTERFACE IMPLEMENTATION ===========================================
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        // see if modmenu and cloth-config are available.
        final FabricLoader loader_instance = FabricLoader.getInstance();
        if (loader_instance.isModLoaded("modmenu") && loader_instance.isModLoaded("cloth-config")) {
            return parent -> ClothConfig.buildConfigScreenWithCloth(parent);
        }
        return parent -> null;
    }
}
