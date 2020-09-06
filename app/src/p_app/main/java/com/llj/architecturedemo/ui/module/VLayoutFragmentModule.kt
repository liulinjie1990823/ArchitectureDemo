package com.llj.architecturedemo.ui.module

import com.llj.architecturedemo.ui.fragment.VLayoutFragment
import com.llj.architecturedemo.ui.view.IVLayoutView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */

@Module
class VLayoutFragmentModule {

    @Provides
    internal fun provideVLayoutFragment(fragment: VLayoutFragment): IVLayoutView {
        return fragment
    }
}