package com.llj.widget.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.application.router.CRouter
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.R

/**
 * ArchitectureDemo
 * describe:
 *
 * @author llj
 * @date 2018/7/5
 */
@Route(path = CRouter.APP_CONSTRAINT_ACTIVITY)
class ConstraintActivity : MainMvcBaseActivity<ViewBinding>() {


  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
  }

  override fun layoutId(): Int {
    return R.layout.activity_constraint
  }

  override fun initViews(savedInstanceState: Bundle?) {
  }

  override fun initData() {

  }
}
