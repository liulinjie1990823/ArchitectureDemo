package com.llj.architecturedemo

import android.app.Application
import androidx.room.Room
import com.llj.architecturedemo.api.TestApiService
import com.llj.architecturedemo.db.AppDb
import com.llj.architecturedemo.db.dao.MobileDao
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
internal class AppComponentModule {

  @AppScope
  @Provides
  fun provideTestApiService(retrofit: Retrofit): TestApiService {
    return retrofit.create(TestApiService::class.java)
  }

  @AppScope
  @Provides
  fun provideAppDb(app: Application): AppDb {
    return Room.databaseBuilder(app, AppDb::class.java, "app.db").build()
  }

  @AppScope
  @Provides
  fun provideMobileDao(appDb: AppDb): MobileDao {
    return appDb.mobileDao()
  }
}
