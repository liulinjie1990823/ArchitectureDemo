package com.llj.architecturedemo

import com.llj.component.service.ComponentMvpBaseActivity
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
abstract class AppMvpBaseActivity<P : IBasePresenter> : ComponentMvpBaseActivity<P>() {
    override fun moduleName(): String {
        return "app"
    }
}
