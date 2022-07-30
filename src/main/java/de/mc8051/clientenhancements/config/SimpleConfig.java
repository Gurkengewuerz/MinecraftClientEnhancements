package de.mc8051.clientenhancements.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class SimpleConfig {

    private static final Logger LOGGER = LogManager.getLogger("SimpleConfig");
    private boolean broken = false;
    private final File file;

    private CustomConfig config = new CustomConfig();

    public CustomConfig getConfig() {
        return config;
    }

    public void createConfig() throws IOException {

        // try creating missing files
        file.getParentFile().mkdirs();
        if (Files.notExists(file.toPath()))
            Files.createFile(file.toPath());

        // write default config data
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create();

        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
        gson.toJson(config, writer);
        writer.close();
    }

    private void loadConfig() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(file.toPath());
        config = gson.fromJson(reader, CustomConfig.class);
        reader.close();
    }

    public SimpleConfig(String modID) {
        Path path = FabricLoader.getInstance().getConfigDir();
        file = new File(path.toAbsolutePath().toString(), modID + ".json");

        if (!file.exists()) {
            LOGGER.info(modID + " is missing, generating default one...");

            try {
                createConfig();
            } catch (IOException e) {
                LOGGER.error(modID + " failed to generate!");
                LOGGER.trace(e);
                broken = true;
            }
        }

        if (!broken) {
            try {
                loadConfig();
            } catch (Exception e) {
                LOGGER.error(modID + " failed to load!");
                LOGGER.trace(e);
                broken = true;
            }
        }
    }
}