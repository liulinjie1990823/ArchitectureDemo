package com.llj.architecturedemo;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.llj.architecturedemo.api.TestApiService;
import com.llj.architecturedemo.db.AppDb;
import com.llj.architecturedemo.db.dao.MobileDao;
import com.llj.lib.net.RetrofitUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Module
class AppModule {

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        Retrofit.Builder builder = RetrofitUtils.createRxJava2Retrofit(HttpUrl.BASE_URL);

        return builder.build();
    }

    @Singleton
    @Provides
    TestApiService provideGithubService(@NonNull Retrofit retrofit) {
        return retrofit.create(TestApiService.class);
    }

    @Singleton
    @Provides
    AppDb provideAppDb(Application app) {
        return Room.databaseBuilder(app, AppDb.class, "app.db").build();
    }

    @Singleton
    @Provides
    MobileDao provideMobileDao(AppDb appDb) {
        return appDb.mobileDao();
    }
}
