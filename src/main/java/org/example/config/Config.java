package org.example.config;

import org.example.util.logger.Log;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class Config {
    // private static final File PROPS = new File("file.properties");
    private static final Properties PROPERTIES = new Properties();

    public static Properties getProperties() {
        File file = null;
        Reader reader;
        if (PROPERTIES.isEmpty()) {
            Log.info("(Config) 1. try to load PROPERTIES");
            String fileName = "file.properties";
            InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(fileName);
            reader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
            try {
                PROPERTIES.load(reader);
            } catch (IOException e) {
                Log.info("(Config) 2. Exception while getting properties object" + e);
                throw new RuntimeException("Invalid config file");
            }
        }
        return PROPERTIES;
    }
}



