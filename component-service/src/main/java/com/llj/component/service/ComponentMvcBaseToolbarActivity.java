package com.llj.component.service;

import android.content.res.TypedArray;
import android.os.Bundle;

import com.llj.lib.base.MvcBaseToolbarActivity;
import com.llj.lib.tracker.ITracker;
import com.llj.lib.tracker.PageName;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/12/13
 */
public abstract class ComponentMvcBaseToolbarActivity extends MvcBaseToolbarActivity implements ITracker {

    protected int     mActivityOpenEnterAnimation;
    protected int     mActivityOpenExitAnimation;
    protected int     mActivityCloseEnterAnimation;
    protected int     mActivityCloseExitAnimation;
    protected boolean mIsWindowIsTranslucent;

    protected boolean mUseAnim;

    private String mPageName;
    private String mPageId;

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
        if (mUseAnim) {
            overridePendingTransition(mActivityCloseEnterAnimation, mActivityCloseExitAnimation);
        }
    }
}
