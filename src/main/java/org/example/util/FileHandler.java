package org.example.util;

import org.example.project_constants.DialogPaneMessage;
import org.example.project_constants.FileNamePart;
import org.example.model.Transportation;
import org.example.util.logger.Log;
import javax.swing.*;
import java.io.File;

import java.util.*;
import java.util.stream.Collectors;

public class FileHandler {

    public static List<File> newFilesList;

    public static List<Transportation> getNewTransportationsList(File storageDir) {
        newFilesList = getNewCreatedFiles(storageDir);
        List<Transportation> transportationList = new ArrayList<>();
        if (newFilesList.isEmpty()) {
            JOptionPane.showMessageDialog(null, DialogPaneMessage.NEW_FILE_ABSENCE_NOTIFICATION);
        } else {
            for (File file : newFilesList) {
                String[] transportationDataItems = file.getName().split("=");
                if (!(transportationDataItems.length == FileNamePart.NUMBER_OF_ITEMS)) {
                    JOptionPane.showMessageDialog(null, String.format(DialogPaneMessage.WRONG_FILE_NAME,file));
                }
                Transportation transportation = new Transportation(transportationDataItems[FileNamePart.CARRIER]
                        , transportationDataItems[FileNamePart.CLIENT]
                        , transportationDataItems[FileNamePart.DATE]
                        , transportationDataItems[FileNamePart.PRICE]
                        , transportationDataItems[FileNamePart.DRIVER]);
                transportationList.add(transportation);
            }
        }
        return transportationList;
    }

    public static void markAsWritten(File storageDir) {
        for (File file : newFilesList) {
            String fileName = file.getName();
            String input = "+";
            int at = fileName.indexOf("=");
            String newFileName = storageDir + "\\"
                    + fileName.substring(0, at) + input + fileName.substring(at);
            boolean flag = file.renameTo(new File(newFileName));
            if (flag) {
                Log.info("(FileHandler) 2.Файл переименован получил метку \"+\" (добавлен в список)");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Файл: " + fileName + """
                                \nпопал в google список, но НЕ получил метку "+" (добавлен в список в интернете).
                                Возможная причина: имя файла совпадает с ранее существующим в папке.
                                Зайдите в папку заявок и поставьте + вручную. Также поставьте отличительный знак, чтобы имена файлов не совпадали.
                                """);
            }
        }
    }

    private static List<File> getNewCreatedFiles(File storageDir) {
        List<File> fileList = Arrays.asList(Objects.requireNonNull((storageDir).listFiles()));
        return fileList.stream().filter(f -> !f.getName().contains("+"))
                .collect(Collectors.toList());
    }
}