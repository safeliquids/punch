package me.safeliquids.mcmods.punch;

public class ConfigData {
    public static final boolean DEFAULT_ENABLED = true;
    public static final boolean DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON = true;
    public static final boolean DEFAULT_DO_CUSTOM_INDICATOR_SIZES = true;
    public static final int DEFAULT_INDICATOR_WIDTH = 16;
    public static final int DEFAULT_INDICATOR_HEIGHT = 4;
    public static final int DEFAULT_TARGETED_INDICATOR_WIDTH = 32;
    public static final int DEFAULT_TARGETED_INDICATOR_HEIGHT = 8;

    public final boolean enabled;
    public final boolean show_indicator_with_fast_weapon;
    public final boolean do_custom_indicator_sizes;
    public final int indicator_width;
    public final int indicator_height;
    public final int targeted_indicator_width;
    public final int targeted_indicator_height;

    public ConfigData() {
        enabled = DEFAULT_ENABLED;
        show_indicator_with_fast_weapon = DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON;
        do_custom_indicator_sizes = DEFAULT_DO_CUSTOM_INDICATOR_SIZES;
        indicator_width = DEFAULT_INDICATOR_WIDTH;
        indicator_height = DEFAULT_INDICATOR_HEIGHT;
        targeted_indicator_width = DEFAULT_TARGETED_INDICATOR_WIDTH;
        targeted_indicator_height = DEFAULT_TARGETED_INDICATOR_HEIGHT;
    }

    public ConfigData(
        boolean is_enabled, boolean fast_weapon, boolean custom_sizes, int width, int height, int t_width,
        int t_height)
    {
        enabled = is_enabled;
        show_indicator_with_fast_weapon = fast_weapon;
        do_custom_indicator_sizes = custom_sizes;
        indicator_width = width;
        indicator_height = height;
        targeted_indicator_width = t_width;
        targeted_indicator_height = t_height;
    }

    public static ConfigData makeDefault() {
        return new ConfigData();
    }

    public static ConfigData sanitize(ConfigData other) {
        if ( other == null ) {
            return makeDefault();  // idk if this is good at all
        }
        int width = other.indicator_width;
        int height = other.indicator_height;
        int t_width = other.targeted_indicator_width;
        int t_height = other.targeted_indicator_height;
        if ( 0 < width && 0 < height && 0 < t_width && 0 < t_height ) {
            return other;
        }

        // sanitize dimensions
        width = width > 0 ? width : 1;
        height = height > 0 ? height : 1;
        t_width = t_width > 0 ? t_width : 1;
        t_height = t_height > 0 ? t_height : 1;

        return new ConfigData(
            other.enabled, other.show_indicator_with_fast_weapon,
            other.do_custom_indicator_sizes, width, height, t_width, t_height);
    }
}