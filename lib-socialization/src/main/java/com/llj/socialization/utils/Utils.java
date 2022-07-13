package com.llj.socialization.utils;

import android.app.Activity;
import android.content.Context;

/**
 * 分享工具类
 *
 * @author liulinjie
 * @date 2022-06-16 16:19
 */
public class Utils {

  public static void finishActivity(Context context) {
    if (context instanceof Activity) {
      Activity activity = (Activity) context;
      if (activity.getClass().getSimpleName().equals("ResponseActivity") && !activity
          .isDestroyed()) {
        activity.finish();
      }
    }
  }

}
