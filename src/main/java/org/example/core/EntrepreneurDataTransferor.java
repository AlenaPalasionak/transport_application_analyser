package org.example.core;

import org.example.config.Config;

import java.io.File;

public class EntrepreneurDataTransferor extends AbstractDataTransferor {
    public EntrepreneurDataTransferor() {
        super(new File(Config.getProperties().getProperty("entrepreneur.dir"))
                , Config.getProperties().getProperty("entrepreneur_spreadsheetId"));
    }
}
