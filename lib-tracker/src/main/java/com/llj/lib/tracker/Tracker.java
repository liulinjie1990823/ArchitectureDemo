package com.llj.lib.tracker;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;

import com.llj.lib.tracker.model.TrackerEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import timber.log.Timber;

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

    public static void init(Application application, TrackerConfig config) {
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onCreate");
            }

            @Override
            public void onStart(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onStart");
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onResume");
            }

            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onPause");
            }

            @Override
            public void onStop(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onStop");
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onDestroy");
            }
        });
    }


    /**
     * 点击记录，编译时注入
     *
     * @param view
     */
    public static void trackEvent(View view) {

        String pageName = getPageName(view);

        Timber.tag(TAG).i("trackEvent：%s", pageName);

        TrackerEvent trackerEvent = new TrackerEvent(TrackerEvent.APP_CLICK, pageName, System.currentTimeMillis());


        //请求或者写入本地
        report(trackerEvent);
    }


    public static void trackViewOnClick(View view) {

        trackEvent(view);
    }

    public static void trackFragmentResume(Fragment fragment) {

    }

    public static void trackFragmentStart(Fragment fragment) {

    }

    public static void trackFragmentPause(Fragment fragment) {

    }

    public static void trackFragmentStop(Fragment fragment) {

    }


    public static void trackFragmentUserVisibleHint(Fragment fragment, boolean isVisibleToUser) {

    }

    public static void trackFragmentHiddenChanged(Fragment fragment, boolean hidden) {

    }

    /**
     * 生命周期记录，编译时注入
     *
     * @param activity
     * @param eventType
     */
    public static void trackLifecycle(Activity activity, @TrackerEvent.Type String eventType) {
        TrackerEvent trackerEvent = new TrackerEvent(eventType, getPageName(activity), System.currentTimeMillis());


        //请求或者写入本地
        report(trackerEvent);
    }

    /**
     * 生命周期记录，编译时注入
     *
     * @param fragment
     * @param eventType
     */
    public static void trackLifecycle(Fragment fragment, @TrackerEvent.Type String eventType) {
        TrackerEvent trackerEvent = new TrackerEvent(eventType, getPageName(fragment), System.currentTimeMillis());


        //请求或者写入本地
        report(trackerEvent);
    }

    /**
     * 获取点击的页面
     *
     * @param view
     *
     * @return
     */
    private static String getPageName(View view) {
        Context context = getTrueContext(view);
        String pageName = context.getClass().getCanonicalName() == null ? context.getClass().getName() : context.getClass().getCanonicalName();
        if (context instanceof FragmentActivity) {
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

            Fragment parentFragment = findVisibleFragment(fragmentManager);

            if (parentFragment != null) {
                //view的区域
                Rect rectView = new Rect();
                view.getGlobalVisibleRect(rectView);
                //fragment的区域
                Rect rectParent = new Rect();
                try {
                    parentFragment.getView().getGlobalVisibleRect(rectParent);
                } catch (NullPointerException e) {
                    return pageName;
                }
                Fragment childFragment = findVisibleFragment(parentFragment.getChildFragmentManager());
                if (childFragment != null) {
                    Rect rectChild = new Rect();
                    try {
                        childFragment.getView().getGlobalVisibleRect(rectChild);
                    } catch (NullPointerException e) {
                        return pageName;
                    }
                    //childFragment不忽略的情况下使用childFragment
                    if (rectChild.contains(rectView) && !(childFragment instanceof ITracker && ((ITracker) childFragment).ignore())) {
                        pageName = ((ITracker) childFragment).getPageName();
                    } else {
                        //parentFragment不忽略的情况下使用parentFragment
                        if (rectParent.contains(rectView) && !(parentFragment instanceof ITracker && ((ITracker) parentFragment).ignore())) {
                            pageName = ((ITracker) parentFragment).getPageName();
                        }
                    }
                } else {
                    //parentFragment不忽略的情况下使用parentFragment
                    if (rectParent.contains(rectView) && !(parentFragment instanceof ITracker && ((ITracker) parentFragment).ignore())) {
                        pageName = ((ITracker) parentFragment).getPageName();
                    }
                }
            }

        }
        return pageName;
    }

    /**
     * 获得准确的Activity
     *
     * @param view
     *
     * @return
     */
    private static Context getTrueContext(View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            context = view.getContext();
        } else if (context instanceof ContextThemeWrapper) {
            context = ((ContextThemeWrapper) context).getBaseContext();
        }
        return context;
    }

    private static String getPageName(Activity activity) {
        String pageName = null;
        if (activity instanceof ITracker) {
            pageName = ((ITracker) activity).getPageName();
        }
        return pageName;
    }

    private static String getPageName(Fragment fragment) {
        String pageName = null;
        if (fragment instanceof ITracker) {
            pageName = ((ITracker) fragment).getPageName();
        }
        return pageName;
    }

    /**
     * 获取显示的fragment
     *
     * @param fragmentManager
     *
     * @return
     */
    private static Fragment findVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> children = fragmentManager.getFragments();
        Fragment fragment = null;
        for (Fragment child : children) {
            //adapter模式下isVisible都是true,hideAndShow模式下getUserVisibleHint都是true,所以这里要同时判断
            if (child != null && child.isVisible() && child.getUserVisibleHint()) {
                fragment = child;
                break;
            }
        }
        return fragment;
    }

    public static void report(TrackerEvent event) {
        Log.i(TAG, "TrackerEvent：" + event.toString());

    }

}
