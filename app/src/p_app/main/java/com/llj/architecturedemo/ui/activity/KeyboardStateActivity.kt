package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityKeyboardStateBinding
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.listeners.KeyboardStateObserver
import com.llj.lib.base.listeners.KeyboardStateObserver.OnKeyboardVisibilityListener

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/4/19 2:14 PM
 */
@Route(path = CRouter.APP_KEYBOARD_STATE_ACTIVITY)
class KeyboardStateActivity : AppMvcBaseActivity<ActivityKeyboardStateBinding>() {

  override fun layoutViewBinding(): ActivityKeyboardStateBinding? {
    return ActivityKeyboardStateBinding.inflate(layoutInflater)
  }

  @Autowired(name = CRouter.KEY_TYPE) @JvmField var mType: Int = 0


  override fun initViews(savedInstanceState: Bundle?) {
    ARouter.getInstance().inject(this);
    if (mType == 0) {
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    } else if (mType == 1) {
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }
    setTranslucentStatusBar(window, true)

    KeyboardStateObserver.getKeyboardStateObserver(window).setKeyboardVisibilityListener(
        object : OnKeyboardVisibilityListener {
          override fun onKeyboardShow(resizeHeight: Int) {
            mViewBinder!!.tvText.text = "contentView height:$resizeHeight"
          }

          override fun onKeyboardHide(resizeHeight: Int) {
            mViewBinder!!.tvText.text = "contentView height:$resizeHeight"
          }

        })
  }

  override fun initData() {
  }

}