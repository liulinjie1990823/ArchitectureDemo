package com.llj.lib.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtilLj {
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        if (map != null && map.size() != 0) {
            for (Entry<K, V> entry : map.entrySet()) {
                if (entry.getValue() != null && value != null && (entry.getValue() == value)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    public static String toJson(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        StringBuilder paras = new StringBuilder();
        paras.append("{");
        Iterator<Entry<String, String>> ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) ite.next();
            paras.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
            if (ite.hasNext()) {
                paras.append(",");
            }
        }
        paras.append("}");
        return paras.toString();
    }
}
