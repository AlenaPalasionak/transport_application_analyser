package org.example.google_sheet;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetsQuickstart {

    public static void main(String... args) throws IOException, GeneralSecurityException {
            AbstractDataTransfer eh = new CompanyDataTransfer();
        System.out.println(eh.getSpreadsheetId());
        System.out.println(eh.getLastNumber());
        eh.addValueToSheet();
    }
}
