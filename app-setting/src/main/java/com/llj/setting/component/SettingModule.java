package com.llj.setting.component;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.IComponent;
import com.llj.component.service.ComponentApplication;
import com.llj.setting.DaggerSettingComponent;
import com.llj.setting.SettingComponent;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/25
 */
public class SettingModule implements IComponent {
    private SettingComponent mSettingComponent;

    @Override
    public String getName() {
        return "app-setting";
    }

    @Override
    public boolean onCall(CC cc) {
        if (cc == null) {
            return false;
        }
        if ("init".equals(cc.getActionName())) {
            ComponentApplication componentApplication = (ComponentApplication) cc.getContext();
            mSettingComponent = DaggerSettingComponent.builder()
                    .component(componentApplication.mComponent)
                    .build();
        } else if ("injectActivity".equals(cc.getActionName())) {
            Activity activity = (Activity) cc.getContext();
            mSettingComponent.activityInjector().inject(activity);
        } else if ("injectFragment".equals(cc.getActionName())) {
            FragmentActivity activity = (FragmentActivity) cc.getContext();
            String tag = cc.getParamItem("fragment");

            if (tag != null) {
                Fragment findFragmentByTag = activity.getSupportFragmentManager().findFragmentByTag(tag);
                if (findFragmentByTag != null) {
                    mSettingComponent.supportFragmentInjector().inject(findFragmentByTag);
                }
            }
        }
        return false;
    }
}
