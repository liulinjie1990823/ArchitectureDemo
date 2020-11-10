package com.llj.loading

import androidx.viewbinding.ViewBinding
import com.llj.component.service.PlatformMvcBaseActivity
import com.llj.application.router.CRouter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
abstract class LoadingMvcBaseActivity<V : ViewBinding> : PlatformMvcBaseActivity<V>(){
  override fun getModuleName(): String {
    return CRouter.MODULE_LOADING
  }
}
