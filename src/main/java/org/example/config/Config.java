package org.example.config;

import org.example.util.logger.Log;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static final Properties PROPERTIES = new Properties();

    public static Properties getProperties() {
        Reader reader;
        if (PROPERTIES.isEmpty()) {
            String fileName = "file.properties";
            Log.info("(Config) 1. try to load PROPERTIES (properties file name is " + fileName + ")");
            try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(fileName)) {
                reader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                PROPERTIES.load(reader);

            } catch (IOException e) {
                Log.info("(Config) 2. Exception while getting properties object" + e);
                throw new RuntimeException("Invalid config file");
            }
        }
        Log.info("(Config) 3. Properties:  " + PROPERTIES.toString());

        return PROPERTIES;
    }
}



