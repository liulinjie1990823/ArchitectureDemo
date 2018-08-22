package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
@Route(path = CRouter.APP_LINEAR_LAYOUT_ACTIVITY)
class LinearLayoutActivity : MvcBaseActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_linear_layout
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}