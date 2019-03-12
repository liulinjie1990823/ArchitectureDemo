package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.MvcBaseActivity;
import com.llj.lib.component.api.finder.NeacyFinder;

import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/12
 */
@Route(path = CRouter.APP_APT_ACTIVITY)
public class AptActivity2 extends MvcBaseActivity {
    @Override
    public int layoutId() {
        return R.layout.activity_apt;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        NeacyFinder.inject(this);
    }

    @Override
    public void initData() {

    }
}
