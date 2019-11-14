package com.llj.component.service

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector

/**
 * ArchitectureDemo.
 * dagger中各个子Component
 * author llj
 * date 2019/3/26
 */
interface IInject {

    fun activityInjector(): DispatchingAndroidInjector<Activity>
    fun supportFragmentInjector(): DispatchingAndroidInjector<androidx.fragment.app.Fragment>
}