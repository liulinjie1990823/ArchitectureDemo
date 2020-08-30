package com.llj.login

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvcBaseFragment
import com.llj.component.service.arouter.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2019/3/14
 */
abstract class LoginMvcBaseFragment<V : ViewBinding> : MiddleMvcBaseFragment<V>() {
  override fun getModuleName(): String {
    return CRouter.MODULE_LOGIN
  }
}
