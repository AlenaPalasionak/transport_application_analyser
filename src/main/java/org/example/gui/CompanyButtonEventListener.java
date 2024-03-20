package org.example.gui;

import org.example.core.CompanyDataTransferor;
import org.example.util.FileHandler;

public class CompanyButtonEventListener extends BaseEventListener {
    public CompanyButtonEventListener() {
        super("Начать перенос данных для ЧТУП ?", new CompanyDataTransferor());
    }
}