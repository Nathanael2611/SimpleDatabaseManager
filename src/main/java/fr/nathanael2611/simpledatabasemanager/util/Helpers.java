package fr.nathanael2611.simpledatabasemanager.util;

import java.util.HashMap;

public class Helpers {
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static <T>  String[] extractAllHashMapEntryNames(HashMap<String, T> map) {
        String[] strings = new String[map.size()];
        int i = 0;
        for(String str : map.keySet()) {
            strings[i] = str;
            i++;
        }
        return strings;
    }
}
