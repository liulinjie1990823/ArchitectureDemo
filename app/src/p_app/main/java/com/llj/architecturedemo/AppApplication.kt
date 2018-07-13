package com.llj.architecturedemo

import com.llj.component.service.ComponentApplication

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/18
 */
class AppApplication : ComponentApplication() {
    override fun injectApp() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }
}
