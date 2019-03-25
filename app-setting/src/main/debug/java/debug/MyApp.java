package debug;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.billy.cc.core.component.CC;
import com.llj.component.service.ComponentApplication;
import com.llj.lib.base.MvpBaseActivity;
import com.llj.lib.base.MvpBaseFragment;
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter;
import com.llj.lib.statusbar.LightStatusBarCompat;
import com.llj.lib.statusbar.StatusBarCompat;
import com.llj.setting.DaggerSettingComponent;
import com.llj.setting.R;
import com.llj.setting.SettingComponent;

import org.jetbrains.annotations.Nullable;

import dagger.android.AndroidInjector;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
public class MyApp extends ComponentApplication {

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
                .component(mComponent)
                .build();
    }

    @Nullable
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return new AndroidInjector<Activity>() {
            @Override
            public void inject(Activity instance) {
                MvpBaseActivity activity = (MvpBaseActivity) instance;
                if ("app".equals(activity.moduleName())) {
                    //主工程
                } else if ("setting".equals(activity.moduleName())) {
                    mSettingComponent.activityInjector().inject(activity);
                }
            }
        };
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Nullable
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return new AndroidInjector<Fragment>() {
            @Override
            public void inject(Fragment instance) {
                MvpBaseFragment fragment = (MvpBaseFragment) instance;
                if ("app".equals(fragment.moduleName())) {
                    //主工程
                } else if ("setting".equals(fragment.moduleName())) {
                    mSettingComponent.supportFragmentInjector().inject(fragment);
                }
            }
        };
    }
}
