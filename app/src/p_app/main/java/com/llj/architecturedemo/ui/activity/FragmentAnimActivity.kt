package com.llj.architecturedemo.ui.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.R
import com.llj.architecturedemo.ui.fragment.AnimationsFragment1
import com.llj.architecturedemo.vo.DataVo
import com.llj.component.service.arouter.CRouter

/**
 * ArchitectureDemo.
 * describe: 验证fragment的动画效果
 * @author llj
 * @date 2020/5/11
 */
@Route(path = CRouter.APP_FRAGMENT_ANIM_ACTIVITY)
class FragmentAnimActivity : DataListActivity() {
  companion object {
    @JvmField
    val setCustomAnimations2 = 0
    val setCustomAnimations4 = 1

  }

  override fun getData(data: ArrayList<DataVo?>) {
    data.add(DataVo("setCustomAnimations2", FragmentAnimActivity.setCustomAnimations2))
    data.add(DataVo("setCustomAnimations4", FragmentAnimActivity.setCustomAnimations4))
  }

  override fun onClick(view: View, item: DataVo) {

    val animationsFragment1 = AnimationsFragment1()
    val beginTransaction = supportFragmentManager.beginTransaction()
    if (item.type == FragmentAnimActivity.setCustomAnimations2) {
      beginTransaction.setCustomAnimations(R.anim.bottom_to_center, R.anim.center_to_bottom, R.anim.pull_in_right, R.anim.push_out_right)
    } else if (item.type == FragmentAnimActivity.setCustomAnimations4) {
      beginTransaction.addToBackStack(null)
      beginTransaction.setCustomAnimations(R.anim.bottom_to_center, R.anim.center_to_bottom, R.anim.pull_in_right, R.anim.push_out_right)
    }
    animationsFragment1.addFragment(beginTransaction, animationsFragment1)
  }

  override fun onBackPressed() {
    super.onBackPressed()
  }
}