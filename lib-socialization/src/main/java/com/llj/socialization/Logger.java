package com.llj.socialization;

import android.util.Log;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public class Logger {
    public static final String TAG = "third-part";


    public static void e(String message) {
        Log.e(TAG, message);
    }

    public static void e(Throwable throwable) {
        Log.e(TAG, Log.getStackTraceString(throwable));
    }
    public static void e(String tag,Throwable throwable) {
        Log.e(tag, Log.getStackTraceString(throwable));
    }
}
