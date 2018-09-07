package com.llj.module.java.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.llj.lib.base.MvcBaseActivity;
import com.llj.lib.component.annotation.BindView;
import com.llj.lib.component.api.finder.NeacyFinder;
import com.llj.module.java.R;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public class TextActivity extends MvcBaseActivity {
    @BindView(R.id.root_text) ConstraintLayout mConstraintLayout;

    @Override
    public void initData() {

    }

    @Override
    public void initViews(Bundle bundle) {
        NeacyFinder.inject(this);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_text;
    }
}
