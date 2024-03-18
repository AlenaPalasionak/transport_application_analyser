package org.example.google_sheet;

import org.example.config.Config;

import java.io.File;

public class EntrepreneurDataTransfer extends AbstractDataTransfer {
    public EntrepreneurDataTransfer() {
        super(new File(Config.getProperties().getProperty("entrepreneur.dir"))
                , Config.getProperties().getProperty("entrepreneur_spreadsheetId"));
    }
}
