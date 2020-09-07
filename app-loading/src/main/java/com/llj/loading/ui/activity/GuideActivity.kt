package com.llj.loading.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.application.router.CRouterClassName
import com.llj.loading.LoadingMvcBaseActivity
import com.llj.loading.databinding.ActivityGuideBinding

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2018/9/20
 */
@Route(path = CRouterClassName.LOADING_GUIDE_ACTIVITY)
class GuideActivity : LoadingMvcBaseActivity<ActivityGuideBinding>() {

  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {
  }
}