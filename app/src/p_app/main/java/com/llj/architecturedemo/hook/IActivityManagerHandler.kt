package com.llj.architecturedemo.hook

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import com.llj.architecturedemo.ui.activity.StubActivity
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/30
 */
class IActivityManagerHandler(private var mBase: Any) : InvocationHandler {
    private val TAG = "IActivityManagerHandler"

    @Throws(Throwable::class)
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any {

        if ("startActivity" == method.name) {
            // 只拦截这个方法
            // 替换参数, 任你所为;甚至替换原始Activity启动别的Activity偷梁换柱
            // API 23:
            // public final Activity startActivityNow(Activity parent, String id,
            // Intent intent, ActivityInfo activityInfo, IBinder token, Bundle state,
            // Activity.NonConfigurationInstances lastNonConfigurationInstances) {

            // 找到参数里面的第一个Intent 对象

            val raw: Intent
            var index = 0

            for (i in args.indices) {
                if (args[i] is Intent) {
                    index = i
                    break
                }
            }
            raw = args[index] as Intent

            val newIntent = Intent()

            // 替身Activity的包名, 也就是我们自己的包名
            val stubPackage = "com.weishu.intercept_activity.app"

            // 这里我们把启动的Activity临时替换为 StubActivity
            val componentName = ComponentName(stubPackage, StubActivity::class.java.simpleName)
            newIntent.component = componentName

            // 把我们原始要启动的TargetActivity先存起来
            newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT, raw)

            // 替换掉Intent, 达到欺骗AMS的目的
            args[index] = newIntent

            Log.d(TAG, "hook success")
            return method.invoke(mBase, args)

        }

        return method.invoke(mBase, args)
    }
}