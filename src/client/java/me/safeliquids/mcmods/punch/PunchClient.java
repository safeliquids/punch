package me.safeliquids.mcmods.punch;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PunchClient implements ClientModInitializer {
    private static boolean doModifyCrosshairRendering = true;
    private static final Logger LOGGER = LoggerFactory.getLogger("punch");
    @Override
    public void onInitializeClient() {
        doModifyCrosshairRendering = true;
        LOGGER.info("loading punch mod");
    }

    public static boolean shouldModifyCrosshairRendering() {
        return doModifyCrosshairRendering;
    }
}
