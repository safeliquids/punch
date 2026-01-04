package me.safeliquids.mcmods.punch;

import net.fabricmc.api.ClientModInitializer;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

public class PunchClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("punch");
    private static final Path CONFIG_FILE_PATH = Path.of(".", "config", "punch.json");
    
    private static final Gson GSON = new Gson();

    private static Config sConfig = null;

    @Override
    public void onInitializeClient() {
        LOGGER.info("loading punch mod");
        // TODO: pretty printing with indents
        sConfig = readConfig();
        if (sConfig == null) {
            sConfig = Config.makeDefault();
            writeConfig(sConfig);
        }
    }

    public static Config getConfig() {
        return sConfig;
    }

    private Config readConfig() {
        if (!canReadConfigFile()) {
            LOGGER.info("Config file is not available");
            return null;
        }
        Config config = null;
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH.toFile())) {
            config = GSON.fromJson(reader, Config.class);
            if (config == null) {
                LOGGER.warn("config file is empty");
            }
        }
        catch ( JsonIOException ex ) {
            LOGGER.warn("error reading from config file");
        }
        catch ( JsonSyntaxException ex ) {
            LOGGER.warn("config file contains something invalid");
        }
        catch ( IOException ex ) {
            LOGGER.warn("error reading from config file");
        }

        if ( config == null ) {
            return null;
        }

        Config sanitizedConfig = config.sanitized();
        if (config != sanitizedConfig) {
            LOGGER.warn("Invalid entries in config file; replacing with defaults");
        }
        return sanitizedConfig;
    }

    private void writeConfig(Config config) {
        if (config == null) {
            return;
        }
        if (!Files.exists(CONFIG_FILE_PATH)) {
            try {
                Files.createDirectories(CONFIG_FILE_PATH.getParent());
            }
            catch (IOException ex) {
                LOGGER.warn("could not create config directory");
                return;
            }
        }
        else if (!Files.isRegularFile(CONFIG_FILE_PATH) || !Files.isWritable(CONFIG_FILE_PATH)) {
            LOGGER.warn("config file exists but is not writable");
            return;
        }

        final String serialized_config = GSON.toJson(config);
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH.toFile())) {
            writer.write(serialized_config);
            writer.write("\n");
        }
        catch (IOException ex) {
            LOGGER.warn("could not write config to file");
        }
    }

    private boolean canReadConfigFile() {
        return Files.exists(CONFIG_FILE_PATH)
            && Files.isRegularFile(CONFIG_FILE_PATH) && Files.isReadable(CONFIG_FILE_PATH);
    }

    private boolean canWriteConfigFile() {
        return Files.exists(CONFIG_FILE_PATH)
            && Files.isRegularFile(CONFIG_FILE_PATH) && Files.isWritable(CONFIG_FILE_PATH);
    }

    public static class Config {
        public final boolean enabled;
        public final boolean show_indicator_with_fast_weapon;
        public final boolean do_custom_indicator_sizes;
        public final int indicator_width;
        public final int indicator_height;
        public final int targeted_indicator_width;
        public final int targeted_indicator_height;

        public Config() {
            enabled = true;
            show_indicator_with_fast_weapon = true;
            do_custom_indicator_sizes = true;
            indicator_width = 16;
            indicator_height = 4;
            targeted_indicator_width = 32;
            targeted_indicator_height = 8;
        }

        public Config(
            boolean is_enabled, boolean fast_weapon, boolean custom_sizes, int width, int height, int t_width,
            int t_height)
        {
            enabled = is_enabled;
            show_indicator_with_fast_weapon = fast_weapon;
            do_custom_indicator_sizes = custom_sizes;
            indicator_width = width > 0 ? width : 1;
            indicator_height = height > 0 ? height : 1;
            targeted_indicator_width = t_width > 0 ? t_width : 1;
            targeted_indicator_height = t_height > 0 ? t_height : 1;
        }

        public Config sanitized() {
            if (indicator_width > 0
                    && indicator_height > 0 && targeted_indicator_width > 0 && targeted_indicator_height > 0) {
                return this;
            }
            return new Config(
                enabled, show_indicator_with_fast_weapon, do_custom_indicator_sizes, indicator_width, indicator_height,
                targeted_indicator_width, targeted_indicator_height);
        }

        public static Config makeDefault() {
            return new Config(true, true, true, 16, 4, 32, 8);
        }
    }
}
