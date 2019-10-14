package com.llj.setting

import com.llj.component.service.MiddleMvcBaseFragment

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class SettingMvcBaseFragment : MiddleMvcBaseFragment() {

    override fun getModuleName(): String {
        return "app-setting"
    }
}