package com.llj.loading.ui.activity

import android.os.Bundle
import com.llj.architecturedemo.R
import com.llj.component.service.ComponentMvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
class GuideActivity : ComponentMvcBaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_guide
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}