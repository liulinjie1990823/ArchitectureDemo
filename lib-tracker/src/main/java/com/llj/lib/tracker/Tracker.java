package com.llj.lib.tracker;

import android.support.annotation.IntDef;
import android.util.Log;

import com.llj.lib.tracker.model.TrackerEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-19
 */
public class Tracker {
    public static final String TAG = Tracker.class.getSimpleName();

    public static final int DISABLE = -1;
    public static final int DEBUG   = 0;
    public static final int RELEASE = 1;

    @IntDef({DISABLE, DEBUG, RELEASE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ReportStrategy {
    }

    public static void init(TrackerConfig config) {
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver() {
//            @Override
//            public void onCreate(@NonNull LifecycleOwner owner) {
//                Log.e(TAG, "app onCreate");
//            }
//
//            @Override
//            public void onStart(@NonNull LifecycleOwner owner) {
//                Log.e(TAG, "app onStart");
//            }
//
//            @Override
//            public void onResume(@NonNull LifecycleOwner owner) {
//                Log.e(TAG, "app onResume");
//            }
//
//            @Override
//            public void onPause(@NonNull LifecycleOwner owner) {
//                Log.e(TAG, "app onPause");
//            }
//
//            @Override
//            public void onStop(@NonNull LifecycleOwner owner) {
//                Log.e(TAG, "app onStop");
//            }
//
//            @Override
//            public void onDestroy(@NonNull LifecycleOwner owner) {
//                Log.e(TAG, "app onDestroy");
//            }
//        });
    }


    public static void report(TrackerEvent event) {
        Log.i(TAG, "TrackerEvent：" + event.toString());

    }

}
