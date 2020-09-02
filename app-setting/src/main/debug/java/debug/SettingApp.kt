package debug

import com.llj.application.AppApplication
import com.llj.setting.di.DaggerSettingComponent
import com.llj.setting.di.SettingComponent

/**
 * ArchitectureDemo. describe: author llj date 2019/3/25
 */
class SettingApp : AppApplication() {
    private lateinit var mSettingComponent: SettingComponent

    override fun onCreate() {
        super.onCreate()
        mSettingComponent = DaggerSettingComponent.builder()
                .appComponent(mAppComponent)
                .build()
    }
}