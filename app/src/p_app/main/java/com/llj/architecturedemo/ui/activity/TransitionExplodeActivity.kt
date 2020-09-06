package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.transition.Explode
import android.view.Window
import android.view.animation.AccelerateInterpolator
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityTransitionExplodeBinding
import com.llj.application.router.CRouterClassName

@Route(path = CRouterClassName.APP_TRANSITION_EXPLODE_ACTIVITY)
class TransitionExplodeActivity : MainMvcBaseActivity<ActivityTransitionExplodeBinding>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

    window.enterTransition = Explode().setDuration(500).setInterpolator(AccelerateInterpolator())
    window.returnTransition = Explode().setDuration(500)

    super.onCreate(savedInstanceState)
  }

  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}