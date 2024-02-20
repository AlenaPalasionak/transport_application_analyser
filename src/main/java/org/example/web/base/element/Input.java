package org.example.web.base.element;

import org.openqa.selenium.By;

public class Input extends BaseElement{
    public Input(By uniqueLocator, String elementName) {
        super(uniqueLocator, elementName);
    }

    public void input(String userInfo) {
        getElement().sendKeys(userInfo);
    }
}
