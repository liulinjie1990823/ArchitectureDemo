package com.llj.lib.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

/**
 * describe: 兼容存在多个tab的页面中，只有一个fragment中的布局有fitSystemWindow效果
 *
 * @author llj
 * @date 2018/11/7
 */
public class WindowInsetsFrameLayout extends FrameLayout {

  public WindowInsetsFrameLayout(Context context) {
    this(context, null);
  }

  public WindowInsetsFrameLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public WindowInsetsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
      @Override
      public void onChildViewAdded(View parent, View child) {
        requestApplyInsets();
      }

      @Override
      public void onChildViewRemoved(View parent, View child) {

      }
    });
  }

  @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
  @Override
  public WindowInsets onApplyWindowInsets(WindowInsets insets) {
    int childCount = getChildCount();
    for (int index = 0; index < childCount; index++) {
      getChildAt(index).dispatchApplyWindowInsets(insets);
    }
    return insets;

  }
}
