package com.llj.setting.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.application.router.CRouter
import com.llj.lib.jump.annotation.Jump
import com.llj.lib.utils.AFragmentUtils
import com.llj.setting.R
import com.llj.setting.SettingMvcBaseActivity
import com.llj.setting.databinding.SettingInjectActivityBinding

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-10-12
 */
@Route(path = CRouter.SETTING_INJECT_ACTIVITY)
@Jump(outPath = "ciw://InjectActivity", inPath = CRouter.SETTING_INJECT_ACTIVITY, needLogin = true, desc = "InjectActivity")
class InjectActivity : SettingMvcBaseActivity<SettingInjectActivityBinding?>() {
  override fun initViews(savedInstanceState: Bundle?) {
    mClToolbar.setBackgroundColor(getCompatColor(mContext, R.color.red))
    mTvTbTitle.text = "InjectActivity"
    val fragment = ARouter.getInstance().build(CRouter.SETTING_INJECT_FRAGMENT)
        .navigation() as Fragment
    AFragmentUtils.addFragment(supportFragmentManager, R.id.container, fragment)
  }

  override fun initData() {}
}