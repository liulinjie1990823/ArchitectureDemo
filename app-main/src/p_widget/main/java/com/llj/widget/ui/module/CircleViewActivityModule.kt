package com.llj.widget.ui.module

import com.llj.widget.ui.activity.CircleViewActivity
import com.llj.widget.ui.view.CircleViewView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/16
 */
@Module
class CircleViewActivityModule {
    @Provides
    internal fun provideView(activity: CircleViewActivity): CircleViewView {
        return activity
    }

    //    @Provides
    //    MainContractViewModel provideModel(MainActivity activity) {
    //        return ViewModelProviders.of(activity).get(MainContractViewModel.class);
    //    }

}
