package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityTransitionShareBinding
import com.llj.application.router.CRouterClassName

@Route(path = CRouterClassName.APP_TRANSITION_SHARE_ACTIVITY)
class TransitionShareActivity : MainMvcBaseActivity<ActivityTransitionShareBinding>() {
  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}