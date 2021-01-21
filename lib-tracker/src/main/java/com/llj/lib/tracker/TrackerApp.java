package com.llj.lib.tracker;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import com.llj.lib.tracker.model.TrackerEvent;
import timber.log.Timber;

/**
 * describe 页面生命周期事件上报
 *
 * @author liulinjie
 * @date 12/21/20 4:48 PM
 */
public class TrackerApp {

  private static final String TAG = "TrackerApp";

  public static void init(Application application, TrackerConfig config) {
    //app生命周期
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

    //activity生命周期
    application.registerActivityLifecycleCallbacks(new TrackerActivityLifeCycleAdapter() {

      private TrackerFragmentLifeCycle mFragmentLifecycle = new TrackerFragmentLifeCycle();

      @Override
      public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        if (activity instanceof FragmentActivity) {
          ((FragmentActivity) activity).getSupportFragmentManager()
              .registerFragmentLifecycleCallbacks(mFragmentLifecycle, true);
        }
      }

      @Override
      public void onActivityResumed(@NonNull Activity activity) {
        trackPage(activity, TrackerEvent.PAGE_APPEAR);
      }

      @Override
      public void onActivityPaused(@NonNull Activity activity) {
        trackPage(activity, TrackerEvent.PAGE_DISAPPEAR);
      }

      @Override
      public void onActivityDestroyed(@NonNull Activity activity) {
        if (activity instanceof FragmentActivity) {
          ((FragmentActivity) activity).getSupportFragmentManager()
              .unregisterFragmentLifecycleCallbacks(mFragmentLifecycle);
        }
      }
    });
  }

  /**
   * @param object
   * @param eventName
   */
  private static void trackApp(Object object, @TrackerEvent.PageType String eventName) {
    String pageName = object.getClass().getName();
    Timber.tag(TAG).i("trackApp：%s", pageName);

    Tracker.report(new TrackerEvent(TrackerEvent.TYPE_PAGE, eventName, pageName));
  }

  /**
   * 页面事件
   *
   * @param object activity or fragment
   * @param eventName
   */
  private static void trackPage(Object object, @TrackerEvent.PageType String eventName) {
    ITracker trackerObject = getTracker(object);
    if (trackerObject == null) {
      return;
    }
    if (Tracker.filterPage(object.getClass().getName())) {
      return;
    }
    Timber.tag(TAG).i("trackPage：%s", trackerObject.getPageName());

    Tracker.report(new TrackerEvent(TrackerEvent.TYPE_PAGE, eventName, trackerObject));
  }


  public static void trackFragmentResume(Object fragment) {
    if (!Tracker.isFragmentVisible((Fragment) fragment)) {
      return;
    }
    trackPage(fragment, TrackerEvent.PAGE_APPEAR);

  }


  public static void trackFragmentPause(Object fragment) {
    if (!Tracker.isFragmentVisible((Fragment) fragment)) {
      return;
    }
    trackPage(fragment, TrackerEvent.PAGE_DISAPPEAR);
  }


  public static void trackFragmentUserVisibleHint(Object fragment, boolean isVisibleToUser) {
    if (!Tracker.isFragmentVisible((Fragment) fragment)) {
      return;
    }
    trackPage(fragment, TrackerEvent.PAGE_APPEAR);
  }

  public static void trackFragmentHiddenChanged(Object fragment, boolean hidden) {
    trackPage(fragment, hidden ? TrackerEvent.PAGE_DISAPPEAR : TrackerEvent.APP_APPEAR);
  }


  /**
   * 生命周期获取页面名
   *
   * @param object
   * @return
   */
  private static ITracker getTracker(Object object) {
    ITracker pageName = null;

    if (object instanceof ITracker && (!((ITracker) object).ignorePage())) {
      pageName = ((ITracker) object);
    }
    return pageName;
  }


}
