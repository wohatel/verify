package com.murong.val.util;

public class StringUtils {

    public static boolean isBlank(String value) {
        if (value == null) {
            return true;
        }
        if (value.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
