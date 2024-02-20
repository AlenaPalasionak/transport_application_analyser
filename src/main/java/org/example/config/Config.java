package org.example.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("file.properties");
    private static  Properties PROPERTIES;

    public static Properties getProperties(String key) {
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



