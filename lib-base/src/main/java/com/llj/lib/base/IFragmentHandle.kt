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

  fun addFragment(fragmentTransaction: FragmentTransaction,
                  fragment: Fragment) {
    addFragment(fragmentTransaction, Window.ID_ANDROID_CONTENT, fragment, null, true, false)
  }

  fun addFragment(fragmentTransaction: FragmentTransaction,
                  fragment: Fragment,
                  allowingStateLoss: Boolean) {
    addFragment(fragmentTransaction, Window.ID_ANDROID_CONTENT, fragment, null, allowingStateLoss, false)
  }

  fun addFragment(fragmentTransaction: FragmentTransaction,
                  @IdRes containerViewId: Int,
                  fragment: Fragment) {
    addFragment(fragmentTransaction, containerViewId, fragment, null, true, false)
  }

  fun addFragment(fragmentTransaction: FragmentTransaction,
                  @IdRes containerViewId: Int,
                  fragment: Fragment,
                  allowingStateLoss: Boolean) {
    addFragment(fragmentTransaction, containerViewId, fragment, null, allowingStateLoss, false)
  }

  fun addFragment(fragmentTransaction: FragmentTransaction,
                  @IdRes containerViewId: Int,
                  fragment: Fragment,
                  tag: String?,
                  allowingStateLoss: Boolean) {
    addFragment(fragmentTransaction, containerViewId, fragment, tag, allowingStateLoss, false)
  }

  fun addFragment(fragmentTransaction: FragmentTransaction,
                  @IdRes containerViewId: Int,
                  fragment: Fragment,
                  allowingStateLoss: Boolean,
                  hide: Boolean) {
    addFragment(fragmentTransaction, containerViewId, fragment, null, allowingStateLoss, hide)
  }

  fun addFragment(fragmentTransaction: FragmentTransaction,
                  @IdRes containerViewId: Int,
                  fragment: Fragment, tag: String?,
                  allowingStateLoss: Boolean,
                  hide: Boolean) {

    if (tag != null && tag.isNotEmpty()) {
      fragmentTransaction.add(containerViewId, fragment, tag)
    } else {
      fragmentTransaction.add(containerViewId, fragment)
    }
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
  fun addFragmentNow(fragmentTransaction: FragmentTransaction,
                     fragment: Fragment) {
    addFragmentNow(fragmentTransaction, Window.ID_ANDROID_CONTENT, fragment, null, true, false)
  }

  fun addFragmentNow(fragmentTransaction: FragmentTransaction,
                     fragment: Fragment,
                     allowingStateLoss: Boolean) {
    addFragmentNow(fragmentTransaction, Window.ID_ANDROID_CONTENT, fragment, null, allowingStateLoss, false)
  }

  fun addFragmentNow(fragmentTransaction: FragmentTransaction,
                     @IdRes containerViewId: Int,
                     fragment: Fragment) {
    addFragmentNow(fragmentTransaction, containerViewId, fragment, null, false, false)
  }

  fun addFragmentNow(fragmentTransaction: FragmentTransaction,
                     @IdRes containerViewId: Int,
                     allowingStateLoss: Boolean,
                     fragment: Fragment) {
    addFragmentNow(fragmentTransaction, containerViewId, fragment, null, allowingStateLoss, false)
  }

  fun addFragmentNow(fragmentTransaction: FragmentTransaction,
                     @IdRes containerViewId: Int,
                     fragment: Fragment
                     , tag: String?,
                     allowingStateLoss: Boolean) {
    addFragmentNow(fragmentTransaction, containerViewId, fragment, tag, allowingStateLoss, false)
  }

  fun addFragmentNow(fragmentTransaction: FragmentTransaction,
                     @IdRes containerViewId: Int,
                     fragment: Fragment,
                     allowingStateLoss: Boolean,
                     hide: Boolean) {
    addFragmentNow(fragmentTransaction, containerViewId, fragment, null, allowingStateLoss, hide)
  }

  fun addFragmentNow(fragmentTransaction: FragmentTransaction,
                     @IdRes containerViewId: Int,
                     fragment: Fragment, tag: String?,
                     allowingStateLoss: Boolean,
                     hide: Boolean) {
    if (tag != null && tag.isNotEmpty()) {
      fragmentTransaction.add(containerViewId, fragment, tag)
    } else {
      fragmentTransaction.add(containerViewId, fragment)
    }
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
  fun replaceFragment(fragmentTransaction: FragmentTransaction,
                      @IdRes containerViewId: Int,
                      fragment: Fragment) {
    replaceFragment(fragmentTransaction, containerViewId, fragment, null)
  }

  fun replaceFragment(fragmentTransaction: FragmentTransaction,
                      @IdRes containerViewId: Int,
                      fragment: Fragment, tag: String?) {
    if (tag != null && tag.isNotEmpty()) {
      fragmentTransaction.replace(containerViewId, fragment, tag)
    } else {
      fragmentTransaction.replace(containerViewId, fragment)
    }
    fragmentTransaction.commitAllowingStateLoss()
  }
  //endregion


  //region replaceFragmentNow
  fun replaceFragmentNow(fragmentManager: FragmentManager,
                         @IdRes containerViewId: Int,
                         fragment: Fragment) {
    replaceFragmentNow(fragmentManager, containerViewId, fragment, null)
  }

  fun replaceFragmentNow(fragmentManager: FragmentManager,
                         @IdRes containerViewId: Int,
                         fragment: Fragment, tag: String?) {
    val ft = fragmentManager.beginTransaction()
    if (tag != null && tag.isNotEmpty()) {
      ft.replace(containerViewId, fragment, tag)
    } else {
      ft.replace(containerViewId, fragment)
    }
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