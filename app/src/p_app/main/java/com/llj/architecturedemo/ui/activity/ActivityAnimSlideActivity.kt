package com.llj.architecturedemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityActivityAnimBinding
import com.llj.architecturedemo.databinding.ActivityActivityAnimSlideBinding
import com.llj.architecturedemo.databinding.ActivityTransitionBinding
import com.llj.component.service.arouter.CRouterClassName

@Route(path = CRouterClassName.APP_ACTIVITY_ANIM_SLIDE_ACTIVITY)
class ActivityAnimSlideActivity : AppMvcBaseActivity<ActivityActivityAnimSlideBinding>() {
  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}