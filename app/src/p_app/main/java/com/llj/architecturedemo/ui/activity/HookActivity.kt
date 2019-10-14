package com.llj.architecturedemo.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.hook.HookHelper
import com.llj.component.service.arouter.CRouter


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/29
 */
@Route(path = CRouter.APP_HOOK_ACTIVITY)
class HookActivity : AppMvcBaseActivity() {
    @BindView(R.id.tv_click) lateinit var mTvClick: TextView


    override fun layoutId(): Int {
        return R.layout.activity_hook
    }

    override fun initViews(savedInstanceState: Bundle?) {
        try {
            HookHelper.attachContext()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mTvClick.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("http://www.baidu.com")

            // 注意这里使用的ApplicationContext 启动的Activity
            // 因为Activity对象的startActivity使用的并不是ContextImpl的mInstrumentation
            // 而是自己的mInstrumentation, 如果你需要这样, 可以自己Hook
            // 比较简单, 直接替换这个Activity的此字段即可.
            startActivity(intent)
        }
    }

    override fun initData() {
    }
}