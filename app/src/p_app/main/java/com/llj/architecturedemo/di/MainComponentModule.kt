package com.llj.architecturedemo.di

import android.app.Application
import androidx.room.Room
import com.llj.architecturedemo.api.TestApiService
import com.llj.architecturedemo.db.AppDb
import com.llj.architecturedemo.db.dao.MobileDao
import com.llj.lib.base.utils.AppExecutors
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * ArchitectureDemo
 * describe:
 * @author liulj
 * @date 2018/6/6
 */
@Module
internal class MainComponentModule {

  @MainScope
  @Provides
  fun testApiService(retrofit: Retrofit): TestApiService {
    return retrofit.create(TestApiService::class.java)
  }

  @MainScope
  @Provides
  fun dbApp(application: Application): AppDb {
    return Room.databaseBuilder(application, AppDb::class.java, "demo-db").build()
  }

  @MainScope
  @Provides
  fun mobileDao(appDb: AppDb): MobileDao {
    return appDb.mobileDao()
  }

  @MainScope
  @Provides
  fun appExecutors(): AppExecutors {
    return AppExecutors()
  }

}
