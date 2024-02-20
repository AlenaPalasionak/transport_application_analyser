package org.example.model;

import org.example.config.Config;
import org.example.web.driver.BrowserUtil;
import org.example.file_handler.FileData;
import org.example.util.FileHandler;

import java.io.File;
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
     * every file name is processed and split according to the regex, so that we get needed data
     */
    public static void initTransportationData(String url, File storageDir) {
        List<File> fileList = FileHandler.getFileList(storageDir);
        for (File file : fileList) {
            String[] fileNamePart = file.getName().split("=");
            String carrierName = fileNamePart[FileData.CARRIER];
            String date = fileNamePart[FileData.DATE];
            String clientName = fileNamePart[FileData.CLIENT];
            String price = fileNamePart[FileData.PRICE];
            BrowserUtil.getDriver().get(url);
           // BrowserUtil.getDriver().
        }
    }

}
