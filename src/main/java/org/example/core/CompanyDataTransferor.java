package org.example.core;

import org.example.config.Config;
import org.example.util.logger.Log;

import java.io.File;

public class CompanyDataTransferor extends AbstractDataTransferor {

    public CompanyDataTransferor() {
        super(new File(Config.getProperties().getProperty("company.dir"))
                , (Config.getProperties().getProperty("company_spreadsheetId")));
        Log.info("(CompanyDataTransferor) 1. CompanyDataTransferor is created");
    }
}