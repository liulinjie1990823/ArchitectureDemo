package com.llj.login.ui.module

import com.llj.login.ui.activity.PhoneLoginActivity
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
class PhoneLoginActivityModule {
    @Provides
    internal fun providePhoneLoginActivity(activity: PhoneLoginActivity): PhoneLoginView {
        return activity
    }
}