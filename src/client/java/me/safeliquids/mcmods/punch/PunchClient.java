package me.safeliquids.mcmods.punch;

import net.fabricmc.api.ClientModInitializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

public class PunchClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("punch");
    private static final Path CONFIG_FILE_PATH = Path.of(".", "config", "punch.json");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Config sConfig = null;

    @Override
    public void onInitializeClient() {
        LOGGER.info("loading punch mod");
        // pretty printing
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
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
        Config config;
        try {
            config = MAPPER.readValue(CONFIG_FILE_PATH.toFile(), Config.class);
        }
        // jackson-databind documentation says IOException is raised when a low-level I/O issue occurs, but that is
        // not true. IOException is actually raised even when the file is invalid JSON or when there is a problem
        // with constructing the POJO (e.g. when the target class does not have a parameter-less constructor)
        catch (IOException ex) {
            LOGGER.warn("ioexception when reading config file");
            config = null;
        }

        if (config == null) {
            LOGGER.warn("Could not read config from file");
            return null;
        }
        Config sanitizedConfig = config.sanitized();
        if (config != sanitizedConfig) {
            LOGGER.warn("Invalid entries in config file");
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
        try {
            MAPPER.writeValue(CONFIG_FILE_PATH.toFile(), config);
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

    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class Config {
        @JsonProperty("enabled")
        public final boolean mEnabled;
        @JsonProperty("show_indicator_with_fast_weapon")
        public final boolean mShowIndicatorWithFastWeapon;
        @JsonProperty("do_custom_indicator_sizes")
        public final boolean mDoCustomIndicatorSizes;
        @JsonProperty("indicator_width")
        public final int mIndicatorWidth;
        @JsonProperty("indicator_height")
        public final int mIndicatorHeight;
        @JsonProperty("targeted_indicator_width")
        public final int mTargetedIndicatorWidth;
        @JsonProperty("targeted_indicator_height")
        public final int mTargetedIndicatorHeight;

        public Config() {
            mEnabled = true;
            mShowIndicatorWithFastWeapon = true;
            mDoCustomIndicatorSizes = true;
            mIndicatorWidth = 16;
            mIndicatorHeight = 4;
            mTargetedIndicatorWidth = 32;
            mTargetedIndicatorHeight = 8;
        }

        public Config(
            boolean enabled, boolean fast_weapon, boolean custom_sizes, int width, int height, int t_width,
            int t_height)
        {
            mEnabled = enabled;
            mShowIndicatorWithFastWeapon = fast_weapon;
            mDoCustomIndicatorSizes = custom_sizes;
            mIndicatorWidth = width > 0 ? width : 1;
            mIndicatorHeight = height > 0 ? height : 1;
            mTargetedIndicatorWidth = t_width > 0 ? t_width : 1;
            mTargetedIndicatorHeight = t_height > 0 ? t_height : 1;
        }

        public Config sanitized() {
            if (mIndicatorWidth > 0
                    && mIndicatorHeight > 0 && mTargetedIndicatorWidth > 0 && mTargetedIndicatorHeight > 0) {
                return this;
            }
            return new Config(
                mEnabled, mShowIndicatorWithFastWeapon, mDoCustomIndicatorSizes, mIndicatorWidth, mIndicatorHeight,
                mTargetedIndicatorWidth, mTargetedIndicatorHeight);
        }

        public static Config makeDefault() {
            return new Config(true, true, true, 16, 4, 32, 8);
        }
    }
}
