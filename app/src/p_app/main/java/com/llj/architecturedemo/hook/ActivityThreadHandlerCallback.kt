package com.llj.architecturedemo.hook

import android.content.Intent
import android.os.Handler
import android.os.Message


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/30
 */
class ActivityThreadHandlerCallback(private var mBase: Handler) : Handler.Callback {

    override fun handleMessage(msg: Message): Boolean {
        when (msg?.what) {
            // ActivityThread里面 "LAUNCH_ACTIVITY" 这个字段的值是100
            // 本来使用反射的方式获取最好, 这里为了简便直接使用硬编码
            100 -> handleLaunchActivity(msg)
        }

        mBase.handleMessage(msg)
        return true
    }

    private fun handleLaunchActivity(msg: Message) {
        // 这里简单起见,直接取出TargetActivity;

        val obj = msg.obj
        // 根据源码:
        // 这个对象是 ActivityClientRecord 类型
        // 我们修改它的intent字段为我们原来保存的即可.
        // switch (msg.what) {
        //      case LAUNCH_ACTIVITY: {
        //          Trace.traceBegin(Trace.TRACE_TAG_ACTIVITY_MANAGER, "activityStart");
        //          final ActivityClientRecord r = (ActivityClientRecord) msg.obj;

        //          r.packageInfo = getPackageInfoNoCheck(
        //                  r.activityInfo.applicationInfo, r.compatInfo);
        //         handleLaunchActivity(r, null);


        try {
            // 把替身恢复成真身
            val intent = obj.javaClass.getDeclaredField("intent")
            intent.isAccessible = true
            val raw = intent.get(obj) as Intent

            val target = raw.getParcelableExtra<Intent>(AMSHookHelper.EXTRA_TARGET_INTENT)
            raw.component = target!!.component

        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}
