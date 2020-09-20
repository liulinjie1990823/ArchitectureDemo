/*
 * Copyright © 2019, Beijing Jinhaiqunying, Co,. Ltd. All Rights Reserved.
 * Copyright Notice
 * Jinhaiqunying copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 * without the prior written consent of Jinhaiqunying.
 * Disclaimer
 * This specification is preliminary and is subject to change at any time without notice.
 * Jinhaiqunying assumes no responsibility for any errors contained herein.
 *
 */

package com.llj.lib.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * describe: 兼容存在多个tab的页面中，只有一个fragment中的布局有fitSystemWindow效果
 *
 * @author liulinjie
 * @date 2019-12-09 19:27
 */
public class WindowInsetsDispatchConstraintLayout extends ConstraintLayout {

  public WindowInsetsDispatchConstraintLayout(Context context) {
    this(context, null, 0, 0);
  }

  public WindowInsetsDispatchConstraintLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0, 0);
  }

  public WindowInsetsDispatchConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public WindowInsetsDispatchConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
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
