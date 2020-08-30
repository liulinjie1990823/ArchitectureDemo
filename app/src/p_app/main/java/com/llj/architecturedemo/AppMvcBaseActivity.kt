package com.llj.architecturedemo

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvcBaseActivity
import com.llj.component.service.arouter.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
abstract class AppMvcBaseActivity<V : ViewBinding> : MiddleMvcBaseActivity<V>(){
  override fun getModuleName(): String {
    return CRouter.MODULE_MAIN
  }
}
