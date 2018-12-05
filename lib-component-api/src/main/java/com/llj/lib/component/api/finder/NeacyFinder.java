package com.llj.lib.component.api.finder;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.llj.lib.component.api.provider.ActivityProvider;
import com.llj.lib.component.api.provider.Provider;
import com.llj.lib.component.api.provider.ViewProvider;


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public class NeacyFinder {
    private static final String                   FINDER_SUFFIX     = "$$Finder";
    private static final ActivityProvider         ACTIVITY_PROVIDER = new ActivityProvider();
    private static final ViewProvider             VIEW_PROVIDER     = new ViewProvider();
    private static       ArrayMap<String, Finder> mFinderArrayMap   = new ArrayMap<>();

    public static void inject(Activity activity) {
        inject(activity, activity, ACTIVITY_PROVIDER);
    }

    public static void inject(Fragment fragment, View view) {
        inject(fragment, view, VIEW_PROVIDER);
    }

    public static void inject(Object host, Object source, Provider provider) {
        String className = host.getClass().getName();
        try {
            Class<?> finderClass = Class.forName(className + FINDER_SUFFIX);
            Finder finder = mFinderArrayMap.get(className);
            if (finder == null) {
                finder = (Finder) finderClass.newInstance();
                mFinderArrayMap.put(className, finder);
            }
            finder.inject(host, source, provider);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
