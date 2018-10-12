package debug

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.v4.content.ContextCompat
import com.billy.cc.core.component.CC
import com.llj.component.service.ComponentApplication
import com.llj.component.service.statusbar.LightStatusBarCompat
import com.llj.component.service.statusbar.StatusBarCompat
import com.llj.login.R

/**
 * @author billy.qi
 * @since 17/11/20 20:02
 */
class MyApp : ComponentApplication() {
    override fun onCreate() {
        super.onCreate()
        CC.enableVerboseLog(true)
        CC.enableDebug(true)
        CC.enableRemoteCC(true)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {

            }

            override fun onActivityStarted(activity: Activity?) {

            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if (activity == null) {
                    return
                }
                StatusBarCompat.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.white))
                LightStatusBarCompat.setLightStatusBar(activity.window, true)
            }

        })

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    override fun injectApp() {

    }
}
