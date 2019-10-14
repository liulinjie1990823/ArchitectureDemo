package com.llj.component.service

import android.app.Activity
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.text.TextUtils
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.mvp.IBasePresenter
import com.llj.lib.statusbar.LightStatusBarCompat
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/5
 */
abstract class MiddleMvpBaseFragment<P : IBasePresenter> : MvpBaseFragment<P>(), HasAndroidInjector {

    //下面代码是为了在component-service中生成ComponentMvpBaseFragment_MembersInjector对象
    //否则会在多个module中生成多个ComponentMvpBaseFragment_MembersInjector对象
    @Inject
    lateinit var mSupportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun androidInjector(): AndroidInjector<Any> {
        return AndroidInjector { }
    }

    // true 黑色字体  false 白色
    protected open fun statusBarTextColorBlack(): Boolean {
        return true
    }

    fun isLogin(): Boolean {
        return !TextUtils.isEmpty(MiddleApplication.mUserInfoVo.access_token)
    }

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
