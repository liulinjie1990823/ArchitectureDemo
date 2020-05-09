package com.llj.lib.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2020/5/9
 */
interface IFragmentHandle {

  //region addFragment
  fun addFragment(fragmentManager: FragmentManager,
                  @IdRes containerViewId: Int,
                  fragment: Fragment) {
    addFragment(fragmentManager, containerViewId, fragment, null, false)
  }

  fun addFragment(fragmentManager: FragmentManager,
                  @IdRes containerViewId: Int,
                  fragment: Fragment
                  , tag: String?) {
    addFragment(fragmentManager, containerViewId, fragment, tag, false)
  }

  fun addFragment(fragmentManager: FragmentManager,
                  @IdRes containerViewId: Int,
                  fragment: Fragment,
                  hide: Boolean) {
    addFragment(fragmentManager, containerViewId, fragment, null, hide)
  }

  fun addFragment(fragmentManager: FragmentManager,
                  @IdRes containerViewId: Int,
                  fragment: Fragment, tag: String?, hide: Boolean) {
    val ft = fragmentManager.beginTransaction()
    if (tag != null && tag.isNotEmpty()) {
      ft.add(containerViewId, fragment, tag)
    } else {
      ft.add(containerViewId, fragment)
    }
    if (hide) {
      ft.hide(fragment)
    }
    ft.commitAllowingStateLoss()
  }
  //endregion


  //region addFragmentNow
  fun addFragmentNow(fragmentManager: FragmentManager,
                     @IdRes containerViewId: Int,
                     fragment: Fragment) {
    addFragmentNow(fragmentManager, containerViewId, fragment, null, false)
  }

  fun addFragmentNow(fragmentManager: FragmentManager,
                     @IdRes containerViewId: Int,
                     fragment: Fragment
                     , tag: String?) {
    addFragmentNow(fragmentManager, containerViewId, fragment, tag, false)
  }

  fun addFragmentNow(fragmentManager: FragmentManager,
                     @IdRes containerViewId: Int,
                     fragment: Fragment,
                     hide: Boolean) {
    addFragmentNow(fragmentManager, containerViewId, fragment, null, hide)
  }

  fun addFragmentNow(fragmentManager: FragmentManager,
                     @IdRes containerViewId: Int,
                     fragment: Fragment, tag: String?, hide: Boolean) {
    val ft = fragmentManager.beginTransaction()
    if (tag != null && tag.isNotEmpty()) {
      ft.add(containerViewId, fragment, tag)
    } else {
      ft.add(containerViewId, fragment)
    }
    if (hide) {
      ft.hide(fragment)
    }
    ft.commitNowAllowingStateLoss()
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
  fun replaceFragment(fragmentManager: FragmentManager,
                      @IdRes containerViewId: Int,
                      fragment: Fragment) {
    replaceFragment(fragmentManager, containerViewId, fragment, null)
  }

  fun replaceFragment(fragmentManager: FragmentManager,
                      @IdRes containerViewId: Int,
                      fragment: Fragment, tag: String?) {
    val ft = fragmentManager.beginTransaction()
    if (tag != null && tag.isNotEmpty()) {
      ft.replace(containerViewId, fragment, tag)
    } else {
      ft.replace(containerViewId, fragment)
    }
    ft.commitAllowingStateLoss()
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