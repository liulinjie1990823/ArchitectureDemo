package debug

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDex
import com.billy.cc.core.component.CC
import com.llj.application.AppApplication
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.login.DaggerLoginComponent
import com.llj.login.LoginComponent
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
                .middleComponent(mMiddleComponent)
                .build()

        CC.enableVerboseLog(true)
        CC.enableDebug(true)
        CC.enableRemoteCC(true)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                StatusBarCompat.setStatusBarColor(activity.window, ContextCompat.getColor(activity, com.llj.login.R.color.white))
                LightStatusBarCompat.setLightStatusBar(activity.window, true)
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



    override fun androidInjector(): AndroidInjector<Any> {
        return AndroidInjector { data ->

          if (data is MvpBaseActivity<*, *>) {
                val mvpBaseActivity = data
                mLoginComponent.activityInjector().inject(mvpBaseActivity)
            } else {
            val mvpBaseFragment = data as MvpBaseFragment<*, *>
                mLoginComponent.supportFragmentInjector().inject(mvpBaseFragment)
            }
        }
    }
}
