package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.transition.Slide
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityTransitionSlideBinding
import com.llj.component.service.arouter.CRouterClassName

@Route(path = CRouterClassName.APP_TRANSITION_SLIDE_ACTIVITY)
class TransitionSlideActivity : AppMvcBaseActivity<ActivityTransitionSlideBinding>() {
  override fun onCreate(savedInstanceState: Bundle?) {
    window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
    window.enterTransition = Slide().setDuration(300).setInterpolator(AccelerateDecelerateInterpolator())
    window.returnTransition = Slide().setDuration(300)
    super.onCreate(savedInstanceState)
  }

  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}