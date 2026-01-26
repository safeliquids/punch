package me.safeliquids.mcmods.punch.config;

import com.google.gson.Gson;

public class ConfigData {
    // === DEFAULTS (STATIC) ==================================================
    public static final boolean DEFAULT_ENABLED = true;
    public static final boolean DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON = true;

    // === PUBLIC MEMBERS =====================================================
    boolean mEnabled;
    boolean mFastWeapon;
    CustomIndicatorSizes mCustomIndicatorSizes;

    // === PUBLIC METHODS =====================================================
    public ConfigData() {
        mEnabled = DEFAULT_ENABLED;
        mFastWeapon = DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON;
        mCustomIndicatorSizes = new CustomIndicatorSizes();
    }

    public ConfigData(
        boolean is_enabled, boolean fast_weapon, CustomIndicatorSizes custom_sizes)
    {
        mEnabled = is_enabled;
        mFastWeapon = fast_weapon;
        mCustomIndicatorSizes = custom_sizes;
    }

    public boolean getEnabled() {
        return mEnabled;
    }

    public boolean getFastWeapon() {
        return mEnabled;
    }

    public CustomIndicatorSizes getCustomIndicatorSizes() {
        return mCustomIndicatorSizes;
    }

    public static ConfigData sanitize(ConfigData other) {
        if ( other == null ) {
            return new ConfigData();  // idk if this is good at all
        }

        CustomIndicatorSizes sanitized_sizes = CustomIndicatorSizes.sanitize(other.mCustomIndicatorSizes);
        if (sanitized_sizes == other.mCustomIndicatorSizes) {
            return other;
        }

        return new ConfigData(other.mEnabled, other.mFastWeapon, sanitized_sizes);
    }
}