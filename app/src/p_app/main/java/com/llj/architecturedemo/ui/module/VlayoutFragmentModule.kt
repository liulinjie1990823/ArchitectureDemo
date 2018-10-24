package com.llj.architecturedemo.ui.module

import com.llj.architecturedemo.ui.fragment.VlayoutFragment
import com.llj.architecturedemo.ui.view.IVlayoutView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */

@Module
class VlayoutFragmentModule {

    @Provides
    internal fun providePasswordLoginFragment(fragment: VlayoutFragment): IVlayoutView {
        return fragment
    }
}