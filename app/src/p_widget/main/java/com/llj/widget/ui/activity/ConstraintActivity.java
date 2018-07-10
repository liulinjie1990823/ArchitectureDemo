package com.llj.widget.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.MyBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/5
 */
@Route(path = CRouter.WIDGET_CONSTRAINT_ACTIVITY)
public class ConstraintActivity extends MyBaseActivity {
    @Override
    public int layoutId() {
        return R.layout.activity_constraint;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }
}
