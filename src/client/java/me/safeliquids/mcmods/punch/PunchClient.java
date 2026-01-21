package me.safeliquids.mcmods.punch;

import me.safeliquids.mcmods.punch.config.ConfigData;

import net.fabricmc.api.ClientModInitializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    
    private static Gson sGson = null;
    private static ConfigData sConfig = null;

    @Override
    public void onInitializeClient() {
        LOGGER.info("loading punch mod");
        // construct static Gson instance with pretty printing
        sGson = new GsonBuilder().setPrettyPrinting().create();
        sConfig = readConfig();
        if (sConfig == null) {
            sConfig = new ConfigData();
        }
        writeConfig(sConfig);
    }

    public static ConfigData getConfig() {
        return sConfig;
    }

    private ConfigData readConfig() {
        if (!canReadConfigFile()) {
            LOGGER.info("Config file is not available");
            return null;
        }
        ConfigData config = null;
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH.toFile())) {
            config = sGson.fromJson(reader, ConfigData.class);
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

        ConfigData sanitizedConfig = ConfigData.sanitize(config);
        if (config != sanitizedConfig) {
            LOGGER.warn("Invalid entries in config file; replacing with defaults");
        }
        return sanitizedConfig;
    }

    private void writeConfig(ConfigData config) {
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

        final String serialized_config = sGson.toJson(config);
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


}
