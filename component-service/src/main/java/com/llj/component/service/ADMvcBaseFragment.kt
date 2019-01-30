package com.llj.component.service

import android.app.Activity
import android.os.Bundle
import android.support.annotation.CallSuper
import android.text.TextUtils
import com.llj.lib.base.MvcBaseFragment
import com.llj.lib.statusbar.LightStatusBarCompat

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
abstract class ADMvcBaseFragment : MvcBaseFragment() {


    // true 黑色字体  false 白色
    protected open fun statusBarTextColorBlack(): Boolean {
        return true
    }

    fun isLogin(): Boolean {
        return !TextUtils.isEmpty(ComponentApplication.mUserInfoVo.access_token)
    }

    @CallSuper
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            LightStatusBarCompat.setLightStatusBar((mContext as Activity).window, statusBarTextColorBlack())
        }
    }

    @CallSuper
    override fun initViews(savedInstanceState: Bundle?) {
        LightStatusBarCompat.setLightStatusBar((mContext as Activity).window, statusBarTextColorBlack())
    }

    fun checkLogin(): Boolean {
        return if (TextUtils.isEmpty(ComponentApplication.mUserInfoVo.access_token)) {
            //            CRouter.start(CRouter.LOGIN_PHONE_LOGIN);
            false
        } else true
    }
}
