package org.example.model;

import org.example.web.driver.BrowserUtil;
import org.example.file_handler.Constants;
import org.example.util.FileHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Transportation {
//    private String carrierName;
//    private String clientName;
//    private String date;
//    private String price;
//    private int number;

    /**
     *
     * @param storageDir directory where application should get List of Files
     *      *                   every file name is processed and split according to
     *      *                   the regex
     * @return Transportation data from file name as a List
     */
    public static List<List<String>> getTransportationDataList(File storageDir) {
        List<File> fileList = FileHandler.getFileList(storageDir);
        List<List<String>> transportationDataList = new ArrayList<>();
        for (File file : fileList) {

            String[] fileNamePart = file.getName().split("=");
            FileHandler.markAsWritten(file);
            String carrierName = fileNamePart[Constants.CARRIER];
            String date = fileNamePart[Constants.DATE];
            String clientName = fileNamePart[Constants.CLIENT];
            String price = fileNamePart[Constants.PRICE];
            transportationDataList.add(List.of(carrierName, date, clientName, price));
        }
        return transportationDataList;
    }
}
