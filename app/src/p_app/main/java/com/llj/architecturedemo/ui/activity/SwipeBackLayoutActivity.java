package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gw.swipeback.SwipeBackLayout;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.statusbar.LightStatusBarCompat;
import com.llj.lib.statusbar.StatusBarCompat;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/12/17
 */
@Route(path = CRouter.APP_SWIPE_BACK_LAYOUT_ACTIVITY)
public class SwipeBackLayoutActivity extends AppMvcBaseActivity {
    @BindView(R.id.swipeBackLayout) SwipeBackLayout mSwipeBackLayout;

    @Override
    public void initData() {
    }

    @Override
    public void initViews(@Nullable Bundle bundle) {
        StatusBarCompat.translucentStatusBar(getWindow(), true);
        LightStatusBarCompat.setLightStatusBar(getWindow(), false);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_swipe_back_layout;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
