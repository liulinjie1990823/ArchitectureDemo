package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.R
import com.llj.component.service.ComponentMvcBaseActivity
import com.llj.component.service.arouter.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/9
 */
@Route(path = CRouter.APP_JAVA_TYPE_ACTIVITY)
class KotlinTypeActivity : ComponentMvcBaseActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_java_type
    }

    override fun initViews(savedInstanceState: Bundle?) {

    }

    override fun initData() {


    }

    open class C constructor(var name: String? = null)

    open class Java(name: String?) : C(name)

    class Kotlin(name: String?) : Java(name)
}
