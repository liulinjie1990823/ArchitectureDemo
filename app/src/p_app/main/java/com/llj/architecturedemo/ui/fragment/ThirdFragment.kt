package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.llj.architecturedemo.R
import com.llj.lib.base.MvcBaseFragment

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
class ThirdFragment : MvcBaseFragment<ViewBinding>() {
    override fun layoutId(): Int {
    return R.layout.fragment_third
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}