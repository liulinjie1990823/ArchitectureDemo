package debug;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.billy.cc.core.component.CC;
import com.llj.component.service.MiddleApplication;
import com.llj.lib.base.MvpBaseActivity;
import com.llj.lib.base.MvpBaseFragment;
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter;
import com.llj.lib.statusbar.LightStatusBarCompat;
import com.llj.lib.statusbar.StatusBarCompat;
import com.llj.setting.DaggerSettingComponent;
import com.llj.setting.R;
import com.llj.setting.SettingComponent;

import org.jetbrains.annotations.NotNull;

import dagger.android.AndroidInjector;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
public class SettingApp extends MiddleApplication {

    private SettingComponent mSettingComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                if (activity == null) {
                    return;
                }
                StatusBarCompat.setStatusBarColor(activity.getWindow(), ContextCompat.getColor(activity, R.color.white));
                LightStatusBarCompat.setLightStatusBar(activity.getWindow(), true);
            }
        });
    }

    @Override
    protected void injectApp() {
        mSettingComponent = DaggerSettingComponent.builder()
                .middleComponent(mMiddleComponent)
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @NotNull
    @Override
    public AndroidInjector<Object> androidInjector() {
        return new AndroidInjector<Object>() {
            @Override
            public void inject(Object instance) {
                if(instance instanceof Activity){
                    MvpBaseActivity activity = (MvpBaseActivity) instance;
                    if ("app".equals(activity.getModuleName())) {
                        //主工程
                    } else if ("setting".equals(activity.getModuleName())) {
                        mSettingComponent.activityInjector().inject(activity);
                    }
                }else if(instance instanceof Fragment){
                    MvpBaseFragment fragment = (MvpBaseFragment) instance;
                    if ("app".equals(fragment.getModuleName())) {
                        //主工程
                    } else if ("setting".equals(fragment.getModuleName())) {
                        mSettingComponent.supportFragmentInjector().inject(fragment);
                    }
                }

            }
        };
    }

}
