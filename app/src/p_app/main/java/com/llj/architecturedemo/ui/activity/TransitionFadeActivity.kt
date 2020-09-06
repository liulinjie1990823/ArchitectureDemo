package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.transition.Fade
import android.view.Window
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityTransitionFadeBinding
import com.llj.application.router.CRouterClassName

@Route(path = CRouterClassName.APP_TRANSITION_FADE_ACTIVITY)
class TransitionFadeActivity : MainMvcBaseActivity<ActivityTransitionFadeBinding>() {
  override fun onCreate(savedInstanceState: Bundle?) {
    window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
    window.enterTransition = Fade().setDuration(300)
    window.returnTransition = Fade().setDuration(300)
    super.onCreate(savedInstanceState)
  }
  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}