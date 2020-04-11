package debug;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDex;
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
import dagger.android.AndroidInjector;
import org.jetbrains.annotations.NotNull;

/**
 * ArchitectureDemo. describe: author llj date 2019/3/25
 */
public class SettingApp extends MiddleApplication {

  private SettingComponent mSettingComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    mSettingComponent = DaggerSettingComponent.builder()
        .middleComponent(mMiddleComponent)
        .build();

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
        StatusBarCompat.setStatusBarColor(activity.getWindow(),
            ContextCompat.getColor(activity, R.color.white));
        LightStatusBarCompat.setLightStatusBar(activity.getWindow(), true);
      }
    });
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
        if (instance instanceof Activity) {
          MvpBaseActivity activity = (MvpBaseActivity) instance;
          mSettingComponent.activityInjector().inject(activity);
        } else if (instance instanceof Fragment) {
          MvpBaseFragment fragment = (MvpBaseFragment) instance;
          mSettingComponent.supportFragmentInjector().inject(fragment);
        }

      }
    };
  }

}
