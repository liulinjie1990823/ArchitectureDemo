package com.llj.architecturedemo.ui.module

import com.llj.architecturedemo.ui.activity.RequestActivity
import com.llj.architecturedemo.ui.view.IRequestView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Module
class RequestActivityModule {
    @Provides
    internal fun provideView(activity: RequestActivity): IRequestView {
        return activity
    }
}
