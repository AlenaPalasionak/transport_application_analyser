package org.example.web.base.element;

import org.example.util.WaitUtil;
import org.example.web.driver.BrowserUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class BaseElement {
    protected By uniqueLocator;
    protected String elementName;
    protected WaitUtil waits;

    protected BaseElement(By uniqueLocator, String elementName) {
        this.uniqueLocator = uniqueLocator;
        this.elementName = elementName;
        waits = new WaitUtil();
    }

    protected WebElement getElement() {
        waits.waitForLocatorPresence(uniqueLocator);
        return BrowserUtil.getDriver().findElement(uniqueLocator);
    }

    public List<WebElement> getElements() {
        waits.waitForLocatorPresence(uniqueLocator);
        return BrowserUtil.getDriver().findElements(uniqueLocator);
    }

    protected static WebElement getElByTagName(String tagName) {
        return BrowserUtil.getDriver().findElement(By.tagName(tagName));
    }

    public boolean areWebElementsDisplayed() {
        waits.waitForElementsDisplayed(getElements());
        List<WebElement> elements = getElements();
        return !elements.isEmpty();
    }

    public boolean isDisplayedWithoutWaiting() {
        List<WebElement> elements = getElements();
        return !elements.isEmpty();
    }

    public void scrollAndClickByLastWebElement() {
        List<WebElement> deleteButtons = getElements();
        WebElement elementById = deleteButtons.get(deleteButtons.size() - 1);
      //  JsUtil.verticalScroll(elementById);
        waits.waitToBeClickable(elementById);
        elementById.click();
    }

    public void scrollAndClick() {
      //  JsUtil.verticalScroll(getElement());
        waits.waitToBeClickable(getElement());
        WebElement element = getElement();
        element.click();
    }

    public String getTextFromEl() {
        waits.waitForElementDisplayed(getElement());
        return getElement().getText();
    }

    public static String getTextFromTag(String tagName) {
        return getElByTagName(tagName).getText();
    }
}