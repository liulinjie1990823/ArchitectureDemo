package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.application.router.CRouter
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityDisplayBinding

/**
 * describe
 *
 * @author
 * @date
 */
@Route(path = CRouter.APP_DISPLAY_ACTIVITY)
class DisplayActivity : MainMvcBaseActivity<ActivityDisplayBinding>() {

  override fun initViews(savedInstanceState: Bundle?) {
    val densityDpi = mContext.resources.displayMetrics.densityDpi //440
    val scaledDensity = mContext.resources.displayMetrics.scaledDensity //2.75
    val density = mContext.resources.displayMetrics.density //2.75
    val xdpi = mContext.resources.displayMetrics.xdpi //394
    val ydpi = mContext.resources.displayMetrics.ydpi
    val width = mContext.resources.displayMetrics.widthPixels
    val height = mContext.resources.displayMetrics.heightPixels
  }

  override fun initData() {
  }
}