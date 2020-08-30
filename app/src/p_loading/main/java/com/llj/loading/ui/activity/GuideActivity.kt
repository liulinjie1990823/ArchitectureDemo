package com.llj.loading.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.component.service.MiddleMvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
class GuideActivity : AppMvcBaseActivity<ViewBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_guide
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}