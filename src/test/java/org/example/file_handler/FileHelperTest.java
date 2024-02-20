package org.example.file_handler;

import org.example.util.FileHandler;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHelperTest {
    private static final String DIR = "C:\\Users\\User2\\Desktop\\Dir";
    private static final String PATTERN_NAME = "\\Название перевозчика= Заявка №1 от =02.13.2024= Название Заказчика =водитель= ";

    @BeforeAll
    static void beforeAll() {
        new File(DIR).mkdir();
        for (int i = 0; i < 5; i++) {
            File f = new File
                    (DIR + PATTERN_NAME + i + ".doc");
            File f2 = new File(DIR + PATTERN_NAME + i + "+.doc");
            try {
                f.createNewFile();
                f2.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    @Test
//    void initTransportationData() {
//        String carrierName;
//        String clientName;
//        String date;
//        String price;
//        FileHandler.initTransportationData(new File(DIR));
//assertEquals(carrierName, "Название перевозчика" );
//    }
//    @Test
//    void getCompanyFileList() {
//        List<File> files = FileHandler.getFileList(new File(DIR));
//        assertEquals(10, files.size());
//    }

    @AfterAll
    static void afterAll() {
    }
}
