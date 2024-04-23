package org.example.config;

import org.example.util.logger.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("F:\\1_Programming\\Just_coding\\2024_intellij\\transport_application_analyser\\file.properties");
    private static final Properties PROPERTIES = new Properties();

    public static Properties getProperties() {
        Log.info("(Config) 1. is gonna get Properties Object");
        if (PROPERTIES.isEmpty()) {
            try {
                Log.info("(Config) 2. try to load PROPERTIES");

                PROPERTIES.load(new FileReader(PROPS
                        , StandardCharsets.UTF_8
                ));
            } catch (IOException e) {
                Log.info("(Config) 3. Exception while getting properties object" + e);
                throw new RuntimeException("Invalid config file " + PROPS.getAbsolutePath());
            }
        }
        return PROPERTIES;
    }
}



