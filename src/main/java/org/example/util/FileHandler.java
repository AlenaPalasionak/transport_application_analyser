package org.example.util;

import org.example.resources.FileNamePart;
import org.example.model.Transportation;
import org.example.util.logger.Log;

import javax.swing.*;
import java.io.File;

import java.util.*;
import java.util.stream.Collectors;

public class FileHandler {

    public static List<File> fileList;

    public static List<Transportation> getTransportationDataList(File storageDir) {
        fileList = FileHandler.getFileList(storageDir);
        List<Transportation> transportationList = new ArrayList<>();
        if (fileList.isEmpty()) {
            JOptionPane.showMessageDialog(null, """
                    Нет файлов для записи. Если новые файлы были созданы, возможно вы забыли убрать знак "+",
                    который помечает их как "уже добавленные в список"
                    """);
        } else {
            for (File file : fileList) {
                String[] transportationDataItems = file.getName().split("=");
                if (!(transportationDataItems.length == FileNamePart.NUMBER_OF_ITEMS)) {
                    JOptionPane.showMessageDialog(null, """
                            Имя файла записано с ошибкой.
                            Проверьте наличие знаков "=" между данными о перевозке.
                            """);
                }
                //   FileHandler.markAsWritten(file, storageDir); хочу убрать отсюда переименовывание файлов
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

    private static List<File> getFileList(File storageDir) {
        List<File> fileList = Arrays.asList(Objects.requireNonNull((storageDir).listFiles()));
        return fileList.stream().filter(f -> !f.getName().contains("+"))
                .collect(Collectors.toList());
    }

    public static void markAsWritten(File file, File storageDir) {
        String fileName = file.getName();
        String input = "+";
        int at = fileName.indexOf("=");
        String newFileName = storageDir + "\\"
                + fileName.substring(0, at) + input + fileName.substring(at);
        boolean flag = file.renameTo(new File(newFileName));
        if (flag) {
            Log.info("Файл переименован получил метку \"+\" (добавлен в список)");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Файл: " + fileName + """
                            \nпопал в список, но НЕ получил метку "+" (добавлен в список в интернете).
                            Возможная причина: имя файла совпадает с ранее существующим в папке.
                            Зайдите в папку заявок и поставьте + вручную. Также поставьте отличительный знак, чтобы имена файлов не совпадали.
                            """);
        }
    }
}