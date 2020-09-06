package com.llj.architecturedemo.ui.module

import com.llj.architecturedemo.ui.fragment.ScrollableLayoutFragment
import com.llj.architecturedemo.ui.view.IScrollableLayoutView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */

@Module
class ScrollableLayoutFragmentModule {

    @Provides
    internal fun providePasswordLoginFragment(fragment: ScrollableLayoutFragment): IScrollableLayoutView {
        return fragment
    }
}