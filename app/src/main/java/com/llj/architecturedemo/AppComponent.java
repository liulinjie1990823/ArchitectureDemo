package com.llj.architecturedemo;

import android.app.Application;

import com.llj.lib.base.BaseApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/18
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AppActivitiesModule.class,//将所有的activity注册进来
        AppModule.class,
        ViewModelModule.class,
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    //调用该方法才会注入BaseApplication中的@Inject标记的对象
    void inject(BaseApplication application);
}
