package org.example.gui;

import org.example.core.EntrepreneurDataTransferor;

public class EntrepreneurButtonEventListener extends BaseEventListener {
    public EntrepreneurButtonEventListener() {
        super("Начать перенос данных для ИП ?", new EntrepreneurDataTransferor());
    }
}
