package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.pixplicity.sharp.Sharp;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-17
 */
@Route(path = CRouter.APP_SVG_ACTIVITY)
public class SvgActivity extends AppMvcBaseActivity {

    @BindView(R.id.view) View mView;
    @Override
    public int layoutId() {
        return R.layout.svg_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initData() {

    }
}
