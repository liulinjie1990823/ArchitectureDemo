package com.llj.lib.statusbar;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * After Lollipop use system method. Created by qiu on 8/27/16.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class StatusBarCompatLollipop {

  private StatusBarCompatLollipop() {
  }

  /**
   * statusBar's Height in pixels.
   *
   * @param window window
   * @return statusBar's Height in pixels
   */
  private static int getStatusBarHeight(@NonNull Window window) {
    int result = 0;
    int resId = window.getContext().getResources()
        .getIdentifier("status_bar_height", "dimen", "android");
    if (resId > 0) {
      result = window.getContext().getResources().getDimensionPixelOffset(resId);
    }
    return result;
  }

  /**
   * set StatusBarColor.
   *
   * <p>1. set Flags to call setStatusBarColor 2. call setSystemUiVisibility to
   * clear translucentStatusBar's Flag. 3. set FitsSystemWindows to false
   */


  static void setStatusBarColor(@NonNull Window window,
      @ColorInt int statusColor,
      @ColorInt int navigationBarColor) {

    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.setStatusBarColor(statusColor);
    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

    if (navigationBarColor != 0) {
      window.setNavigationBarColor(navigationBarColor);
    }
  }

  static void setStatusBarColor(@NonNull Window window, @ColorInt int statusColor) {
    setStatusBarColor(window, statusColor, 0);
  }

  /**
   * translucentStatusBar(full-screen).
   *
   * <p>1. set Flags to full-screen 2. set FitsSystemWindows to false
   *
   * @param hideStatusBarBackground hide statusBar's shadow
   */
  static void translucentStatusBar(@NonNull Window window, boolean hideStatusBarBackground,
      boolean hideNavigation) {

    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    if (hideStatusBarBackground) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

      window.setStatusBarColor(Color.TRANSPARENT);
      window.getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
      if (hideNavigation) {
        window.setNavigationBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
      }
    } else {
      window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

  }

  /**
   * compat for CollapsingToolbarLayout.
   *
   * <p>1. change to full-screen mode(like translucentStatusBar).
   *
   * <p>2. cancel CollapsingToolbarLayout's WindowInsets, let it layout as normal(now
   * setStatusBarScrimColor is useless).
   *
   * <p>3. set View's FitsSystemWindow to false.
   *
   * <p>4. add Toolbar's height, let it layout from top, then add paddingTop to layout normal.
   *
   * <p>5. change statusBarColor by AppBarLayout's offset. 6. add Listener to change
   *
   * @param window
   * @param appBarLayout
   * @param collapsingToolbarLayout
   * @param toolbar
   * @param statusColor
   */
  static void setStatusBarColorForCollapsingToolbar(@NonNull Window window,
      final AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout,
      Toolbar toolbar, final int statusColor) {

    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(Color.TRANSPARENT);
    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

    ViewCompat
        .setOnApplyWindowInsetsListener(collapsingToolbarLayout,
            new OnApplyWindowInsetsListener() {
              @Override
              public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                return insets;
              }
            });

    ViewGroup contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
    View childView = contentView.getChildAt(0);
    if (childView != null) {
      childView.setFitsSystemWindows(false);
      ViewCompat.requestApplyInsets(childView);
    }

    ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
    appBarLayout.setFitsSystemWindows(false);

    toolbar.setFitsSystemWindows(false);
    if (toolbar.getTag() == null) {
      CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) toolbar
          .getLayoutParams();
      int statusBarHeight = getStatusBarHeight(window);
      lp.height += statusBarHeight;
      toolbar.setLayoutParams(lp);
      toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight,
          toolbar.getPaddingRight(), toolbar.getPaddingBottom());
      toolbar.setTag(true);
    }

    Behavior behavior = ((LayoutParams) appBarLayout
        .getLayoutParams()).getBehavior();
    if (behavior instanceof AppBarLayout.Behavior) {
      int verticalOffset = ((AppBarLayout.Behavior) behavior).getTopAndBottomOffset();
      if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout
          .getScrimVisibleHeightTrigger()) {
        window.setStatusBarColor(statusColor);
      } else {
        window.setStatusBarColor(Color.TRANSPARENT);
      }
    } else {
      window.setStatusBarColor(Color.TRANSPARENT);
    }

    collapsingToolbarLayout.setFitsSystemWindows(false);
    appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
      if (Math.abs(verticalOffset) > appBarLayout1.getHeight() - collapsingToolbarLayout
          .getScrimVisibleHeightTrigger()) {
        if (window.getStatusBarColor() != statusColor) {
          startColorAnimation(window.getStatusBarColor(), statusColor,
              collapsingToolbarLayout.getScrimAnimationDuration(), window);
        }
      } else {
        if (window.getStatusBarColor() != Color.TRANSPARENT) {
          startColorAnimation(window.getStatusBarColor(), Color.TRANSPARENT,
              collapsingToolbarLayout.getScrimAnimationDuration(), window);
        }
      }
    });
    collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);
    collapsingToolbarLayout.setStatusBarScrimColor(statusColor);
  }

  /**
   * use ValueAnimator to change statusBarColor when using collapsingToolbarLayout.
   */
  static void startColorAnimation(int startColor, int endColor, long duration,
      final Window window) {
    if (sAnimator != null) {
      sAnimator.cancel();
    }
    sAnimator = ValueAnimator.ofArgb(startColor, endColor)
        .setDuration(duration);
    sAnimator.addUpdateListener(valueAnimator -> {
      if (window != null) {
        window.setStatusBarColor((Integer) valueAnimator.getAnimatedValue());
      }
    });
    sAnimator.start();
  }

  private static ValueAnimator sAnimator;
}
