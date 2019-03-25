package com.llj.login.ui.module

import com.llj.login.ui.fragment.PasswordLoginFragment
import com.llj.login.ui.view.PhoneLoginView
import dagger.Module
import dagger.Provides

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/19
 */
@Module
class PasswordLoginFragmentModule {

    @Provides
    internal fun providePasswordLoginFragment(fragment: PasswordLoginFragment): PhoneLoginView {
        return fragment
    }
}