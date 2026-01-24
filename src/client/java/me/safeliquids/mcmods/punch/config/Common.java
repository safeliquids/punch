package me.safeliquids.mcmods.punch.config;

import com.google.gson.*;

public class Common {
    public static int getIntFieldOrDefault(JsonObject object, String key, int def) {
        if (object == null || !object.has(key)) {
            return def;
        }
        JsonElement elem = object.get(key);
        if (!elem.isJsonPrimitive()) {
            return def;
        }
        JsonPrimitive prim = elem.getAsJsonPrimitive();
        if (!prim.isNumber()) {
            return def;
        }
        return prim.getAsInt();
    }

    public static boolean getBooleanFieldOrDefault(JsonObject object, String key, boolean def) {
        if (object == null || !object.has(key)) {
            return def;
        }
        JsonElement elem = object.get(key);
        if (!elem.isJsonPrimitive()) {
            return def;
        }
        JsonPrimitive prim = elem.getAsJsonPrimitive();
        if (!prim.isBoolean()) {
            return def;
        }
        return prim.getAsBoolean();
    }

    public static JsonObject getObjectFieldOrNull(JsonObject object, String key) {
        if (object == null || !object.has(key)) {
            return null;
        }
        JsonElement elem = object.get(key);
        if (!elem.isJsonObject()) {
            return null;
        }
        return elem.getAsJsonObject();
    }
}