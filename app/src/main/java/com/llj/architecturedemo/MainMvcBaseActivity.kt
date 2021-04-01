package com.llj.architecturedemo

import androidx.viewbinding.ViewBinding
import com.llj.application.router.CRouter
import com.llj.component.service.PlatformMvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
abstract class MainMvcBaseActivity<V : ViewBinding> : PlatformMvcBaseActivity<V>(){
  override fun getModuleName(): String {
    return CRouter.MODULE_MAIN
  }
}
