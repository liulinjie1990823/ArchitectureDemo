package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.vo.DataVo
import com.llj.application.router.CRouter

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/5/14 8:43 PM
 */
@Route(path = CRouter.APP_JNI_TEST_ACTIVITY)
class JniTestActivity : DataListActivity() {

  override fun initViews(savedInstanceState: Bundle?) {
    super.initViews(savedInstanceState)
  }

  override fun getData(data: ArrayList<DataVo?>) {
    data.add(DataVo("stringFromJNI", 0))
    data.add(DataVo("stringFromJNI2", 1))
    data.add(DataVo("test3", 2))
    data.add(DataVo("test4", 3))
  }

  override fun onClick(view: View, item: DataVo) {
    when {
//      item.type == 0 -> {
//        showLongToast(jniTest.stringFromJNI())
//      }
//      item.type == 1 -> {
//        showLongToast(jniTest.stringFromJNI2())
//      }
//      item.type == 2 -> {
//        showLongToast(jniTest.test3().toString())
//      }
//      item.type == 3 -> {
//        showLongToast(jniTest.test4().toString())
//      }
    }
  }
}