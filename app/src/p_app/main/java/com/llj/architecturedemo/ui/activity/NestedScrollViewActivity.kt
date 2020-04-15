package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
@Route(path = CRouter.APP_NESTED_SCROLLVIEW_ACTIVITY)
class NestedScrollViewActivity : AppMvcBaseActivity<ViewBinding>() {
    override fun layoutId(): Int {
        return R.layout.activity_nested_scrollview
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}