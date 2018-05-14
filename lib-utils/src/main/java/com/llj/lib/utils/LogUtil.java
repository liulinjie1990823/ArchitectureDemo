package com.llj.lib.utils;

import android.util.Log;


/**
 * @author liulj
 */
public class LogUtil {
    private static       boolean DEBUGLLJ     = BuildConfig.DEBUG;
    private static final String  LLJ          = "LLJ";

    /**
     * Verbose黑色
     *
     * @param message
     */
    public static void LLJv(String message) {
        if (DEBUGLLJ)
            Log.v(LLJ, " | Verbose | " + message);
    }

    /**
     * Debug蓝色
     *
     * @param message
     */
    public static void LLJd(String message) {
        if (DEBUGLLJ)
            Log.d(LLJ, " | Debug | " + message);
    }

    /**
     * Info绿色
     *
     * @param message
     */
    public static void LLJi(String message) {
        if (DEBUGLLJ)
            Log.i(LLJ, " | Info | " + message);
    }

    /**
     * @param msg
     * @param args
     */
    public static void LLJi(String msg, Object... args) {
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        if (DEBUGLLJ)
            Log.i(LLJ, " | Info | " + msg);
    }

    /**
     * @param throwable
     */
    public static void LLJe(Throwable throwable) {
        if (DEBUGLLJ)
            Log.e(LLJ, " | Error | ", throwable);
    }


    /**
     * Warning黄色
     *
     * @param message
     */
    public static void LLJw(String message) {
        if (DEBUGLLJ)
            Log.w(LLJ, " | Warn | " + message);
    }

    /**
     * Error红色
     *
     * @param message
     */
    public static void LLJe(String message) {
        if (DEBUGLLJ)
            Log.e(LLJ, " | Error | " + message);
    }

    /**
     * Send a DEBUG log message
     *
     * @param msg
     * @param args
     */
    public static void LLJe(String msg, Object... args) {
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        if (DEBUGLLJ)
            Log.e(LLJ, " | Error | " + msg);
    }

    /**
     * Info绿色
     *
     * @param message
     */
    public static void i(String tag, String message) {
        if (DEBUGLLJ)
            Log.i(tag, " | Info | " + message);
    }

    /**
     * Error红色
     *
     * @param message
     */
    public static void e(String tag, String message) {
        if (DEBUGLLJ)
            Log.e(tag, " | Error | " + message);
    }

    public static void currentThread(String tag) {
        if (DEBUGLLJ)
            Log.e(tag, " | Error | " + "Thread:" + Thread.currentThread().getId());
    }
}
