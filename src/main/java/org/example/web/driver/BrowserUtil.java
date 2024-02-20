package org.example.web.driver;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class BrowserUtil {

    public static WebDriver getDriver() {
        return DriverFactory.getDriverInstance();
    }

    public static void close() {
        DriverFactory.closeDriver();
    }

    public static void openUrl(String url) {
        getDriver().get(url);
    }

    public static void maximizeWindow() {
        getDriver().manage().window().maximize();
    }

    public static void closeTab() {
        getDriver().close();
        List<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        if (!tabs.isEmpty()) {
            String winHandleBefore = tabs.get(tabs.size() - 1);
            getDriver().switchTo().window(winHandleBefore);
        }
    }
}

