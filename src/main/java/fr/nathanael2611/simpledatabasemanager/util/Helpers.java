package fr.nathanael2611.simpledatabasemanager.util;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple helper class.
 * This contains some useful methods used sometimes in code
 *
 * @author Nathanael2611
 */
public class Helpers {
    /**
     * Just return true if string is numeric.
     * Just return false if string is not numeric.
     */
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Just extract all entry-names from a HashMap<String, T>
     */
    public static <T>  String[] extractAllHashMapEntryNames(HashMap<String, T> map) {
        String[] strings = new String[map.size()];
        int i = 0;
        for(String str : map.keySet()) {
            strings[i] = str;
            i++;
        }
        return strings;
    }

    /**
     * Create an entry
     */
    public static <V> Map.Entry createEntry(String str, V obj){
        return new Map.Entry() {
            @Override
            public Object getKey() {
                return str;
            }

            @Override
            public V getValue() {
                return obj;
            }

            @Override
            public Object setValue(Object value) {
                return null;
            }
        };
    }
}
