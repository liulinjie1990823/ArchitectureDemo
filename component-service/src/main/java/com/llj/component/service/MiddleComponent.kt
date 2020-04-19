package com.llj.component.service

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/5/18
 */
@Singleton
@Component(modules = [
  MiddleComponentModule::class
])
interface MiddleComponent {

  @Component.Builder
  interface Builder {

    //提供给AppModule中方法中的Application入参用
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): com.llj.component.service.MiddleComponent
  }

  //将ComponentModule中的实例提供出来给依赖的Component使用
  fun retrofit(): Retrofit
  fun okHttpClient(): OkHttpClient
  fun application(): Application

}
