package com.llj.architecturedemo.module

import com.llj.architecturedemo.ui.activity.MainActivity
import com.llj.architecturedemo.view.MainContractView

import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Module
class MainActivityModule {
    @Provides
    internal fun provideView(activity: MainActivity): MainContractView {
        return activity
    }

    //    @Provides
    //    MainContractViewModel provideModel(MainActivity activity) {
    //        return ViewModelProviders.of(activity).get(MainContractViewModel.class);
    //    }

}
