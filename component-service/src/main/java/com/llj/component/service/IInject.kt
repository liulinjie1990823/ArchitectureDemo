package com.llj.component.service

import android.app.Activity
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/26
 */
interface IInject {

    fun activityInjector(): DispatchingAndroidInjector<Activity>
    fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>
}