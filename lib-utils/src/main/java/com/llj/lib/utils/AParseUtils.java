package com.llj.lib.utils;

import android.text.TextUtils;

/**
 * 字符串强转，已经处理了异常
 *
 * @author liulj
 */
public class AParseUtils {
    /**
     * 1.
     *
     * @param string
     * @return
     */
    public static int parseInt(String string) {
        return parseInt(string, 0);
    }

    /**
     * 2.
     *
     * @param string
     * @param def
     * @return
     */
    public static int parseInt(String string, int def) {
        if (TextUtils.isEmpty(string))
            return def;
        int num = def;
        try {
            num = Integer.parseInt(string);
        } catch (Exception e) {
            return num;
        }
        return num;
    }

    /**
     * 3.
     *
     * @param string
     * @return
     */
    public static long parseLong(String string) {
        return parseLong(string, 0L);
    }

    /**
     * 4.
     *
     * @param string
     * @param def
     * @return
     */
    public static long parseLong(String string, long def) {
        if (TextUtils.isEmpty(string))
            return def;
        long num = def;
        try {
            num = Long.parseLong(string);
        } catch (Exception e) {
            return num;
        }
        return num;
    }

    /**
     * @param string
     * @param def
     * @return
     */
    public static float parseFloat(String string, float def) {
        if (TextUtils.isEmpty(string))
            return def;
        float num = def;
        try {
            num = Float.parseFloat(string);
        } catch (Exception e) {
            return num;
        }
        return num;

    }

    /**
     * @param string
     * @return
     */
    public static float parseFloat(String string) {
        return parseFloat(string, 0f);
    }

    /**
     * @param string
     * @param def
     * @return
     */
    public static double parseDouble(String string, double def) {
        if (TextUtils.isEmpty(string))
            return def;
        double num = def;
        try {
            num = Double.parseDouble(string);
        } catch (Exception e) {
            return num;
        }
        return num;

    }

    /**
     * @param string
     * @return
     */
    public static double parseDouble(String string) {
        return parseDouble(string, 0f);
    }
}