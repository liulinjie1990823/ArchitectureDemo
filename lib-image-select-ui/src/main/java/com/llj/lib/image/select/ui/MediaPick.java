package com.llj.lib.image.select.ui;

import android.app.Activity;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;
import java.util.Set;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-28
 */
public class MediaPick {

    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;

    private MediaPick(Activity activity) {
        this(activity, null);
    }

    private MediaPick(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private MediaPick(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }


    public static MediaPick from(Activity activity) {
        return new MediaPick(activity);
    }

    public static MediaPick from(Fragment fragment) {
        return new MediaPick(fragment);
    }

    public MediaPickConfig choose(Set<MimeType> mimeTypes, boolean mediaTypeExclusive) {
        return new MediaPickConfig();
    }
}
