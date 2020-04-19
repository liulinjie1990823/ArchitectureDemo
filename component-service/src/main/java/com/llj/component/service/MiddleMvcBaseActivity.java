package com.llj.component.service;

import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.viewbinding.ViewBinding;
import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.llj.component.service.arouter.CJump;
import com.llj.lib.base.MvcBaseActivity;
import com.llj.lib.base.event.BaseEvent;
import com.llj.lib.jump.annotation.callback.JumpCallback;
import com.llj.lib.jump.api.core.Warehouse;
import com.llj.lib.tracker.ITracker;
import com.llj.lib.tracker.PageName;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe: author llj date 2018/12/13
 */
public abstract class MiddleMvcBaseActivity<V extends ViewBinding> extends
    MvcBaseActivity<V> implements ITracker {

  protected int     mActivityOpenEnterAnimation;
  protected int     mActivityOpenExitAnimation;
  protected int     mActivityCloseEnterAnimation;
  protected int     mActivityCloseExitAnimation;
  protected boolean mIsWindowIsTranslucent;

  protected boolean mUseAnim;

  private String mPageName;
  private String mPageId;

  @Override
  public int layoutId() {
    return 0;
  }

  @Override
  public String getPageName() {
    if (mPageName == null) {
      PageName annotation = getClass().getAnnotation(PageName.class);
      mPageName = annotation == null ? getClass().getSimpleName() : annotation.value();
    }
    return mPageName;
  }

  @Override
  public String getPageId() {
    if (mPageId == null) {
      mPageId = UUID.randomUUID().toString();
    }
    return mPageId;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    if (mUseAnim) {
      mIsWindowIsTranslucent = isWindowIsTranslucent();
      initAnim();
      overridePendingTransition(mActivityOpenEnterAnimation, mActivityOpenExitAnimation);
    }

    getPageName();
    getPageId();

    super.onCreate(savedInstanceState);
  }

  private void initAnim() {
    TypedArray activityStyle = getTheme()
        .obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
    int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
    activityStyle.recycle();

    activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId,
        new int[]{android.R.attr.activityOpenEnterAnimation,
            android.R.attr.activityOpenExitAnimation});
    mActivityOpenEnterAnimation = activityStyle.getResourceId(0, 0);
    mActivityOpenExitAnimation = activityStyle.getResourceId(1, 0);

    activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId,
        new int[]{android.R.attr.activityCloseEnterAnimation,
            android.R.attr.activityCloseExitAnimation});
    mActivityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
    mActivityCloseExitAnimation = activityStyle.getResourceId(1, 0);
    activityStyle.recycle();
  }

  private boolean isWindowIsTranslucent() {
    TypedArray activityStyle = getTheme()
        .obtainStyledAttributes(new int[]{android.R.attr.windowIsTranslucent});
    boolean windowIsTranslucent = activityStyle.getBoolean(0, false);
    activityStyle.recycle();
    return windowIsTranslucent;
  }

  @Override
  public void finish() {
    super.finish();
    if (mUseAnim) {
      overridePendingTransition(mActivityCloseEnterAnimation, mActivityCloseExitAnimation);
    }
  }

  @Override
  public boolean inCurrentPage(@NotNull BaseEvent event) {
    String pageName = event.getPageName();
    if (pageName != null && pageName.startsWith(CJump.SCHEME)) {
      JumpCallback jumpCallback = Warehouse.sMap.get(pageName);
      if (jumpCallback != null) {
        //通过ciw获取内页地址
        String inPath = jumpCallback.getInPath();

        //通过内页地址获取类名，判断类名
        Postcard build = ARouter.getInstance().build(inPath);
        LogisticsCenter.completion(build);

        return build.getDestination() != null
            && build.getDestination().getCanonicalName() != null
            && build.getDestination().getCanonicalName()
            .equals(getClass().getCanonicalName());
      }
    }
    return false;
  }
}
