package com.llj.lib.base;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.llj.lib.base.help.DisplayHelper;
import com.llj.lib.base.help.FilePathHelper;
import com.llj.lib.image.loader.ImageLoader;
import com.llj.lib.utils.AActivityManagerUtils;
import com.llj.lib.utils.AToastUtils;
import com.llj.lib.utils.helper.Utils;
import com.squareup.leakcanary.LeakCanary;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/25
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (AActivityManagerUtils.isRunningProcess(this)) {
            Utils.init(this);
            initDisplay();// 初始化屏幕宽高信息
            initSavePath();// 初始化文件存储路径
            initImageLoader();
            initToast();//全局toast初始化
            initCrashHandler();//异常捕捉
            initStetho();//设置okhttp请求调试
            initLeakCanary();//监听内存溢出
            //initStrictMode();//设置严格模式
        }
    }

    private void initDisplay() {
        DisplayHelper.init(this);
    }

    private void initSavePath() {
        FilePathHelper.init(this);
    }

    private void initImageLoader() {
        ImageLoader.getInstance(this);
    }


    private void initToast() {
        AToastUtils.init();
    }

    private void initCrashHandler() {

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


    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }
}
