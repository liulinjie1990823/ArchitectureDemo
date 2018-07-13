package com.llj.widget.ui.activity

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MyBaseActivity
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.widget.ui.presenter.CircleViewPresenter
import com.llj.widget.ui.view.CircleViewView
import kotlinx.android.synthetic.main.activity_circle_view.*

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/11
 */
@Route(path = CRouter.CIRCLE_VIEW_ACTIVITY)
class CircleViewActivity : MyBaseActivity<CircleViewPresenter>(), CircleViewView {

    override fun layoutId(): Int {
        return R.layout.activity_circle_view
    }

    override fun initViews(savedInstanceState: Bundle?) {
        val beginColor = "#004A98"
        val endColor = "#458BD5"
        mIvTopBag.setRectF(0f, 0f, 1080f, 1080 * 440 / 750f * 2, 100f, 0f)
        val layoutParams = mIvTopBag.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = (-(mIvTopBag.rectF.bottom / 2)).toInt()

        mIvTopBag.setColor(beginColor, endColor)


        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(Color.parseColor(beginColor), Color.parseColor(endColor)))
        mIvTopBagSticky.setImageDrawable(gradientDrawable)

    }

    override fun initData() {}

}
