package com.llj.component.service

import android.os.Bundle
import android.support.v4.app.Fragment
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.mvp.IBasePresenter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/12/13
 */
abstract class ComponentMvpBaseActivity<P : IBasePresenter> : MvpBaseActivity<P>(), HasSupportFragmentInjector {

    //下面代码是为了在component-service中生成ComponentMvpBaseActivity_MembersInjector对象
    //否则会在多个module中生成多个ComponentMvpBaseActivity_MembersInjector对象
    @Inject
    lateinit var mSupportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
       return mSupportFragmentInjector
    }


    var mActivityOpenEnterAnimation: Int = 0
    var mActivityOpenExitAnimation: Int = 0
    var mActivityCloseEnterAnimation: Int = 0
    var mActivityCloseExitAnimation: Int = 0
    var mIsWindowIsTranslucent: Boolean = false

    private val isWindowIsTranslucent: Boolean
        get() {
            val activityStyle = theme.obtainStyledAttributes(intArrayOf(android.R.attr.windowIsTranslucent))
            val windowIsTranslucent = activityStyle.getBoolean(0, false)
            activityStyle.recycle()
            return windowIsTranslucent
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        mIsWindowIsTranslucent = isWindowIsTranslucent
        initAnim()
        overridePendingTransition(mActivityOpenEnterAnimation, mActivityOpenExitAnimation)
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
        overridePendingTransition(mActivityCloseEnterAnimation, mActivityCloseExitAnimation)
    }

    companion object {
        val PAGE_NAME = "PageName"
    }
}
