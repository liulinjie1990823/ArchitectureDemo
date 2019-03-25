package com.llj.setting.ui.module;

import com.llj.setting.ui.activity.QrCodeActivity;
import com.llj.setting.ui.view.SettingView;

import dagger.Module;
import dagger.Provides;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
@Module
public class SettingActivityModule {
    /**
     *  提供给Presenter的参数用的view
     * @param activity activity中inject的对象
     * @return
     */
    @Provides
     SettingView settingActivity(QrCodeActivity activity) {
        return activity;
    }
}
