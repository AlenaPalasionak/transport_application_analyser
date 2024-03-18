package org.example.google_sheet;

import org.example.config.Config;

import java.io.File;

public class CompanyDataTransfer extends AbstractDataTransfer {

    public CompanyDataTransfer() {
        super(new File(Config.getProperties().getProperty("company.dir"))
                , (Config.getProperties().getProperty("company_spreadsheetId")));
    }
}