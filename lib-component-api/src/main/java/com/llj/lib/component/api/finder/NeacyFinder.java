package com.llj.lib.component.api.finder;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.View;


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public class NeacyFinder {
    private static final String FINDER_SUFFIX = "_Finder";

    private static       ArrayMap<String, Finder> mFinderArrayMap   = new ArrayMap<>();

    public static void inject(Activity activity) {
        inject(activity, activity.getWindow().getDecorView());
    }

    public static void inject(Fragment fragment, View view) {
        inject(fragment, view);
    }

    public static void inject(Object host, View source) {
        String className = host.getClass().getName();
        try {
            Class<?> finderClass = Class.forName(className + FINDER_SUFFIX);
            Finder finder = mFinderArrayMap.get(className);
            if (finder == null) {
                finder = (Finder) finderClass.newInstance();
                mFinderArrayMap.put(className, finder);
            }
            finder.inject(host, source);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
