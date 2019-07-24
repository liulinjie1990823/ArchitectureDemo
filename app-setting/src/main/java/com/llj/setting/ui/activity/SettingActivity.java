package com.llj.setting.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.utils.AFragmentUtils;
import com.llj.setting.R;
import com.llj.setting.SettingMvcBaseActivity;

import org.jetbrains.annotations.Nullable;

import io.flutter.facade.Flutter;

/**
 * ArchitectureDemo.
 * describe:设置界面
 *
 * @author llj
 * @date 2019-07-17
 */
@Route(path = CRouter.SETTING_SETTING_ACTIVITY)
public class SettingActivity extends SettingMvcBaseActivity {
    @Override
    public int layoutId() {
        return R.layout.setting_setting_activity;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        AFragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.container, Flutter.createFragment("/"));
    }

    @Override
    public void initData() {

    }
}
