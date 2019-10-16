package com.llj.component.service;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.llj.lib.base.MvcBaseFragment;
import com.llj.lib.statusbar.LightStatusBarCompat;
import com.llj.lib.tracker.ITracker;
import com.llj.lib.tracker.PageName;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-16
 */
abstract public class MiddleMvcBaseFragment extends MvcBaseFragment implements ITracker {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getPageName();
        getPageId();
        super.onCreate(savedInstanceState);
    }

    @CallSuper
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            LightStatusBarCompat.setLightStatusBar(((Activity) mContext).getWindow(), statusBarTextColorBlack());
        }
    }

    public boolean statusBarTextColorBlack() {
        return true;
    }

    @CallSuper
    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        LightStatusBarCompat.setLightStatusBar(((Activity) mContext).getWindow(), statusBarTextColorBlack());
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
