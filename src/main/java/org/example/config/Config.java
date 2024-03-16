package org.example.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("F:\\1_Programming\\Just_coding\\2024_intellij\\transport_aplication_analyser\\config\\file.properties");
    private static final Properties PROPERTIES = new Properties();

    public static Properties getProperties() {
        if (PROPERTIES.isEmpty()) {
            try {
                PROPERTIES.load(new FileReader(PROPS));
            } catch (IOException e) {
                throw new RuntimeException("Invalid config file " + PROPS.getAbsolutePath());
            }
        }
        return PROPERTIES;
    }
}



