package com.llj.lib.statusbar;

import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.Window;

/**
 * Utils for status bar Created by qiu on 3/29/16.
 */
public class StatusBarCompat {

  //Get alpha color
  private static int calculateStatusBarColor(@ColorInt int color, int alpha) {
    float a = 1 - alpha / 255f;
    int red = color >> 16 & 0xff;
    int green = color >> 8 & 0xff;
    int blue = color & 0xff;
    red = (int) (red * a + 0.5);
    green = (int) (green * a + 0.5);
    blue = (int) (blue * a + 0.5);
    return 0xff << 24 | red << 16 | green << 8 | blue;
  }


  /**
   * @param window
   * @param statusColor
   * @param navigationBarColor
   */
  public static void setStatusBarColor(@NonNull Window window,
      @ColorInt int statusColor,
      @ColorInt int navigationBarColor) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      StatusBarCompatLollipop.setStatusBarColor(window, statusColor, navigationBarColor);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      StatusBarCompatKitKat.setStatusBarColor(window, statusColor);
    }
  }

  /**
   * set statusBarColor
   *
   * @param window
   * @param statusColor color
   */
  public static void setStatusBarColor(@NonNull Window window, @ColorInt int statusColor) {
    setStatusBarColor(window, statusColor, 0);
  }

  /**
   * 覆盖模式，状态栏覆盖在布局上方
   *
   * @param window
   * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
   */
  public static void translucentStatusBar(@NonNull Window window, boolean hideStatusBarBackground) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      StatusBarCompatLollipop.translucentStatusBar(window, hideStatusBarBackground, false);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      StatusBarCompatKitKat.translucentStatusBar(window);
    }
  }

  public static void translucentStatusBarAndNavigation(@NonNull Window window,
      boolean hideStatusBarBackground) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      StatusBarCompatLollipop.translucentStatusBar(window, hideStatusBarBackground, true);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      StatusBarCompatKitKat.translucentStatusBar(window);
    }
  }


  /**
   * 覆盖模式，状态栏覆盖在布局上方
   *
   * @param window
   * @param appBarLayout
   * @param collapsingToolbarLayout
   * @param toolbar
   * @param statusColor
   */
  public static void setStatusBarColorForCollapsingToolbar(@NonNull Window window,
      AppBarLayout appBarLayout,
      CollapsingToolbarLayout collapsingToolbarLayout,
      Toolbar toolbar,
      @ColorInt int statusColor) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      StatusBarCompatLollipop
          .setStatusBarColorForCollapsingToolbar(window, appBarLayout, collapsingToolbarLayout,
              toolbar, statusColor);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      StatusBarCompatKitKat
          .setStatusBarColorForCollapsingToolbar(window, appBarLayout, collapsingToolbarLayout,
              toolbar, statusColor);
    }
  }
}
