package org.example.core;

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
import com.google.api.services.sheets.v4.model.ValueRange;
import org.example.Main;
import org.example.model.Transportation;
import org.example.project_constants.DialogPaneMessage;
import org.example.util.FileHandler;
import org.example.util.logger.Log;

import javax.swing.*;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractDataTransferor {
    protected final File storageDir;
    protected final String spreadsheetId;
    private String EMPTY_ROW_NUMBER_COORDINATES;
    public static final String NOT_TO_BE_FILLED_SELL = "";
    private static final String H1_H_RANGE = "!H1:H";
    private static final String APPLICATION_NAME = "Google Sheets API";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final NetHttpTransport HTTP_TRANSPORT;

    static {
        try {
            Log.info("(AbstractDataTransferor) 1.  HTTP_TRANSPORT is gonna be initialised");
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            Log.info("(AbstractDataTransferor)  2. HTTP_TRANSPORT was gonna be initialised but new RuntimeException was thrown "
                    + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public AbstractDataTransferor(File storageDir, String spreadsheetId) {
        this.storageDir = storageDir;
        this.spreadsheetId = spreadsheetId;
    }

    public void addValueToSpreadSheets(String sheetName) {
        Log.info("(AbstractDataTransferor) 3. Values are gonna be added to Sheet" + sheetName
                + ". Storage directory is gonna be opened in case it exists ");
        List<Transportation> transportationList = FileHandler.getNewTransportationsList(storageDir);
        for (Transportation tr : transportationList) {
            String carrierName = tr.carrierName();
            String clientName = tr.clientName();
            String date = tr.date();
            String price = tr.price();
            String driver = tr.driver();
            ValueRange body = new ValueRange()
                    .setValues(List.of(
                            List.of(clientName, NOT_TO_BE_FILLED_SELL
                                    , carrierName, driver, price
                                    , date, NOT_TO_BE_FILLED_SELL, getNextNumber(sheetName))));
            update(body);
        }
        FileHandler.markAsWritten(storageDir);
    }

    private int getLastNumber(String sheetName) {
        final ValueRange numerationRageResponseH1H;
        String h1HRangeOfSheet = sheetName + H1_H_RANGE;
        try {
            numerationRageResponseH1H = getService().spreadsheets().values()
                    .get(spreadsheetId, h1HRangeOfSheet)
                    .execute();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, DialogPaneMessage.WRONG_MONTH_ISSUE);
            Log.info("(AbstractDataTransferor) 4. Last Number was gonna be got, but the Exception was thrown " + e.getMessage());
            throw new RuntimeException(e);
        }
        List<List<Object>> numerationRowsListH1H = numerationRageResponseH1H.getValues();
        final int numerationRowsListH1HSize = numerationRowsListH1H.size();
        EMPTY_ROW_NUMBER_COORDINATES = String.format(sheetName + "!" + "A%s", numerationRowsListH1HSize + 1);
        String numberOfSell = (String) numerationRowsListH1H.get(numerationRowsListH1HSize - 1).get(0);
        if (isListEmpty(numberOfSell)) {
            return 0;
        } else {
            List<Object> lastRowList = numerationRowsListH1H.get(numerationRowsListH1H.size() - 1);
            Object lastCellValue = lastRowList.get(0);
            return Integer.parseInt(String.valueOf(lastCellValue));
        }
    }

    private boolean isListEmpty(String numberOfSell) {
        return Objects.equals(numberOfSell, "№");
    }

    private int getNextNumber(String sheetName) {
        return getLastNumber(sheetName) + 1;
    }

    private void update(ValueRange body) {
        try {
            getService().spreadsheets().values()
                    .update(spreadsheetId, EMPTY_ROW_NUMBER_COORDINATES, body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            Log.info("(AbstractDataTransferor) 5. Spreadsheet was gonna be updated, but the Exception was thrown " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Sheets getService() throws IOException {
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential getCredentials() throws IOException {
        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            Log.info("(AbstractDataTransferor) 6. Resource not found: " + CREDENTIALS_FILE_PATH);
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
