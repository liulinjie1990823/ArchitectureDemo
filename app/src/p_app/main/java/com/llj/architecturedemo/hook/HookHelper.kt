package com.llj.architecturedemo.hook

import android.app.Instrumentation


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/30
 */
class HookHelper {

    companion object {

        @Throws(Exception::class)
        fun attachContext() {
            // 先获取到当前的ActivityThread对象
            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread")
            currentActivityThreadMethod.isAccessible = true
            //currentActivityThread是一个static函数所以可以直接invoke，不需要带实例参数
            val currentActivityThread = currentActivityThreadMethod.invoke(null)

            // 拿到原始的 mInstrumentation字段
            val mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation")
            mInstrumentationField.isAccessible = true
            val mInstrumentation = mInstrumentationField.get(currentActivityThread) as Instrumentation

            // 创建代理对象
            val evilInstrumentation = EvilInstrumentation(mInstrumentation)

            // 偷梁换柱
            mInstrumentationField.set(currentActivityThread, evilInstrumentation)
        }
    }
}