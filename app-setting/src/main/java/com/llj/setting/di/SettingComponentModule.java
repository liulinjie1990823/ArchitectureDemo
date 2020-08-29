package com.llj.setting.di;

import com.llj.setting.api.SettingApiService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * ArchitectureDemo. describe: author llj date 2019/3/25
 */
@Module
class SettingComponentModule {

  @SettingScope
  @Provides
  SettingApiService provideSettingApiService(Retrofit retrofit) {
    return retrofit.create(SettingApiService.class);
  }
}
