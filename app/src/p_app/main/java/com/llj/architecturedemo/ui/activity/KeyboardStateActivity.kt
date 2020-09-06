package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityKeyboardStateBinding
import com.llj.application.router.CRouter
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.base.listeners.KeyboardStateObserver
import com.llj.lib.base.listeners.KeyboardStateObserver.OnKeyboardVisibilityListener
import com.llj.lib.statusbar.LightStatusBarCompat

/**
 * describe Keyboard
 *
 * @author liulinjie
 * @date 2020/4/19 2:14 PM
 */
@Route(path = CRouter.APP_KEYBOARD_STATE_ACTIVITY)
class KeyboardStateActivity : MainMvcBaseActivity<ActivityKeyboardStateBinding>() {


  @Autowired(name = CRouter.KEY_TYPE) @JvmField var mType: Int = 0


  override fun initViews(savedInstanceState: Bundle?) {
    ARouter.getInstance().inject(this);
    if (mType == 0) {
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
      setTranslucentStatusBar(window, true)
    } else if (mType == 1) {
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
      LightStatusBarCompat.setLightStatusBar(window, true)
    } else if (mType == 2) {
      //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN下RESIZE会失效，需要自己设置布局高度
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
      setTranslucentStatusBar(window, true)
    } else if (mType == 3) {
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
      LightStatusBarCompat.setLightStatusBar(window, true)
    }

    KeyboardStateObserver.getKeyboardStateObserver(window).setKeyboardVisibilityListener(
        object : OnKeyboardVisibilityListener {
          override fun onKeyboardShow(resizeHeight: Int) {
            mViewBinder!!.tvText.text = "rootView getWindowVisibleDisplayFrame height:$resizeHeight"
            mViewBinder!!.tvText2.text = "rootView height:" + mViewBinder!!.vRoot.measuredHeight
            if (mType == 2) {
              mViewBinder!!.vRoot.layoutParams.height = resizeHeight + DisplayHelper.STATUS_BAR_HEIGHT
              mViewBinder!!.vRoot.requestLayout()
            }
          }

          override fun onKeyboardHide(resizeHeight: Int) {
            mViewBinder!!.tvText.text = "rootView getWindowVisibleDisplayFrame height:$resizeHeight"
            mViewBinder!!.tvText2.text = "rootView height:" + mViewBinder!!.vRoot.measuredHeight
            if (mType == 2) {
              mViewBinder!!.vRoot.layoutParams.height = resizeHeight + DisplayHelper.STATUS_BAR_HEIGHT
              mViewBinder!!.vRoot.requestLayout()
            }
          }

        })
  }

  override fun initData() {

  }

}