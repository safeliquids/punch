package me.safeliquids.mcmods.punch.config;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CustomIndicatorSizesAdapter 
    implements JsonSerializer<CustomIndicatorSizes>, JsonDeserializer<CustomIndicatorSizes>
{
    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_TARGET_PARTIAL = "target_partial";
    public static final String KEY_TARGET_FULL = "target_full";
    public static final String KEY_NORMAL_PARTIAL = "normal_partial";
    public static final String KEY_WIDTH = "width";
    public static final String KEY_HEIGHT = "height";

    public JsonElement serialize(CustomIndicatorSizes sizes, Type type_of_sizes, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty(KEY_ENABLED, sizes.enabled);
        result.add(KEY_TARGET_PARTIAL, serializeSize(sizes.target_partial));
        result.add(KEY_TARGET_FULL, serializeSize(sizes.target_full));
        result.add(KEY_NORMAL_PARTIAL, serializeSize(sizes.normal_partial));
        return result;
    }

    public CustomIndicatorSizes deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        if (json == null || !json.isJsonObject()) {
            return new CustomIndicatorSizes();
        }
        JsonObject obj = json.getAsJsonObject();

        boolean enabled = Common.getBooleanFieldOrDefault(obj, KEY_ENABLED, CustomIndicatorSizes.DEFAULT_ENABLED);

        CustomIndicatorSizes.Size target_partial = deserializeSize(
            Common.getObjectFieldOrNull(obj, KEY_TARGET_PARTIAL), CustomIndicatorSizes.DEFAULT_TARGET_PARTIAL_WIDTH,
            CustomIndicatorSizes.DEFAULT_TARGET_PARTIAL_WIDTH);
        CustomIndicatorSizes.Size target_full = deserializeSize(
            Common.getObjectFieldOrNull(obj, KEY_TARGET_FULL), CustomIndicatorSizes.DEFAULT_TARGET_FULL_WIDTH,
            CustomIndicatorSizes.DEFAULT_TARGET_FULL_HEIGHT);
        CustomIndicatorSizes.Size normal_partial = deserializeSize(
            Common.getObjectFieldOrNull(obj, KEY_NORMAL_PARTIAL), CustomIndicatorSizes.DEFAULT_NORMAL_PARTIAL_WIDTH,
            CustomIndicatorSizes.DEFAULT_NORMAL_PARTIAL_HEIGHT);

        return new CustomIndicatorSizes(enabled, target_partial, target_full, normal_partial);
    }

    // === PRIVATE METHODS ====================================================
    JsonElement serializeSize(CustomIndicatorSizes.Size size) {
        JsonObject result = new JsonObject();
        result.addProperty(KEY_WIDTH, size.width);
        result.addProperty(KEY_HEIGHT, size.height);
        return result;
    }

    CustomIndicatorSizes.Size deserializeSize(JsonObject object, int default_width, int default_height) {
        if (object == null) {
            return new CustomIndicatorSizes.Size(default_width, default_height);
        }

        int width = Common.getIntFieldOrDefault(object, KEY_WIDTH, default_width);
        int height = Common.getIntFieldOrDefault(object, KEY_HEIGHT, default_height);

        if (width <= 0) {
            width = default_width;
        }
        if (height <= 0) {
            height = default_height;
        }

        return new CustomIndicatorSizes.Size(width, height);
    }
}

