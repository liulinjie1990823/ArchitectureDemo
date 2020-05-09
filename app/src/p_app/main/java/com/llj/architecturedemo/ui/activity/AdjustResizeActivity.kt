package com.llj.architecturedemo.ui.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.architecturedemo.vo.DataVo
import com.llj.component.service.arouter.CRouter

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020-02-13 15:39
 */
@Route(path = CRouter.APP_ADJUST_RESIZE_ACTIVITY2)
class AdjustResizeActivity : DataListActivity() {

  override fun getData(data: ArrayList<DataVo?>) {
    data.add(DataVo("adjustPan Translucent true", 0))
    data.add(DataVo("adjustResize Translucent false", 1))
    data.add(DataVo("adjustResize Translucent true", 2))
    data.add(DataVo("adjustResize Translucent false", 3))
  }

  override fun onClick(view: View, item: DataVo) {
    ARouter.getInstance().build(CRouter.APP_KEYBOARD_STATE_ACTIVITY)
        .withInt(CRouter.KEY_TYPE, item.type)
        .navigation();
  }
}