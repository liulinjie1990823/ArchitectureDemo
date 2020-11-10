package com.llj.login

import androidx.viewbinding.ViewBinding
import com.llj.component.service.PlatformMvcBaseFragment
import com.llj.application.router.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2019/3/14
 */
abstract class LoginMvcBaseFragment<V : ViewBinding> : PlatformMvcBaseFragment<V>() {
  override fun getModuleName(): String {
    return CRouter.MODULE_LOGIN
  }
}
