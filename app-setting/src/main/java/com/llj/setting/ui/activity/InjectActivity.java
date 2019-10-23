package com.llj.setting.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.lib.jump.annotation.Jump;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.utils.AFragmentUtils;
import com.llj.setting.R;
import com.llj.setting.SettingMvcBaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-12
 */
@Route(path = CRouter.SETTING_INJECT_ACTIVITY)
@Jump(ciw = "ciw://InjectActivity", route = CRouter.SETTING_INJECT_ACTIVITY, needLogin = true, desc = "InjectActivity")
public class InjectActivity extends SettingMvcBaseActivity {
    @Override
    public int layoutId() {
        return R.layout.setting_inject_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        Fragment fragment = (Fragment) ARouter.getInstance().build(CRouter.SETTING_INJECT_FRAGMENT)
                .navigation();

        AFragmentUtils.addFragment(getSupportFragmentManager(),R.id.container,fragment);

    }

    @Override
    public void initData() {

    }
}
