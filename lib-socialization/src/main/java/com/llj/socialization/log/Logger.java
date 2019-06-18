package com.llj.socialization.log;

import android.util.Log;

/**
 * lib-socialization.
 * describe:
 *
 * @author llj
 * @date 2017/1/18
 */

public class Logger {
    public static final String TAG = "lib-socialization";

    public static void i(String message) {
        Log.i(TAG, message);
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }

    public static void e(Throwable throwable) {
        Log.e(TAG, Log.getStackTraceString(throwable));
    }

    public static void e(String tag, Throwable throwable) {
        Log.e(tag, Log.getStackTraceString(throwable));
    }
}
