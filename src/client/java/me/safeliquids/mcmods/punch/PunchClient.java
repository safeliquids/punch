package me.safeliquids.mcmods.punch;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PunchClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("punch");
    private static int sTargetAttackIndicatorWidth = 32;
    private static int sTargetAtackIndicatorHeight = 8;

    @Override
    public void onInitializeClient() {
        LOGGER.info("loading punch mod");
    }

    public static int getTargetAttackIndicatorWidth() {
        return sTargetAttackIndicatorWidth;
    }

    public static int getTargetAttackIndicatorHeight() {
        return sTargetAtackIndicatorHeight;
    }
}
