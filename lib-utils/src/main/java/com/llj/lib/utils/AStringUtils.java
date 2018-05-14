package com.llj.lib.utils;

import android.text.TextUtils;
import android.webkit.URLUtil;

/**
 * Created by liulj on 16/5/29.
 */
public class AStringUtils {
    /**
     * 判断是否是gif
     *
     * @param path
     * @return
     */
    public static boolean isMp4Path(String path) {
        return path != null && (path.endsWith(".mp4") || path.endsWith(".MP4"));
    }

    /**
     * 判断是否是gif
     *
     * @param path
     * @return
     */
    public static boolean isGifPath(String path) {
        return path != null && (path.endsWith(".gif") || path.endsWith(".GIF"));
    }

    /**
     * 判断是否是jpg
     *
     * @param path
     * @return
     */
    public static boolean isJpgPath(String path) {
        return path != null && (path.endsWith(".jpg") || path.endsWith(".JPG") || path.endsWith(".JPEG") || path.endsWith(".jpeg"));
    }

    /**
     * 判断是否是png
     *
     * @param path
     * @return
     */
    public static boolean isPngPath(String path) {
        return path != null && (path.endsWith(".png") || path.endsWith(".PNG"));
    }

    public static boolean isOssPath(String path) {
        return path != null && (path.contains("oss"));
    }

    public static boolean isHttpPath(String url) {
        return URLUtil.isHttpUrl(url);
    }

    public static boolean isHttpsPath(String url) {
        return URLUtil.isHttpsUrl(url);
    }

    public static boolean isNetworkUrl(String url) {
        return URLUtil.isNetworkUrl(url);
    }


    public static String replaceRn(String path) {
        return path == null ? "" : path.replaceAll("\\r\\n", "");
    }

    public static String replaceN(String path) {
        return path == null ? "" : path.replaceAll("\\n", "");
    }

    public static String replaceR(String path) {
        return path == null ? "" : path.replaceAll("\\r", "");
    }

    public static String replaceNr(String path) {
        return path == null ? "" : path.replaceAll("\\n\\r", "");
    }

    //获取字符串中重复的字符的次数
    public static int getRepeatCount(String str, CharSequence target) {
        if (TextUtils.isEmpty(str)) {
            return 0;

        }
        int count = str.length() - str.replace(target, "").length();
        return count;
    }
}
