package com.llj.lib.base;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.facebook.stetho.Stetho;
import com.llj.lib.base.help.CrashHelper;
import com.llj.lib.base.help.DisplayHelper;
import com.llj.lib.base.help.FilePathHelper;
import com.llj.lib.image.loader.ImageLoader;
import com.llj.lib.utils.AActivityManagerUtils;
import com.llj.lib.utils.AToastUtils;
import com.llj.lib.utils.LogUtil;
import com.llj.lib.utils.helper.Utils;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/25
 */
public abstract class BaseApplication extends Application implements
        HasActivityInjector,
        HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> mSupportFragmentInjector;


    @CallSuper
    @Override
    public void onCreate() {
        super.onCreate();
        if (AActivityManagerUtils.isRunningProcess(this)) {
            Utils.init(this);
            initDisplay();// 初始化屏幕宽高信息
            initSavePath();// 初始化文件存储路径

            initImageLoader();//图片加载器
            initToast();//全局toast初始化

            initCrashHandler();//异常捕捉
            initStetho();//设置okhttp请求调试
            initLeakCanary();//监听内存溢出
            initStrictMode();//设置严格模式
        }
        injectApp();
    }

    private void initDisplay() {
        DisplayHelper.init(this);
    }

    private void initSavePath() {
        FilePathHelper.init(this);
    }

    protected void initImageLoader() {
        ImageLoader.getInstance(this);
    }


    protected void initToast() {
        AToastUtils.init();
    }

    protected void initCrashHandler() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        CrashHelper.getInstance().init(this, LogUtil::LLJe);
    }

    private void initStetho() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        Stetho.Initializer build = Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build();
        Stetho.initialize(build);
    }

    private void initLeakCanary() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initStrictMode() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        //设置线程策略
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
        //设置虚拟机策略
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    abstract protected void injectApp();

    ///////////////////////////////////////////////////////////////////////////
    // Dependencies Injection by dagger.android
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mSupportFragmentInjector;
    }
}
