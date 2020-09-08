package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityActivityAnimBinding
import com.llj.application.router.CRouterClassName

@Route(path = CRouterClassName.APP_ACTIVITY_ANIM_ACTIVITY)
class ActivityAnimActivity : MainMvcBaseActivity<ActivityActivityAnimBinding>() {

  override fun initViews(savedInstanceState: Bundle?) {

    mViewBinder.tvFade.setOnClickListener {

      ARouter.getInstance().build(CRouterClassName.APP_ACTIVITY_ANIM_FADE_ACTIVITY)
          .navigation(this)
    }
    mViewBinder.tvSlide.setOnClickListener {
      ARouter.getInstance().build(CRouterClassName.APP_ACTIVITY_ANIM_SLIDE_ACTIVITY)
          .navigation(this)
    }
  }

  override fun initData() {
  }
}