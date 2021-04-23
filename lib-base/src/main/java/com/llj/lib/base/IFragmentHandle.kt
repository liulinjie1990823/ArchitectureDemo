package com.llj.lib.base

import android.view.Window
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/5/9
 */
interface IFragmentHandle {

  //region addFragment
  fun addFragment(
      fragmentTransaction: FragmentTransaction, fragment: Fragment,
      @IdRes containerViewId: Int = Window.ID_ANDROID_CONTENT,
      tag: String? = null,
      hide: Boolean = false,
      allowingStateLoss: Boolean = true,
  ) {
    fragmentTransaction.add(containerViewId, fragment, tag)

    if (hide) {
      fragmentTransaction.hide(fragment)
    }
    if (allowingStateLoss) {
      fragmentTransaction.commitAllowingStateLoss()
    } else {
      fragmentTransaction.commit()
    }
  }
  //endregion


  //region addFragmentNow

  fun addFragmentNow(
      fragmentTransaction: FragmentTransaction, fragment: Fragment,
      @IdRes containerViewId: Int = Window.ID_ANDROID_CONTENT,
      tag: String? = null,
      hide: Boolean = false,
      allowingStateLoss: Boolean = true,
  ) {
    fragmentTransaction.add(containerViewId, fragment, tag)
    if (hide) {
      fragmentTransaction.hide(fragment)
    }
    if (allowingStateLoss) {
      fragmentTransaction.commitNowAllowingStateLoss()
    } else {
      fragmentTransaction.commitNow()
    }
  }

  //endregion


  //region removeFragment
  fun removeFragment(fragmentManager: FragmentManager, tag: String?) {
    val fragment = fragmentManager.findFragmentByTag(tag) ?: return
    val ft = fragmentManager.beginTransaction()
    ft.remove(fragment).commitAllowingStateLoss()
  }

  fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
    val ft = fragmentManager.beginTransaction()
    ft.remove(fragment).commitAllowingStateLoss()
  }
  //endregion

  //region removeFragmentNow
  fun removeFragmentNow(fragmentManager: FragmentManager, tag: String?) {
    val fragment = fragmentManager.findFragmentByTag(tag) ?: return
    val ft = fragmentManager.beginTransaction()
    ft.remove(fragment).commitNowAllowingStateLoss()
  }

  fun removeFragmentNow(fragmentManager: FragmentManager, fragment: Fragment) {
    val ft = fragmentManager.beginTransaction()
    ft.remove(fragment).commitNowAllowingStateLoss()
  }
  //endregion


  //region replaceFragment
  fun replaceFragment(fragmentTransaction: FragmentTransaction, fragment: Fragment,
                      @IdRes containerViewId: Int = Window.ID_ANDROID_CONTENT,
                      tag: String? = null) {
    fragmentTransaction.replace(containerViewId, fragment, tag)
    fragmentTransaction.commitAllowingStateLoss()
  }
  //endregion


  //region replaceFragmentNow
  fun replaceFragmentNow(fragmentManager: FragmentManager, fragment: Fragment,
                         @IdRes containerViewId: Int = Window.ID_ANDROID_CONTENT,
                         tag: String? = null) {
    val ft = fragmentManager.beginTransaction()
    ft.replace(containerViewId, fragment, tag)
    ft.commitNowAllowingStateLoss()
  }
  //endregion


  //region customShow
  fun customShow(fragmentManager: FragmentManager, fragment: Fragment) {
    val ft = fragmentManager.beginTransaction()
    ft.show(fragment).commitAllowingStateLoss()
  }

  fun customShow(fragmentManager: FragmentManager, tag: String?) {
    val fragment = fragmentManager.findFragmentByTag(tag) ?: return
    val ft = fragmentManager.beginTransaction()
    ft.show(fragment).commitAllowingStateLoss()
  }
  //endregion

  //region customShowNow
  fun customShowNow(fragmentManager: FragmentManager, fragment: Fragment) {
    val ft = fragmentManager.beginTransaction()
    ft.show(fragment).commitNowAllowingStateLoss()
  }

  fun customShowNow(fragmentManager: FragmentManager, tag: String?) {
    val fragment = fragmentManager.findFragmentByTag(tag) ?: return
    val ft = fragmentManager.beginTransaction()
    ft.show(fragment).commitNowAllowingStateLoss()
  }
  //endregion


  //region customHide
  fun customHide(fragmentManager: FragmentManager, fragment: Fragment) {
    val ft = fragmentManager.beginTransaction()
    ft.hide(fragment).commitAllowingStateLoss()
  }

  fun customHide(fragmentManager: FragmentManager, tag: String?) {
    val fragment = fragmentManager.findFragmentByTag(tag) ?: return
    val ft = fragmentManager.beginTransaction()
    ft.hide(fragment).commitAllowingStateLoss()
  }
  //endregion

  //region customHideNow
  fun customHideNow(fragmentManager: FragmentManager, fragment: Fragment) {
    val ft = fragmentManager.beginTransaction()
    ft.hide(fragment).commitNowAllowingStateLoss()
  }

  fun customHideNow(fragmentManager: FragmentManager, tag: String?) {
    val fragment = fragmentManager.findFragmentByTag(tag) ?: return
    val ft = fragmentManager.beginTransaction()
    ft.hide(fragment).commitNowAllowingStateLoss()
  }
  //endregion

  fun makeFragmentTag(tag: Int): String {
    return "fragment$tag"
  }

  fun makeFragmentTag(tag: String): String {
    return "fragment$tag"
  }

  fun getVpFragmentName(viewPager: ViewPager, id: Long): String {
    return "android:switcher:" + viewPager.id + ":" + id
  }
}