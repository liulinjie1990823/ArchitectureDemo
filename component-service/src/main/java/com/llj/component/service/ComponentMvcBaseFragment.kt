package com.llj.component.service

import android.app.Activity
import android.os.Bundle
import android.support.annotation.CallSuper
import android.text.TextUtils
import com.llj.lib.base.MvcBaseFragment
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.tracker.ITracker
import com.llj.lib.tracker.PageName
import java.util.*

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
abstract class ComponentMvcBaseFragment : MvcBaseFragment(), ITracker {

    //页面统计用
    private var mPageName: String? = null
    private var mPageId: String? = null

    override fun getPageName(): String {
        if (mPageName == null) {
            val annotation = javaClass.getAnnotation(PageName::class.java)
            mPageName = annotation?.value ?: this.javaClass.simpleName
        }
        return mPageName!!
    }

    override fun getPageId(): String {
        if (mPageId == null) {
            mPageId = UUID.randomUUID().toString()
        }
        return mPageId!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        pageName
        pageId
        super.onCreate(savedInstanceState)
    }

    // true 黑色字体  false 白色
    protected open fun statusBarTextColorBlack(): Boolean {
        return true
    }

    fun isLogin(): Boolean {
        return !TextUtils.isEmpty(MiddleApplication.mUserInfoVo.access_token)
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
        return if (TextUtils.isEmpty(MiddleApplication.mUserInfoVo.access_token)) {
            //            CRouter.start(CRouter.LOGIN_PHONE_LOGIN);
            false
        } else true
    }
}
