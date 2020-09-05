package com.llj.architecturedemo.ui.module

import com.llj.architecturedemo.ui.fragment.VLayoutFragment2
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
class VLayoutFragment2Module {

    @Provides
    internal fun provideVLayoutFragment2(fragment: VLayoutFragment2): IVLayoutView {
        return fragment
    }
}