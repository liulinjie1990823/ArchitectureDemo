package com.llj.architecturedemo.module

import com.llj.architecturedemo.ui.activity.SecondActivity
import com.llj.architecturedemo.view.SecondView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Module
class SecondActivityModule {

    @Provides
    internal fun provideView(activity: SecondActivity): SecondView {
        return activity
    }
}
