package com.llj.lib.utils.helper;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/26
 */
public class Utils {
    @SuppressLint("StaticFieldLeak")
    private static Context sApplication;

    public static void init(@NonNull final Application app) {
        Utils.sApplication = app.getApplicationContext();
    }

    public static Context getApp() {
        if (sApplication == null)
            throw new NullPointerException("u should init first");
        return sApplication;
    }
}
