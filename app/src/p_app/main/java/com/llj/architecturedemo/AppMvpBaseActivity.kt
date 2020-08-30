package com.llj.architecturedemo

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvpBaseActivity
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.IModuleName
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
abstract class AppMvpBaseActivity<P : IBasePresenter> :
    MiddleMvpBaseActivity<ViewBinding, P>(),
    IModuleName {
  override fun getModuleName(): String {
    return CRouter.MODULE_MAIN
  }
}
