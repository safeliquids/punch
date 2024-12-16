package me.safeliquids.mcmods.punch;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PunchClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("punch");

    @Override
    public void onInitializeClient() {
        LOGGER.info("loading punch mod");
    }
}
