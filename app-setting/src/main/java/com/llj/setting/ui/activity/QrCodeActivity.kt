package com.llj.setting.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.application.router.CRouter
import com.llj.application.vo.UserInfoVo
import com.llj.setting.R
import com.llj.setting.SettingMvpBaseActivity
import com.llj.setting.ui.model.MobileInfoVo
import com.llj.setting.ui.presenter.SettingPresenter
import com.llj.setting.ui.view.SettingView
import com.llj.setting.ui.view.SettingView.MobileInfo
import java.util.*

/**
 * ArchitectureDemo. describe: author llj date 2019/3/25
 */
@Route(path = CRouter.SETTING_QRCODE_ACTIVITY)
class QrCodeActivity : SettingMvpBaseActivity<SettingPresenter?>(), SettingView.UserInfo, MobileInfo {
  override fun layoutId(): Int {
    return R.layout.setting_qrcode_activity
  }

  override fun initViews(savedInstanceState: Bundle?) {}
  override fun initData() {}
  override fun getParams1(taskId: Int): HashMap<String, Any>? {
    return null
  }

  override fun onDataSuccess1(result: UserInfoVo, taskId: Int) {}
  override fun getParams2(taskId: Int): HashMap<String, Any>? {
    return null
  }

  override fun onDataSuccess2(result: MobileInfoVo, taskId: Int) {}
  override fun onDataError(tag: Int, e: Throwable, taskId: Int) {}
}