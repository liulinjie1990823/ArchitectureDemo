package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.application.router.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
@Route(path = CRouter.APP_LINEAR_LAYOUT_ACTIVITY)
class LinearLayoutActivity : MainMvcBaseActivity<ViewBinding>() {
    override fun layoutId(): Int {
        return R.layout.activity_linear_layout
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}