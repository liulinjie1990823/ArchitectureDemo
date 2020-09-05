package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityActivityAnimSlideBinding
import com.llj.application.router.CRouterClassName

@Route(path = CRouterClassName.APP_ACTIVITY_ANIM_SLIDE_ACTIVITY)
class ActivityAnimSlideActivity : MainMvcBaseActivity<ActivityActivityAnimSlideBinding>() {
  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}