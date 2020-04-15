package com.llj.setting

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvcBaseFragment

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class SettingMvcBaseFragment : MiddleMvcBaseFragment<ViewBinding>() {

    override fun getModuleName(): String {
        return "app-setting"
    }
}