package org.example.google_sheet;

import org.example.config.Config;
import org.example.model.Transportation;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetsQuickstart {


    public static void main(String... args) throws IOException, GeneralSecurityException {
            AbstractGoogleSheetHandler eh = new CompanyGoogleSheetHandler();
//        System.out.println(eh.getSpreadsheetId());
        System.out.println(eh.getLastNumber());
        eh.addValueToSheet(Transportation.getTransportationDataList
                (new File(Config.getProperties().getProperty("company.dir"))));
       // System.out.println(eh.getEMPTY_ROW_NUMBER());
     //   System.out.println(eh.sheetValueSize);
    }
}
