package org.example.google_sheet;

import org.example.config.Config;

import java.io.IOException;

public class EntrepreneurGoogleSheetHandler extends AbstractGoogleSheetHandler {


    @Override
    public String getSpreadsheetId() {
        return Config.getProperties().getProperty("entrepreneur_spreadsheetId");
    }

    @Override
    public String getDirPath() {
        return null;
    }
}
