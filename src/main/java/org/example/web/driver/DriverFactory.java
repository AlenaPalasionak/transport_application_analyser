package org.example.web.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.config.Config;
import org.openqa.selenium.WebDriver;

public class DriverFactory {
    private static WebDriver driverInstance;
    private static final String BROWSER_NAME = Config.getProperties().getProperty("browser");

    public static WebDriver getDriverInstance() {
        if (driverInstance == null) {
            String browserName = BROWSER_NAME;
            switch (browserName) {
                case "firefox" -> {
                    driverInstance = WebDriverManager.firefoxdriver().create();
                }
                case "chrome" -> {
                    driverInstance = WebDriverManager.chromedriver().create();
                }
                default -> throw new IllegalStateException("Unexpected value: " + browserName);
            }
        }
        return driverInstance;
    }

    public static void closeDriver() {
        driverInstance.quit();
        driverInstance = null;
    }
}