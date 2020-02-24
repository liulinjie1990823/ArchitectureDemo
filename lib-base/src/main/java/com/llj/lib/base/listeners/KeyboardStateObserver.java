/*
 * Copyright © 2020, Beijing Jinhaiqunying, Co,. Ltd. All Rights Reserved.
 * Copyright Notice
 * Jinhaiqunying copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 * without the prior written consent of Jinhaiqunying.
 * Disclaimer
 * This specification is preliminary and is subject to change at any time without notice.
 * Jinhaiqunying assumes no responsibility for any errors contained herein.
 *
 */

package com.llj.lib.base.listeners;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import timber.log.Timber;

/**
 * describe 软键盘的显示和隐藏状态监听
 *
 * @author liulinjie
 * @date 2020-02-13 19:00
 */
public class KeyboardStateObserver {

  private static final String TAG = "KeyboardStateObserver";

  public static KeyboardStateObserver getKeyboardStateObserver(Window window) {
    return new KeyboardStateObserver(window);
  }

  private int mUsableHeightSansKeyboard;//无键盘下DecorView的高度

  private View mChildOfContent;//布局中的rootView
  private int  mUsableHeightPrevious;//布局中的rootView的高度

  private OnKeyboardVisibilityListener mListener;

  public void setKeyboardVisibilityListener(OnKeyboardVisibilityListener listener) {
    this.mListener = listener;
  }


  private KeyboardStateObserver(Window window) {
    FrameLayout content = window.findViewById(android.R.id.content);
    mChildOfContent = content.getChildAt(0);
    mChildOfContent.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          public void onGlobalLayout() {
            if (mUsableHeightSansKeyboard == 0) {
              mUsableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            }
            possiblyResizeChildOfContent();
          }
        });
  }

  private void possiblyResizeChildOfContent() {
    int usableHeightNow = computeUsableHeight();
    //对比当前布局中的rootView的可见高度
    if (usableHeightNow != mUsableHeightPrevious) {
      int heightDifference = mUsableHeightSansKeyboard - usableHeightNow;
      if (heightDifference > (mUsableHeightSansKeyboard / 4)) {
        if (mListener != null) {
          mListener.onKeyboardShow();
        }
      } else {
        if (mListener != null) {
          mListener.onKeyboardHide();
        }
      }
      mUsableHeightPrevious = usableHeightNow;

      Timber.tag(TAG).d("usableHeightNow: " + usableHeightNow + " | usableHeightSansKeyboard:"
          + mUsableHeightSansKeyboard + " | heightDifference:" + heightDifference);
    }
  }


  /**
   * 计算mChildOfContent可见高度，不包括statusBar的高度
   *
   * 需要设置soft属性是SOFT_INPUT_ADJUST_RESIZE才生效， SOFT_INPUT_ADJUST_PAN会使软键盘弹出前后高度一样，都是无软键盘的时候的大小
   *
   * @return
   */
  private int computeUsableHeight() {
    Rect r = new Rect();
    mChildOfContent.getWindowVisibleDisplayFrame(r);
    Timber.tag(TAG).d("rec bottom>" + r.bottom + " | rec top>" + r.top);
    return r.height();
  }

  /**
   * 全屏后者透明覆盖模式下mChildOfContent不会resize，所以导致每次获得的都是屏幕高（如1920），不适用
   *
   * @return
   */
  //private int computeUsableHeight() {
  //  Rect r = new Rect();
  //  mChildOfContent.getGlobalVisibleRect(r);
  //
  //  Timber.tag(TAG).d("rec bottom>" + r.bottom + " | rec top>" + r.top);
  //  return r.height();// 全屏模式下： return r.bottom
  //}

  public interface OnKeyboardVisibilityListener {

    void onKeyboardShow();

    void onKeyboardHide();
  }
}
