package me.safeliquids.mcmods.punch.config;

public class CustomIndicatorSizes {
    // === NESTED TYPES =======================================================
    public static class Size {
        public final int width;
        public final int height;

        public Size() {
            width = height = -1;
        }

        public Size(int w, int h) {
            width = w;
            height = h;
        }
    }

    // === DEFAULTS (STATIC) ==================================================
    public static final boolean DEFAULT_ENABLED = true;
    public static final int DEFAULT_TARGET_PARTIAL_WIDTH = 32;
    public static final int DEFAULT_TARGET_PARTIAL_HEIGHT = 8;
    public static final int DEFAULT_TARGET_FULL_WIDTH = 32;
    public static final int DEFAULT_TARGET_FULL_HEIGHT = 32;
    public static final int DEFAULT_NORMAL_PARTIAL_WIDTH = 16;
    public static final int DEFAULT_NORMAL_PARTIAL_HEIGHT = 4;

    // === PUBLIC MEMBERS =====================================================
    public final boolean enabled;
    public final Size target_partial;
    public final Size target_full;
    public final Size normal_partial;

    // === PUBLIC METHODS =====================================================
    public CustomIndicatorSizes() {
        enabled = DEFAULT_ENABLED;
        target_partial = new Size(DEFAULT_TARGET_PARTIAL_WIDTH, DEFAULT_TARGET_PARTIAL_HEIGHT);
        target_full = new Size(DEFAULT_TARGET_FULL_WIDTH, DEFAULT_TARGET_FULL_HEIGHT);
        normal_partial = new Size(DEFAULT_NORMAL_PARTIAL_WIDTH, DEFAULT_NORMAL_PARTIAL_HEIGHT);
    }

    public CustomIndicatorSizes(boolean is_enabled, Size t_partial, Size t_full, Size n_partial) {
        enabled = is_enabled;
        target_partial = t_partial;
        target_full = t_full;
        normal_partial = n_partial;
    }

    public static CustomIndicatorSizes sanitize(CustomIndicatorSizes other) {
        if (other == null) {
            return new CustomIndicatorSizes();
        }

        Size sanitized_target_partial = sanitizeSize(
            other.target_partial, DEFAULT_TARGET_PARTIAL_WIDTH, DEFAULT_TARGET_PARTIAL_HEIGHT);
        Size sanitized_target_full = sanitizeSize(
            other.target_full, DEFAULT_TARGET_FULL_WIDTH, DEFAULT_TARGET_FULL_HEIGHT);
        Size sanitized_normal_partial = sanitizeSize(
            other.normal_partial, DEFAULT_NORMAL_PARTIAL_WIDTH, DEFAULT_NORMAL_PARTIAL_HEIGHT);
        if (sanitized_target_partial == other.target_partial 
                && sanitized_target_full == other.target_full && sanitized_normal_partial == other.normal_partial)
        {
            return other;
        }

        return new CustomIndicatorSizes(
            other.enabled, sanitized_target_partial, sanitized_target_full, sanitized_normal_partial);
    }

    static Size sanitizeSize(Size size, int default_width, int default_height) {
        if (size == null) {
            return new Size(default_width, default_height);
        }
        if (size.width > 0 && size.height > 0) {
            return size;
        }
        int sanitized_width = size.width > 0 ? size.width : default_width;
        int sanitized_height = size.height > 0 ? size.height : default_height;
        return new Size(sanitized_width, sanitized_height);
    }
}



