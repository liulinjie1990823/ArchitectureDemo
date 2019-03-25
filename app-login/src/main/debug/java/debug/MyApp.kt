package debug

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.billy.cc.core.component.CC
import com.llj.component.service.ComponentApplication
import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.listeners.ActivityLifecycleCallbacksAdapter
import com.llj.lib.statusbar.LightStatusBarCompat
import com.llj.lib.statusbar.StatusBarCompat
import com.llj.login.DaggerLoginComponent
import com.llj.login.LoginComponent
import com.llj.login.R
import dagger.android.AndroidInjector

/**
 * @author billy.qi
 * @since 17/11/20 20:02
 */
class MyApp : ComponentApplication() {
    private lateinit var mLoginComponent: LoginComponent

    override fun onCreate() {
        super.onCreate()
        CC.enableVerboseLog(true)
        CC.enableDebug(true)
        CC.enableRemoteCC(true)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if (activity == null) {
                    return
                }
                StatusBarCompat.setStatusBarColor(activity.window, ContextCompat.getColor(activity, R.color.white))
                LightStatusBarCompat.setLightStatusBar(activity.window, true)
            }

        })
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun injectApp() {
        mLoginComponent = DaggerLoginComponent.builder()
                .component(mComponent)
                .build()
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return AndroidInjector { activity ->
            val mvpBaseActivity = activity as MvpBaseActivity<*>

            if ("app" == mvpBaseActivity.moduleName()) {
                //主工程
            } else if ("login" == mvpBaseActivity.moduleName()) {
                mLoginComponent.activityInjector().inject(mvpBaseActivity)
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return AndroidInjector { fragment ->
            val mvpBaseFragment = fragment as MvpBaseFragment<*>

            if ("app" == mvpBaseFragment.moduleName()) {
                //主工程
            } else if ("login" == mvpBaseFragment.moduleName()) {
                mLoginComponent.supportFragmentInjector().inject(mvpBaseFragment)
            }
        }
    }
}
