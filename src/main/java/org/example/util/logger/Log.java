package org.example.util.logger;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    private static final Logger logger = LogManager.getLogger();
    public static void info(String message) {
        logger.info(message);
    }

}
