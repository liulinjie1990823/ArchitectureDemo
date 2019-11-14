package com.llj.lib.tracker;

import android.app.Activity;
import android.app.Application;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;

import com.llj.lib.tracker.model.TrackerEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
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
    private static final String TAG = Tracker.class.getSimpleName();

    static final int DISABLE = -1;
    static final int DEBUG   = 0;
    static final int RELEASE = 1;

    @IntDef({DISABLE, DEBUG, RELEASE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ReportStrategy {
    }


    private static HashSet<String> mFilter = new HashSet<>();

    static {
        mFilter.add("");
    }

    private static boolean isDebug(Application application) {
        return (application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public static void init(Application application, TrackerConfig config) {
        if (config.getReportStrategy() == Tracker.DISABLE) {
            return;
        }
        //要求RELEASE上传，如果当前是debug就不传
        if ((config.getReportStrategy() == Tracker.RELEASE && isDebug(application))) {
            return;
        }

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onCreate");

            }

            @Override
            public void onStart(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onStart");
                trackApp(owner, TrackerEvent.APP_APPEAR);
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
                trackApp(owner, TrackerEvent.APP_DISAPPEAR);
            }

            @Override
            public void onDestroy(@NonNull LifecycleOwner owner) {
                Timber.tag(TAG).i("app onDestroy");

            }
        });

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                trackPage(activity, TrackerEvent.PAGE_APPEAR);
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                trackPage(activity, TrackerEvent.PAGE_DISAPPEAR);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    /**
     * 是否需要过滤
     *
     * @param pageName
     *
     * @return
     */
    private static boolean filterPage(String pageName) {
        //在过滤名单中的要被过滤
        if (TextUtils.isEmpty(pageName) || mFilter.contains(pageName)) {
            return true;
        }
        return false;
    }

    /**
     * @param object
     * @param eventName
     */
    public static void trackApp(Object object, @TrackerEvent.PageType String eventName) {
        String pageName = object.getClass().getName();
        Timber.tag(TAG).i("trackApp：%s", pageName);

        TrackerEvent trackerEvent = new TrackerEvent(TrackerEvent.TYPE_PAGE, eventName, pageName, System.currentTimeMillis());


        //请求或者写入本地
        report(trackerEvent);
    }

    /**
     * 页面事件
     *
     * @param object    activity or fragment
     * @param eventName
     */
    public static void trackPage(Object object, @TrackerEvent.PageType String eventName) {
        if (filterPage(object.getClass().getName())) {
            return;
        }
        ITracker iTracker = getTracker(object);
        if (iTracker == null) {
            return;
        }
        Timber.tag(TAG).i("trackPage：%s", iTracker.getPageName());


        TrackerEvent trackerEvent = new TrackerEvent(TrackerEvent.TYPE_PAGE, eventName, iTracker, System.currentTimeMillis());

        //请求或者写入本地
        report(trackerEvent);
    }

    /**
     * @param view
     */
    public static void trackEvent(View view, @TrackerEvent.ActionType String eventName) {
        ITracker iTracker = getTracker(view);
        if (filterPage(iTracker.getPageName())) {
            return;
        }
        Timber.tag(TAG).i("trackEvent：%s", iTracker.getPageName());


        TrackerEvent trackerEvent = new TrackerEvent(TrackerEvent.TYPE_ACTION, eventName, iTracker, System.currentTimeMillis());

        //请求或者写入本地
        report(trackerEvent);
    }

    public static void report(TrackerEvent event) {
        Timber.tag(TAG).e("report：%s", event.toString());

        if (event.sync) {
            //同步上传
        } else {
            //异步上传
        }
    }


    public static void trackViewOnClick(View view) {

        trackEvent(view, TrackerEvent.APP_CLICK);
    }


    public static void trackFragmentStart(Object fragment) {
        if (!isFragmentVisible((Fragment) fragment)) {
            return;
        }
        trackPage(fragment, TrackerEvent.PAGE_APPEAR);
    }

    public static void trackFragmentResume(Object fragment) {

    }

    public static void trackFragmentPause(Object fragment) {

    }

    public static void trackFragmentStop(Object fragment) {
        if (!isFragmentVisible((Fragment) fragment)) {
            return;
        }
        trackPage(fragment, TrackerEvent.PAGE_DISAPPEAR);
    }


    public static void trackFragmentUserVisibleHint(Object fragment, boolean isVisibleToUser) {
        if (!isFragmentVisible((Fragment) fragment)) {
            return;
        }
        trackPage(fragment, TrackerEvent.PAGE_APPEAR);
    }

    public static void trackFragmentHiddenChanged(Object fragment, boolean hidden) {
        trackPage(fragment, hidden ? TrackerEvent.PAGE_DISAPPEAR : TrackerEvent.APP_APPEAR);
    }


    /**
     * 点击时间获取页面名
     *
     * @param view
     *
     * @return
     */
    private static ITracker getTracker(View view) {
        Context context = getTrueContext(view);
        ITracker pageName = null;

        //Activity
        if (context instanceof ITracker) {
            pageName = ((ITracker) context);
        }

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

                //parentFragment不忽略的情况下使用parentFragment
                if (rectParent.contains(rectView) && (parentFragment instanceof ITracker) && (!((ITracker) parentFragment).ignoreAction())) {
                    pageName = ((ITracker) parentFragment);
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
                    if (rectChild.contains(rectView) && (childFragment instanceof ITracker) && (!((ITracker) childFragment).ignoreAction())) {
                        pageName = ((ITracker) childFragment);
                    }
                }
            }

        }
        return pageName;
    }

    /**
     * 生命周期获取页面名
     *
     * @param context
     *
     * @return
     */
    private static ITracker getTracker(Object context) {
        ITracker pageName = null;

        if (context instanceof ITracker && (!((ITracker) context).ignorePage())) {
            pageName = ((ITracker) context);
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
            if (child != null && isFragmentVisible(child)) {
                fragment = child;
                break;
            }
        }
        return fragment;
    }

    private static boolean isFragmentVisible(Fragment child) {
        return (!child.isHidden()) && child.getUserVisibleHint();
    }


}
