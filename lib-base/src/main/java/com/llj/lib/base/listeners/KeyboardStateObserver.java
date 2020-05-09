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

  private int mDecorViewHeight;//无键盘下DecorView的高度

  private View mUserRootView;//contentView的子布局，即是用户的布局

  private int mUserRootViewHeightPrevious;//布局中的rootView的高度

  private OnKeyboardVisibilityListener mListener;

  public void setKeyboardVisibilityListener(OnKeyboardVisibilityListener listener) {
    this.mListener = listener;
  }


  private KeyboardStateObserver(Window window) {
    FrameLayout content = window.findViewById(android.R.id.content);
    mUserRootView = content.getChildAt(0);
    mUserRootView.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          public void onGlobalLayout() {
            if (mDecorViewHeight == 0) {
              mDecorViewHeight = mUserRootView.getRootView().getHeight();
            }
            possiblyResizeChildOfContent();
          }
        });
  }

  private void possiblyResizeChildOfContent() {
    int userRootViewHeight = computeUsableHeight();
    //对比当前布局中的rootView的可见高度
    if (userRootViewHeight != mUserRootViewHeightPrevious) {

      //mDecorViewHeight - userRootViewHeight就是软键盘弹起状态下然键盘的高度
      if (mDecorViewHeight - userRootViewHeight > (mDecorViewHeight / 4)) {
        //说明软键盘弹起
        if (mListener != null) {
          mListener.onKeyboardShow(userRootViewHeight);
        }
      } else {
        //软键盘隐藏
        if (mListener != null) {
          mListener.onKeyboardHide(userRootViewHeight);
        }
      }
      mUserRootViewHeightPrevious = userRootViewHeight;

      //Timber.tag(TAG).d("usableHeightNow: " + userRootViewHeight + " | usableHeightSansKeyboard:"
      //    + mDecorViewHeight + " | heightDifference:" + heightDifference);
    }
  }


  /**
   * 计算mUserRootView可见高度，不包括statusBar的高度
   * <p/>
   * SOFT_INPUT_ADJUST_RESIZE属性，在透明模式和非透明模式下，软键盘的显示和隐藏，computeUsableHeight会有不同的值
   * <p/>
   * SOFT_INPUT_ADJUST_PAN属性，activity在透明模式和非透明模式下，软键盘的显示和隐藏，computeUsableHeight会有不同的值（在透明模式下computeUsableHeight也有效果，但是将mFrameLayoutParams.height重新设置高度后，系统还会自动增加一个y轴向上的偏移量，显示会有问题），
   * dialog在透明模式和非透明模式下，软键盘的显示和隐藏，computeUsableHeight的值相等，所以不适用
   *
   * @return
   */
  private int computeUsableHeight() {
    Rect r = new Rect();
    mUserRootView.getWindowVisibleDisplayFrame(r);
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

    void onKeyboardShow(int resizeHeight);

    void onKeyboardHide(int resizeHeight);
  }
}
