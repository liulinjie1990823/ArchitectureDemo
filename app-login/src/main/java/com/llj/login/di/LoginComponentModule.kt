package com.llj.login.di

import com.llj.login.api.LoginApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Module
internal class LoginComponentModule {

    @LoginScope
    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

}
