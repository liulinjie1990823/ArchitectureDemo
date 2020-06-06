package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityTransitionShareBinding
import com.llj.component.service.arouter.CRouterClassName

@Route(path = CRouterClassName.APP_TRANSITION_SHARE_ACTIVITY)
class TransitionShareActivity : AppMvcBaseActivity<ActivityTransitionShareBinding>() {
  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}