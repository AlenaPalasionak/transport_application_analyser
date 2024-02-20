package org.example.util;

import org.example.config.Config;
import org.example.file_handler.FileData;

import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileHandler {
    /**
     *
     * @param storageDir directory where application should get List of Files
     * @return List og Files from defined directory, which are still not in the accountant book
     * and which should be added there
     */
    public static List<File> getFileList(File storageDir) {
        List<File> list = Arrays.asList(Objects.requireNonNull((storageDir).listFiles()));
        return list.stream().filter(f -> !f.getName().endsWith("+"))
                .collect(Collectors.toList());
    }
//    private static void markAsExist() {
//        for (File file : getFileList(Config.getStorageDir())) {
//            file = new File(file.getName() + "+");
//        }
//    }
}
