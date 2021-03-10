package com.llj.setting.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.application.router.CRouter
import com.llj.lib.utils.AFragmentUtils
import com.llj.setting.R
import com.llj.setting.SettingMvcBaseActivity
import com.llj.setting.databinding.SettingSettingActivityBinding
import io.flutter.embedding.android.FlutterFragment

/**
 * ArchitectureDemo. describe:设置界面
 *
 * @author llj
 * @date 2019-07-17
 */
@Route(path = CRouter.SETTING_SETTING_ACTIVITY)
class SettingActivity : SettingMvcBaseActivity<SettingSettingActivityBinding>() {

  override fun initViews(savedInstanceState: Bundle?) {
    AFragmentUtils
        .replaceFragment(getSupportFragmentManager(), R.id.container,
            FlutterFragment.withNewEngine().initialRoute("setting").build());
  }

  override fun initData() {}
}