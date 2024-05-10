package org.example.project_constants;

import org.example.config.Config;

public class FileNamePart {
    public static final int CARRIER = 0;
    public static final int DATE = 2;
    public static final int CLIENT = 3;
    public static final int DRIVER = 4;
    public static final int PRICE = 5;
    public static final int NUMBER_OF_ITEMS = Integer.parseInt
            (Config.getProperties().getProperty("number_of_items_in_file_name"));
}
