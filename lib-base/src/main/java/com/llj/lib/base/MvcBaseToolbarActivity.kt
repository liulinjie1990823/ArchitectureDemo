package com.llj.lib.base

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-07-25
 */
abstract class MvcBaseToolbarActivity<V : ViewBinding> : MvcBaseActivity<V>() {

  lateinit var mClToolbar: ConstraintLayout
  lateinit var mIvTbClose: ImageView
  lateinit var mTvTbTitle: TextView
  lateinit var mTvTbRight: TextView
  lateinit var mIvTbRight: ImageView
  lateinit var mVDivide: View


  override fun layoutId(): Int {
    return 0
  }

  override fun layoutView(): View? {
    val mRootView = layoutInflater.inflate(R.layout.mvc_base_title_activity, null) as ViewGroup

    applyNavigationInsets(mRootView)

    if (layoutId() != 0) {
      initToolbar(mRootView)
      layoutInflater.inflate(layoutId(), mRootView, true)
    } else {
      var layoutViewBinding = layoutViewBinding()
      if (layoutViewBinding == null) {
        //如果忘记设置则使用反射设置
        layoutViewBinding = reflectViewBinder(layoutInflater, mTagLog)
      }
      if (layoutViewBinding != null) {
        mViewBinder = layoutViewBinding
        mRootView.addView(mViewBinder.root, LinearLayout.LayoutParams(-1, -1))
      }
    }
    return mRootView
  }

  private fun initToolbar(view: View) {

    mClToolbar = view.findViewById(R.id.cl_toolbar)
    mIvTbClose = view.findViewById(R.id.iv_close)
    mTvTbTitle = view.findViewById(R.id.tv_title)
    mTvTbRight = view.findViewById(R.id.tv_right)
    mIvTbRight = view.findViewById(R.id.iv_right)
    mVDivide = view.findViewById(R.id.v_divide)


    //根据配置文件设置toolbar
    val toolbarConfig = AppManager.getInstance().toolbarConfig
    if (toolbarConfig != null) {
      if (toolbarConfig.toolbarBackgroundColorRes >= 0) {
        mClToolbar.setBackgroundColor(getCompatColor(this, toolbarConfig.toolbarBackgroundColorRes))
      }
      if (toolbarConfig.toolbarBackgroundDrawable != null) {
        mClToolbar.background = toolbarConfig.toolbarBackgroundDrawable
      }
      mIvTbClose.setImageResource(toolbarConfig.leftImageRes)
      mTvTbTitle.setTextSize(toolbarConfig.titleUnit, toolbarConfig.titleSize.toFloat())
      if (toolbarConfig.titleColorRes >= 0) {
        mTvTbTitle.setTextColor(getCompatColor(this, toolbarConfig.titleColorRes))
      }
      if (toolbarConfig.rightImageRes >= 0) {
        mIvTbRight.setImageResource(toolbarConfig.rightImageRes)
      }
      mTvTbRight.setTextSize(toolbarConfig.rightUnit, toolbarConfig.rightTextSize.toFloat())
      if (toolbarConfig.rightTextColorRes > 0) {
        mTvTbRight.setTextColor(getCompatColor(this, toolbarConfig.rightTextColorRes))
      }
      val layoutParams2 = mVDivide!!.layoutParams as ConstraintLayout.LayoutParams
      layoutParams2.height = toolbarConfig.divideHeight
      if (toolbarConfig.divideBackgroundColorRes >= 0) {
        mVDivide.setBackgroundColor(getCompatColor(this, toolbarConfig.divideBackgroundColorRes))
      }
      mVDivide.visibility = toolbarConfig.visibility
    }
  }
}