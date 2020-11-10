package com.llj.architecturedemo

import androidx.viewbinding.ViewBinding
import com.llj.component.service.PlatformMvpBaseFragment
import com.llj.application.router.CRouter
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class MainMvpBaseFragment<V : ViewBinding, P : IBasePresenter> : PlatformMvpBaseFragment<V, P>() {

  override fun getModuleName(): String {
    return CRouter.MODULE_MAIN
  }
}