package com.llj.architecturedemo.hook

import android.os.Build
import android.os.Handler
import java.lang.reflect.Proxy
import java.lang.reflect.AccessibleObject.setAccessible
import java.lang.reflect.AccessibleObject.setAccessible


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/29
 */
class AMSHookHelper {

    companion object {
        const val EXTRA_TARGET_INTENT = "extra_target_intent"

        fun hookActivityManagerNative() {

            val gDefaultField = if (Build.VERSION.SDK_INT >= 26) {
                val activityManager = Class.forName("android.app.ActivityManager")
                activityManager.getDeclaredField("IActivityManagerSingleton")
            } else {
                val activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative")
                activityManagerNativeClass.getDeclaredField("gDefault");
            }
            gDefaultField.isAccessible = true

            val gDefault = gDefaultField.get(null)


            // gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
            val singleton = Class.forName("android.util.Singleton")
            val mInstanceField = singleton.getDeclaredField("mInstance")
            mInstanceField.isAccessible = true

            val rawIActivityManager = mInstanceField.get(gDefault)
            val iActivityManagerInterface = Class.forName("android.app.IActivityManager")
            val proxy = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader,
                    arrayOf(iActivityManagerInterface), IActivityManagerHandler(rawIActivityManager))
            mInstanceField.set(gDefault, proxy);
        }

        fun hookActivityThreadHandler() {
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread")
            currentActivityThreadField.isAccessible = true
            val currentActivityThread = currentActivityThreadField.get(null)


            val mHField = activityThreadClass.getDeclaredField("mH")
            mHField.isAccessible = true
            val mH = mHField.get(currentActivityThread) as Handler


            val mCallBackField = Handler::class.java.getDeclaredField("mCallback")
            mCallBackField.isAccessible = true

            mCallBackField.set(mH, ActivityThreadHandlerCallback(mH))


        }
    }
}