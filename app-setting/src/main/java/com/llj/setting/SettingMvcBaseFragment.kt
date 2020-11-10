package com.llj.setting

import androidx.viewbinding.ViewBinding
import com.llj.component.service.PlatformMvcBaseFragment
import com.llj.application.router.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class SettingMvcBaseFragment : PlatformMvcBaseFragment<ViewBinding>() {

    override fun getModuleName(): String {
        return CRouter.MODULE_SETTING
    }
}