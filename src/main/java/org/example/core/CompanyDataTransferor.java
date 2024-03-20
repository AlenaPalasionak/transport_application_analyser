package org.example.core;

import org.example.config.Config;

import java.io.File;

public class CompanyDataTransferor extends AbstractDataTransferor {

    public CompanyDataTransferor() {
        super(new File(Config.getProperties().getProperty("company.dir"))
                , (Config.getProperties().getProperty("company_spreadsheetId")));
    }
}