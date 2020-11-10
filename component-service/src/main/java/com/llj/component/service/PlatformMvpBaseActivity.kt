package com.llj.component.service

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.mvp.IBasePresenter
import com.llj.lib.tracker.ITracker
import com.llj.lib.tracker.PageName
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.*
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/12/13
 */
abstract class PlatformMvpBaseActivity<V : ViewBinding, P : IBasePresenter> : MvpBaseActivity<V, P>(), HasAndroidInjector, ITracker {

  //下面代码是为了在component-service中生成MiddleMvpBaseActivity_MembersInjector对象
  //否则会在多个module中生成多个MiddleMvpBaseActivity_MembersInjector对象
  @Inject
  lateinit var mSupportFragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

  override fun androidInjector(): AndroidInjector<Any> {
    return AndroidInjector { }
  }

  var mActivityOpenEnterAnimation: Int = 0
  var mActivityOpenExitAnimation: Int = 0
  var mActivityCloseEnterAnimation: Int = 0
  var mActivityCloseExitAnimation: Int = 0
  var mIsWindowIsTranslucent: Boolean = false

  protected var mUseAnim: Boolean = false

  private var mPageName: String? = null
  private var mPageId: String? = null

  override fun getPageName(): String {
    if (mPageName == null) {
      val annotation = javaClass.getAnnotation(PageName::class.java)
      mPageName = annotation?.value ?: javaClass.simpleName
    }
    return mPageName!!
  }

  override fun getPageId(): String {
    if (mPageId == null) {
      mPageId = UUID.randomUUID().toString()
    }
    return mPageId!!
  }

  private val isWindowIsTranslucent: Boolean
    get() {
      val activityStyle = theme.obtainStyledAttributes(intArrayOf(android.R.attr.windowIsTranslucent))
      val windowIsTranslucent = activityStyle.getBoolean(0, false)
      activityStyle.recycle()
      return windowIsTranslucent
    }


  override fun onCreate(savedInstanceState: Bundle?) {
    if (mUseAnim) {
      mIsWindowIsTranslucent = isWindowIsTranslucent
      initAnim()
      overridePendingTransition(mActivityOpenEnterAnimation, mActivityOpenExitAnimation)
    }
    pageName
    pageId

    super.onCreate(savedInstanceState)
  }

  private fun initAnim() {
    var activityStyle = theme.obtainStyledAttributes(intArrayOf(android.R.attr.windowAnimationStyle))
    val windowAnimationStyleResId = activityStyle.getResourceId(0, 0)
    activityStyle.recycle()

    activityStyle = theme.obtainStyledAttributes(windowAnimationStyleResId, intArrayOf(android.R.attr.activityOpenEnterAnimation, android.R.attr.activityOpenExitAnimation))
    mActivityOpenEnterAnimation = activityStyle.getResourceId(0, 0)
    mActivityOpenExitAnimation = activityStyle.getResourceId(1, 0)

    activityStyle = theme.obtainStyledAttributes(windowAnimationStyleResId, intArrayOf(android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation))
    mActivityCloseEnterAnimation = activityStyle.getResourceId(0, 0)
    mActivityCloseExitAnimation = activityStyle.getResourceId(1, 0)
    activityStyle.recycle()
  }

  override fun finish() {
    super.finish()
    if (mUseAnim) {
      overridePendingTransition(mActivityCloseEnterAnimation, mActivityCloseExitAnimation)
    }
  }
}
