package com.llj.architecturedemo.ui.module

import com.llj.architecturedemo.ui.service.LoadingService
import com.llj.architecturedemo.ui.view.IPreLoadingView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Module
class LoadingServiceModule {
    @Provides
    internal fun provideView(service: LoadingService): IPreLoadingView {
        return service
    }

}
