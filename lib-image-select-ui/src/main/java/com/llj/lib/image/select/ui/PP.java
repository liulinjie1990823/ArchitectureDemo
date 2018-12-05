package com.llj.lib.image.select.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/31
 */
public class PP {
    private WeakReference<Activity> mContext;
    private WeakReference<Fragment> mFragment;

    private PP(Activity activity) {
        this(activity, null);
    }

    private PP(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private PP(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    public static PP from(Activity activity) {
        return new PP(activity);
    }

    public static PP from(Fragment fragment) {
        return new PP(fragment);
    }

}
