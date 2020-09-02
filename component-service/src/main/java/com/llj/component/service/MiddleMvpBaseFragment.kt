package com.llj.component.service

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
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
abstract class MiddleMvpBaseFragment<V : ViewBinding, P : IBasePresenter> : MvpBaseFragment<V, P>(), HasAndroidInjector {

  //下面代码是为了在component-service中生成ComponentMvpBaseFragment_MembersInjector对象
  //否则会在多个module中生成多个ComponentMvpBaseFragment_MembersInjector对象
  @Inject
  lateinit var mSupportFragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

  override fun androidInjector(): AndroidInjector<Any> {
    return AndroidInjector { }
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

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
  }

  override fun onResume() {
    super.onResume()
  }

  override fun onStart() {
    super.onStart()
  }

  override fun onPause() {
    super.onPause()
  }

  override fun onStop() {
    super.onStop()
  }

  override fun onDestroyView() {
    super.onDestroyView()
  }

  override fun onDestroy() {
    super.onDestroy()
  }

  fun checkLogin(): Boolean {
    return false
  }


  fun isLogin(): Boolean {
    return false
  }
}
