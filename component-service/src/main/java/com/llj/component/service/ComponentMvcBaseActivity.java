package com.llj.component.service;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.llj.lib.base.MvcBaseActivity;
import com.llj.lib.tracker.PageName;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/12/13
 */
public abstract class ComponentMvcBaseActivity extends MvcBaseActivity {

    protected int     mActivityOpenEnterAnimation;
    protected int     mActivityOpenExitAnimation;
    protected int     mActivityCloseEnterAnimation;
    protected int     mActivityCloseExitAnimation;
    protected boolean mIsWindowIsTranslucent;

    public static final String PAGE_NAME = "PageName";


    @NonNull
    @Override
    public String getPageKey() {
        return PAGE_NAME;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mIsWindowIsTranslucent = isWindowIsTranslucent();
        initAnim();
        overridePendingTransition(mActivityOpenEnterAnimation, mActivityOpenExitAnimation);
        super.onCreate(savedInstanceState);
    }

    private void initAnim() {
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();

        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityOpenEnterAnimation, android.R.attr.activityOpenExitAnimation});
        mActivityOpenEnterAnimation = activityStyle.getResourceId(0, 0);
        mActivityOpenExitAnimation = activityStyle.getResourceId(1, 0);

        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
        mActivityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        mActivityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
    }

    private boolean isWindowIsTranslucent() {
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowIsTranslucent});
        boolean windowIsTranslucent = activityStyle.getBoolean(0, false);
        activityStyle.recycle();
        return windowIsTranslucent;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(mActivityCloseEnterAnimation, mActivityCloseExitAnimation);
    }
}
