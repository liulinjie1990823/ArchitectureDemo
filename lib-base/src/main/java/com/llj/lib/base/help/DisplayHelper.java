package com.llj.lib.base.help;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.llj.lib.utils.ADisplayUtils;

/**
 * ArchitectureDemo describe: author liulj date 2018/4/25
 */
public class DisplayHelper {

  public static int CONTENT_HEIGHT;

  public static int SCREEN_WIDTH;
  public static int SCREEN_HEIGHT;

  public static int STATUS_BAR_HEIGHT;
  public static int NAVIGATION_BAR_HEIGHT;

  public static float SCREEN_DENSITY;
  public static float SCREEN_DENSITY_DPI;


  private static boolean sInitialed;

  public static void init(Context context) {
    if (sInitialed || context == null) {
      return;
    }
    sInitialed = true;

    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics displayMetrics = new DisplayMetrics();
    wm.getDefaultDisplay().getRealMetrics(displayMetrics);
    SCREEN_WIDTH = displayMetrics.widthPixels;
    SCREEN_HEIGHT = displayMetrics.heightPixels;

    STATUS_BAR_HEIGHT = ADisplayUtils.getStatusBarHeight(context.getApplicationContext());
    NAVIGATION_BAR_HEIGHT = ADisplayUtils.getNavigationBarOffset(context.getApplicationContext());

    SCREEN_DENSITY = context.getResources().getDisplayMetrics().density;
    SCREEN_DENSITY_DPI = context.getResources().getDisplayMetrics().densityDpi;
  }
}
