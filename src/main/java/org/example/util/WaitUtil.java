package org.example.util;

import org.example.config.Config;
import org.example.web.driver.BrowserUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtil {
    private static final int BROWSER_WAIT_VALUE = Config.getProperties("wait");

    public WebDriverWait getWait() {
        return new WebDriverWait(BrowserUtil.getDriver(), Duration.ofSeconds(BROWSER_WAIT_VALUE));
    }

    public void waitForElementDisplayed(WebElement baseElement) {
        getWait().until(ExpectedConditions.visibilityOf(baseElement));
    }

    public void waitForElementsDisplayed(List<WebElement> baseElement) {
        getWait().until(ExpectedConditions.visibilityOfAllElements(baseElement));
    }

    public void waitToBeClickable(WebElement baseElement) {
        getWait().until(ExpectedConditions.elementToBeClickable(baseElement));
    }

    public void waitForLocatorPresence(By locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
