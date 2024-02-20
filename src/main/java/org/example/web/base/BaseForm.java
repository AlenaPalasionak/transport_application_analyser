package org.example.web.base;


import framework.base.element.BaseElement;
import framework.util.logger.Log;

public abstract class BaseForm {
    private final BaseElement uniqueElement;
    private String name;

    protected BaseForm(BaseElement uniqueElement, String name) {
        this.uniqueElement = uniqueElement;
        this.name = name;
    }

    public boolean isPageOpen() {
        Log.info("Open Form/Page: " + name);
        return uniqueElement.areWebElementsDisplayed();
    }
}
