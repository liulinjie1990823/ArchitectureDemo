package com.llj.architecturedemo

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvpBaseFragment
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
abstract class AppMvpBaseFragment<V : ViewBinding, P : IBasePresenter> : MiddleMvpBaseFragment<V, P>() {

  override fun getModuleName(): String {
    return CRouter.MODULE_MAIN
  }
}