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
import org.example.util.FileHandler;

import javax.swing.*;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractDataTransferor {
    protected final File storageDir;
    protected final String spreadsheetId;
    private String EMPTY_ROW_NUMBER;
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
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractDataTransferor(File storageDir, String spreadsheetId) {
        this.storageDir = storageDir;
        this.spreadsheetId = spreadsheetId;
    }

    public void addValueToSheet(String sheetName) {
        List<Transportation> transportationList = FileHandler.getNewTransportationsList(storageDir);
        for (Transportation tr : transportationList) {
            String carrierName = tr.getCarrierName();
            String clientName = tr.getClientName();
            String date = tr.getDate();
            String price = tr.getPrice();
            String driver = tr.getDriver();
            ValueRange body = new ValueRange()
                    .setValues(List.of(
                            List.of(clientName, ""
                                    , carrierName, driver, price
                                    , date, "", getLastNumber(sheetName) + 1)));
            update(body);
        }
        FileHandler.markAsWritten(storageDir);
    }

    private int getLastNumber(String sheetName) {
        ValueRange numerationRageResponse;
        String rangeOfSheet = sheetName + H1_H_RANGE;
        try {
            numerationRageResponse = getService().spreadsheets().values()
                    .get(spreadsheetId, rangeOfSheet)
                    .execute();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, """
                    Произошла ошибка.
                    Неверно указан МЕСЯЦ
                    или
                    названия столбцов таблицы в google списке заполнены неверно.
                    """);
            throw new RuntimeException(e);
        }
        List<List<Object>> numerationRowsList = numerationRageResponse.getValues();
        final int numerationRowsListSize = numerationRowsList.size();
        EMPTY_ROW_NUMBER = String.format(sheetName + "!" + "A%s", numerationRowsListSize + 1);
        String numberSign = (String) numerationRowsList.get(numerationRowsListSize - 1).get(0);
        if (Objects.equals(numberSign, "№")) {
            return 0;
        } else {
            List<Object> lastRowList = numerationRowsList.get(numerationRowsList.size() - 1);
            Object lastCellValue = lastRowList.get(0);
            return Integer.parseInt(String.valueOf(lastCellValue));
        }
    }

    private void update(ValueRange body) {
        try {
            getService().spreadsheets().values()
                    .update(spreadsheetId, EMPTY_ROW_NUMBER, body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Sheets getService() throws IOException {
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential getCredentials()
            throws IOException {
        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
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
