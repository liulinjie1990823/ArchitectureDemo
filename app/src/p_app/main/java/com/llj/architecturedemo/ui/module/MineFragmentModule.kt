package com.llj.architecturedemo.ui.module

import com.llj.architecturedemo.ui.fragment.MineFragment
import com.llj.architecturedemo.ui.view.IMineView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */

@Module
class MineFragmentModule {

    @Provides
    internal fun providePasswordLoginFragment(fragment: MineFragment): IMineView {
        return fragment
    }
}