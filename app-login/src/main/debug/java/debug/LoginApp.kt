package debug

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDex
import com.llj.application.AppApplication
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.login.di.DaggerLoginComponent
import com.llj.login.di.LoginComponent
import dagger.android.AndroidInjector
import org.altbeacon.beacon.Region
import org.altbeacon.beacon.logging.LogManager
import org.altbeacon.beacon.powersave.BackgroundPowerSaver
import org.altbeacon.beacon.startup.BootstrapNotifier
import org.altbeacon.beacon.startup.RegionBootstrap


/**
 * @author billy.qi
 * @since 17/11/20 20:02
 */
class LoginApp : AppApplication(), BootstrapNotifier {
  private lateinit var backgroundPowerSaver: BackgroundPowerSaver
  override fun didDetermineStateForRegion(p0: Int, p1: Region?) {
  }

  override fun didEnterRegion(p0: Region?) {
    Log.i("MiddleApplication", "Got a didEnterRegion call");
    // This call to disable will make it so the activity below only gets launched the first time a beacon is seen (until the next time the app is launched)
    // if you want the Activity to launch every single time beacons come into view, remove this call.
    //        regionBootstrap.disable()
  }

  override fun didExitRegion(p0: Region?) {
  }

  private lateinit var mLoginComponent: LoginComponent
  private lateinit var regionBootstrap: RegionBootstrap

  override fun onCreate() {
    super.onCreate()
    mLoginComponent = DaggerLoginComponent.builder()
        .appComponent(mAppComponent)
        .build()

    registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            LightStatusBarCompat.setLightStatusBar(activity.window, false)
        }

    })

    backgroundPowerSaver = BackgroundPowerSaver(this)

    LogManager.setVerboseLoggingEnabled(true)

//        ScanState.MIN_SCAN_JOB_INTERVAL_MILLIS = 1000
    val region = Region(packageName, null, null, null)
//        regionBootstrap = RegionBootstrap(this, region)
  }

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }


  //单独启动的时候调用
  override fun androidInjector(): AndroidInjector<Any> {
    return AndroidInjector { data ->
      if (data is MvpBaseActivity<*, *>) {
        mLoginComponent.activityInjector().inject(data)
      } else if (data is MvpBaseFragment<*, *>) {
        mLoginComponent.supportFragmentInjector().inject(data)
      }
    }
  }
}
