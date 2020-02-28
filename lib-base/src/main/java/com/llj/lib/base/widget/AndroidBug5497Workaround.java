package com.llj.lib.base.widget;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * describe 针对全屏模式或者透明覆盖模式下Activity和Dialog在AdjustResize属性失效情况下的兼容
 *
 * @author liulinjie
 * @date 2020-02-24 11:55
 */
public class AndroidBug5497Workaround {

  private static final String TAG = "AndroidBug5497";

  private int mUsableHeightSansKeyboard;//无键盘下DecorView的高度
  private int mStatusBarHeight;

  private View mChildOfContent;
  private int  mContentHeight;
  private int  mUsableHeightPrevious;

  private FrameLayout.LayoutParams mFrameLayoutParams;

  private boolean mEnable = true;

  public static void assistActivity(Activity activity) {
    new AndroidBug5497Workaround(activity);
  }

  public AndroidBug5497Workaround(Activity activity) {
    //获取状态栏的高度
    int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
    mStatusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

    FrameLayout content = activity.findViewById(android.R.id.content);
    mChildOfContent = content.getChildAt(0);

    //界面出现变动都会调用这个监听事件
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        if (mEnable) {
          if (mContentHeight == 0) {
            mContentHeight = mChildOfContent.getHeight();//兼容华为等机型
          }
          if (mUsableHeightSansKeyboard == 0) {
            mUsableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
          }
          possiblyResizeChildOfContent();
        }
      }
    };

    mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    mFrameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
  }

  public void setEnable(boolean enable) {
    mEnable = enable;
  }

  //重新调整跟布局的高度
  private void possiblyResizeChildOfContent() {
    int usableHeightNow = computeUsableHeight();

    //当前可见高度和上一次可见高度不一致 布局变动
    if (usableHeightNow != mUsableHeightPrevious) {
      //int usableHeightSansKeyboard2 = mChildOfContent.getHeight();//兼容华为等机型
      int heightDifference = mUsableHeightSansKeyboard - usableHeightNow;
      if (heightDifference > (mUsableHeightSansKeyboard / 4)) {
        //软键盘显示
        // keyboard probably just became visible
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
          mFrameLayoutParams.height =
              mUsableHeightSansKeyboard - heightDifference + mStatusBarHeight;
        } else {
          mFrameLayoutParams.height = mUsableHeightSansKeyboard - heightDifference;
        }
      } else {
        //软键盘隐藏
        mFrameLayoutParams.height = mContentHeight;
      }

      mChildOfContent.requestLayout();
      mUsableHeightPrevious = usableHeightNow;
    }
  }

  /**
   * 计算mChildOfContent可见高度，不包括statusBar的高度
   * <p/>
   * SOFT_INPUT_ADJUST_RESIZE属性，使用透明模式和非透明模式，都适用
   * <p/>
   * SOFT_INPUT_ADJUST_PAN属性，activity使用使用透明模式和非透明模式， 都适用，但是透明模式下computeUsableHeight也有效果，但是将mFrameLayoutParams.height重新设置高度后，系统还会自动增加一个y轴向上的偏移量，显示会有问题
   * dialog使用两种模式computeUsableHeight的值相等，所以不适用
   *
   * @return
   */
  private int computeUsableHeight() {
    Rect r = new Rect();
    mChildOfContent.getWindowVisibleDisplayFrame(r);
    return r.height();
  }

  /**
   * 全屏后者透明覆盖模式下mChildOfContent不会resize，所以导致每次获得的都是屏幕高（如1920），不适用
   * @return
   */
  //private int computeUsableHeight() {
  //  Rect r = new Rect();
  //  mChildOfContent.getGlobalVisibleRect(r);
  //
  //  Timber.tag(TAG).d("rec bottom>" + r.bottom + " | rec top>" + r.top);
  //  return r.height();// 全屏模式下： return r.bottom
  //}
}
