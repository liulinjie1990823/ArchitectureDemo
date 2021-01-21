package com.llj.lib.tracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.annotation.IntDef;
import androidx.fragment.app.Fragment;
import com.llj.lib.tracker.model.TrackerEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import timber.log.Timber;

/**
 * ArchitectureDemo. describe:
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

    TrackerApp.init(application, config);

  }

  /**
   * 是否需要过滤
   *
   * @param pageName
   * @return
   */
  public static boolean filterPage(String pageName) {
    //在过滤名单中的要被过滤
    if (TextUtils.isEmpty(pageName) || mFilter.contains(pageName)) {
      return true;
    }
    return false;
  }


  public static void report(TrackerEvent event) {
    Timber.tag(TAG).e("report：%s", event.toString());

    if (event.sync) {
      //同步上传
    } else {
      //异步上传
    }
  }


  /**
   * 获得准确的Activity
   *
   * @param view
   * @return
   */
  public static Context getTrueContext(View view) {
    Context context = view.getContext();
    if (context instanceof Activity) {
      context = view.getContext();
    } else if (context instanceof ContextThemeWrapper) {
      context = ((ContextThemeWrapper) context).getBaseContext();
    }
    return context;
  }


  public static boolean isFragmentVisible(Fragment child) {
    return child.isVisible() && child.getUserVisibleHint();
  }


}
