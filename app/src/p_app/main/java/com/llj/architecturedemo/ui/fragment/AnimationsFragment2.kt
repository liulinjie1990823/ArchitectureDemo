package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import android.view.View
import com.llj.architecturedemo.AppMvcBaseFragment
import com.llj.architecturedemo.databinding.FragmentAnimation2Binding

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/5/11
 */
class AnimationsFragment2 : AppMvcBaseFragment<FragmentAnimation2Binding>() {

  override fun initViews(savedInstanceState: Bundle?) {
    super.initViews(savedInstanceState)
    mViewBinder!!.root.setOnClickListener(View.OnClickListener {
    })
  }
}