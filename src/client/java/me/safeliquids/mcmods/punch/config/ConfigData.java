package me.safeliquids.mcmods.punch.config;

import com.google.gson.Gson;

public class ConfigData {
    // === DEFAULTS (STATIC) ==================================================
    public static final boolean DEFAULT_ENABLED = true;
    public static final boolean DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON = true;

    // === PUBLIC MEMBERS =====================================================
    public final boolean enabled;
    public final boolean show_indicator_with_fast_weapon;
    public final CustomIndicatorSizes custom_indicator_sizes;

    // === PUBLIC METHODS =====================================================
    public ConfigData() {
        enabled = DEFAULT_ENABLED;
        show_indicator_with_fast_weapon = DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON;
        custom_indicator_sizes = new CustomIndicatorSizes();
    }

    public ConfigData(
        boolean is_enabled, boolean fast_weapon, CustomIndicatorSizes custom_sizes)
    {
        enabled = is_enabled;
        show_indicator_with_fast_weapon = fast_weapon;
        custom_indicator_sizes = custom_sizes;
    }

    public static ConfigData sanitize(ConfigData other) {
        if ( other == null ) {
            return new ConfigData();  // idk if this is good at all
        }

        CustomIndicatorSizes sanitized_sizes = CustomIndicatorSizes.sanitize(other.custom_indicator_sizes);
        if (sanitized_sizes == other.custom_indicator_sizes) {
            return other;
        }

        return new ConfigData(other.enabled, other.show_indicator_with_fast_weapon, sanitized_sizes);
    }
}