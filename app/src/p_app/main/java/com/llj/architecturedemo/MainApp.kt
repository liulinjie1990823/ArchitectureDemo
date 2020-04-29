package com.llj.architecturedemo

import com.llj.application.AppApplication

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2020/4/29
 */
class MainApp : AppApplication() {

    private lateinit var mAppComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        mAppComponent = DaggerAppComponent.builder()
                .middleComponent(mMiddleComponent)
                .build()
    }

}