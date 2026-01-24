package me.safeliquids.mcmods.punch.config;

public class CustomIndicatorSizes {
    // === NESTED TYPES =======================================================
    public static class Size {
        public final int mWidth;
        public final int mHeight;

        public Size() {
            mWidth = mHeight = -1;
        }

        public Size(int w, int h) {
            mWidth = w;
            mHeight = h;
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
    public final boolean mEnabled;
    public final Size mTargetPartial;
    public final Size mTargetFull;
    public final Size mNormalPartial;

    // === PUBLIC METHODS =====================================================
    public CustomIndicatorSizes() {
        mEnabled = DEFAULT_ENABLED;
        mTargetPartial = new Size(DEFAULT_TARGET_PARTIAL_WIDTH, DEFAULT_TARGET_PARTIAL_HEIGHT);
        mTargetFull = new Size(DEFAULT_TARGET_FULL_WIDTH, DEFAULT_TARGET_FULL_HEIGHT);
        mNormalPartial = new Size(DEFAULT_NORMAL_PARTIAL_WIDTH, DEFAULT_NORMAL_PARTIAL_HEIGHT);
    }

    public CustomIndicatorSizes(boolean is_enabled, Size t_partial, Size t_full, Size n_partial) {
        mEnabled = is_enabled;
        mTargetPartial = t_partial;
        mTargetFull = t_full;
        mNormalPartial = n_partial;
    }

    public static CustomIndicatorSizes sanitize(CustomIndicatorSizes other) {
        if (other == null) {
            return new CustomIndicatorSizes();
        }

        Size sanitized_target_partial = sanitizeSize(
            other.mTargetPartial, DEFAULT_TARGET_PARTIAL_WIDTH, DEFAULT_TARGET_PARTIAL_HEIGHT);
        Size sanitized_target_full = sanitizeSize(
            other.mTargetFull, DEFAULT_TARGET_FULL_WIDTH, DEFAULT_TARGET_FULL_HEIGHT);
        Size sanitized_normal_partial = sanitizeSize(
            other.mNormalPartial, DEFAULT_NORMAL_PARTIAL_WIDTH, DEFAULT_NORMAL_PARTIAL_HEIGHT);
        if (sanitized_target_partial == other.mTargetPartial 
                && sanitized_target_full == other.mTargetFull && sanitized_normal_partial == other.mNormalPartial)
        {
            return other;
        }

        return new CustomIndicatorSizes(
            other.mEnabled, sanitized_target_partial, sanitized_target_full, sanitized_normal_partial);
    }

    static Size sanitizeSize(Size size, int default_width, int default_height) {
        if (size == null) {
            return new Size(default_width, default_height);
        }
        if (size.mWidth > 0 && size.mHeight > 0) {
            return size;
        }
        int sanitized_width = size.mWidth > 0 ? size.mWidth : default_width;
        int sanitized_height = size.mHeight > 0 ? size.mHeight : default_height;
        return new Size(sanitized_width, sanitized_height);
    }
}



