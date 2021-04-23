package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import android.view.View
import com.llj.architecturedemo.MainMvcBaseFragment
import com.llj.architecturedemo.R
import com.llj.architecturedemo.databinding.FragmentAnimation1Binding

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/5/11
 */
class AnimationsFragment1 : MainMvcBaseFragment<FragmentAnimation1Binding>() {

  override fun initViews(savedInstanceState: Bundle?) {
    super.initViews(savedInstanceState)
    mViewBinder!!.tvText1.setOnClickListener(View.OnClickListener {
      val beginTransaction = parentFragmentManager.beginTransaction()
      beginTransaction.setCustomAnimations(R.anim.bottom_to_center, R.anim.center_to_bottom, R.anim.pull_in_right, R.anim.push_out_right)
      replaceFragment(beginTransaction, AnimationsFragment2())
    })

    mViewBinder!!.tvText2.setOnClickListener(View.OnClickListener {
      val beginTransaction = parentFragmentManager.beginTransaction()
      beginTransaction.setCustomAnimations(R.anim.bottom_to_center, R.anim.center_to_bottom, R.anim.pull_in_right, R.anim.push_out_right)
      beginTransaction.addToBackStack(null)
      replaceFragment(beginTransaction, AnimationsFragment2())
    })
  }
}