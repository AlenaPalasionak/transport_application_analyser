package org.example.core;

import org.example.config.Config;
import org.example.util.logger.Log;

import java.io.File;

public class EntrepreneurDataTransferor extends AbstractDataTransferor {
    public EntrepreneurDataTransferor() {
        super(new File(Config.getProperties().getProperty("entrepreneur.dir"))
                , Config.getProperties().getProperty("entrepreneur_spreadsheetId"));
        Log.info("(EntrepreneurDataTransferor) 1. EntrepreneurDataTransferor is created");

    }
}
