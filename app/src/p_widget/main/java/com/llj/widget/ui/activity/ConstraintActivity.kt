package com.llj.widget.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.application.router.CRouter

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/7/5
 */
@Route(path = CRouter.WIDGET_CONSTRAINT_ACTIVITY)
class ConstraintActivity : MainMvcBaseActivity<ViewBinding>() {

  override fun layoutId(): Int {
    return R.layout.activity_constraint
  }

  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {

  }
}
