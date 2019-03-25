package com.llj.setting;

import com.llj.setting.api.SettingApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
@Module
 class SettingComponentModule {
    @Provides
    @Singleton
    SettingApiService provideSettingApiService(Retrofit retrofit) {
        return retrofit.create(SettingApiService.class);
    }
}
