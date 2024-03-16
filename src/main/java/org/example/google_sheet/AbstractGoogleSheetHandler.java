package org.example.google_sheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.example.file_handler.Constants;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public abstract class AbstractGoogleSheetHandler {
    protected String spreadsheetId;
    private String EMPTY_ROW_NUMBER;
    private static final String L1_L_RANGE = "Sheet1!L1:L";
    private static final String APPLICATION_NAME = "Google Sheets API";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final NetHttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public String getEMPTY_ROW_NUMBER() {
//        return EMPTY_ROW_NUMBER;
//    }

    public abstract String getSpreadsheetId();
    public abstract String getDirPath();

    public int getLastNumber() {
        ValueRange response = null;
        try {
            response = getService().spreadsheets().values()
                    .get(getSpreadsheetId(), L1_L_RANGE)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<List<Object>> rows = response.getValues();
        final int sheetValueSize = rows.size();
        EMPTY_ROW_NUMBER = String.format("A%s", sheetValueSize + 1);
        return Integer.parseInt(String.valueOf(rows.get(rows.size() - 1).get(0)));
    }

//    public void addValue(String client, String price, String career) throws
//            IOException, GeneralSecurityException {
//        ValueRange body = new ValueRange()
//                .setValues(List.of(
//                        List.of(client, ""
//                                , "", career, price
//                                , "", "", "", "", "", "", getLastNumber() + 1)));
//        UpdateValuesResponse res = getService().spreadsheets().values()
//                .update(getSpreadsheetId(), EMPTY_ROW_NUMBER, body)
//                .setValueInputOption("RAW")
//                .execute();
//    }

    public void addValueToSheet(List<List<String>> data) {
        String carrierName;
        String clientName;
        String date;
        String price;
        for (List<String> fileData : data) {
            carrierName = fileData.get(0);
            clientName = fileData.get(2);
            date = fileData.get(1);
            price = fileData.get(3);
            ValueRange body = new ValueRange()
                    .setValues(List.of(
                            List.of(clientName, ""
                                    , "", carrierName, price
                                    , "", "", "", date, "", "", getLastNumber() + 1)));
            try {
                UpdateValuesResponse res = getService().spreadsheets().values()
                        .update(getSpreadsheetId(), EMPTY_ROW_NUMBER, body)
                        .setValueInputOption("RAW")
                        .execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Sheets getService() throws IOException {
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential getCredentials()
            throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }



}
