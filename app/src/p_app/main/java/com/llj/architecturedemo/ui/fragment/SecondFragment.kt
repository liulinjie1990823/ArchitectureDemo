package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.llj.architecturedemo.MainMvcBaseFragment
import com.llj.architecturedemo.R

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
class SecondFragment : MainMvcBaseFragment<ViewBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_second
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
    }

    override fun initData() {
    }
}