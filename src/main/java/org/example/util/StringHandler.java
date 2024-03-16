package org.example.util;

public class StringHandler {
    public static String addCharToStringUsingSubString(String str, char c, int pos) {
        return str.substring(0, pos) + c + str.substring(pos);
    }
}
