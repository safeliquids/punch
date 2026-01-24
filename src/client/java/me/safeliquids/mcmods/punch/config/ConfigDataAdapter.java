package me.safeliquids.mcmods.punch.config;

import com.google.gson.*;

import java.lang.reflect.Type;

// TODO: renaming members of config data classes to something more sensible

public class ConfigDataAdapter implements JsonSerializer<ConfigData>, JsonDeserializer<ConfigData> {
    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_FASTWEAPON = "show_indicator_with_fast_weapon";
    public static final String KEY_INDICATOR_SIZES = "custom_indicator_sizes";

    public JsonElement serialize(ConfigData config_data, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty(KEY_ENABLED, config_data.enabled);
        result.addProperty(KEY_FASTWEAPON, config_data.show_indicator_with_fast_weapon);

        JsonElement custom_sizes = context.serialize(
            config_data.custom_indicator_sizes, CustomIndicatorSizes.class);
        result.add(KEY_INDICATOR_SIZES, custom_sizes);
        return result;
    }

    public ConfigData deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        if (json == null || !json.isJsonObject()) {
            return new ConfigData();
        }
        JsonObject obj = json.getAsJsonObject();
        boolean enabled = Common.getBooleanFieldOrDefault(obj, KEY_ENABLED, ConfigData.DEFAULT_ENABLED);
        boolean fast_weapon = Common.getBooleanFieldOrDefault(
            obj, KEY_FASTWEAPON, ConfigData.DEFAULT_SHOW_INDICATOR_WITH_FAST_WEAPON);
        CustomIndicatorSizes sizes = context.deserialize(obj.get(KEY_INDICATOR_SIZES), CustomIndicatorSizes.class);
        return new ConfigData(enabled, fast_weapon, sizes);
    }
}
