package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityActivityAnimFadeBinding
import com.llj.application.router.CRouterClassName

@Route(path = CRouterClassName.APP_ACTIVITY_ANIM_FADE_ACTIVITY)
class ActivityAnimFadeActivity : MainMvcBaseActivity<ActivityActivityAnimFadeBinding>() {
  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}