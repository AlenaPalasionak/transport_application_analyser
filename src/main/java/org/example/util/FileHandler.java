package org.example.util;

import org.example.config.Config;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileHandler {
    static List<File> filelist;

    /**
     * @param storageDir directory where application should get List of Files
     * @return List og Files from defined directory, which are still not in the accountant book
     * and which should be added there
     */
    public static List<File> getFileList(File storageDir) {
        filelist = Arrays.asList(Objects.requireNonNull((storageDir).listFiles()));
        return filelist.stream().filter(f -> !f.getName().contains("+"))
                .collect(Collectors.toList());
    }

    public static void markAsWritten(File file) {
        String fileName = file.getName();
        String input = "+";
        int at = fileName.indexOf("=");
        String newFileName = Config.getProperties().getProperty("company.dir") + "\\"
                + fileName.substring(0, at) + input + fileName.substring(at);
        boolean flag = file.renameTo(new File(newFileName));
        // if renameTo() return true then if block is
        // executed
        if (flag) {
            System.out.println("File Successfully Rename");
        }
        // if renameTo() return false then else block is
        // executed
        else {
            System.out.println("Operation Failed");
        }
    }
}

