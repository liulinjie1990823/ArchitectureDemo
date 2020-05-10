package com.llj.architecturedemo.ui.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.architecturedemo.vo.DataVo
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseFragment


/**
 * describe 画布
 *
 * @author liulinjie
 * @date 2020/4/17 5:44 PM
 */
@Route(path = CRouter.APP_CANVAS_ACTIVITY)
class CanvasActivity : DataListActivity() {

  override fun getData(data: ArrayList<DataVo?>) {
    data.add(DataVo("drawRect", drawRect))
    data.add(DataVo("drawRoundRect", drawRoundRect))
    data.add(DataVo("drawCircle", drawCircle))
    data.add(DataVo("drawOval", drawOval))
    data.add(DataVo("drawArc", drawArc))
    data.add(DataVo("drawLine", drawLine))
    data.add(DataVo("drawLines", drawLines))
    data.add(DataVo("drawPath", drawPath))
    data.add(DataVo("drawBitmap1", drawBitmap1))
    data.add(DataVo("drawBitmap2", drawBitmap2))
    data.add(DataVo("drawBitmap3", drawBitmap3))
    data.add(DataVo("translate", translate))
    data.add(DataVo("scale", scale))
    data.add(DataVo("rotate", rotate))
  }

  override fun onClick(view: View, item: DataVo) {
    val fragment: MvcBaseFragment<*> = ARouter.getInstance().build(CRouter.APP_CANVAS_FRAGMENT)
        .withInt(CRouter.KEY_TYPE, item.type)
        .navigation() as MvcBaseFragment<*>
    fragment.showsDialog = true
    fragment.mUseSoftInput = false
    fragment.mUseTranslucent = true
    fragment.mTextColorBlack = true
    fragment.smartShowNow(supportFragmentManager, CRouter.APP_CANVAS_FRAGMENT)
  }

  companion object {
    @JvmStatic
    fun callStatic() {
    }

    fun callNonStatic() {}

    const val drawRect = 110
    val drawRoundRect = 1

    @JvmField
    val drawCircle = 2

    @JvmStatic
    val drawOval = 3

    var drawArc = 4

    @JvmField
    var drawLine = 5

    @JvmStatic
    var drawLines = 6

    @JvmStatic
    val drawPath = 7

    @JvmStatic
    val drawBitmap1 = 8

    @JvmStatic
    val drawBitmap2 = 9

    @JvmStatic
    val drawBitmap3 = 10

    @JvmStatic
    val translate = 11

    @JvmStatic
    val scale = 12

    @JvmStatic
    val rotate = 13
  }
}