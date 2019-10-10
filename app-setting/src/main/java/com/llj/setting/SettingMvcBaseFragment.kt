package com.llj.setting

import com.llj.lib.base.MvcBaseFragment

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class SettingMvcBaseFragment : MvcBaseFragment() {

    override fun getModuleName(): String {
        return "app-setting"
    }
}