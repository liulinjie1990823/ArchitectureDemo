package com.llj.architecturedemo

import com.llj.lib.base.MvpBaseActivity
import com.llj.lib.base.mvp.IBasePresenter

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
abstract class MyBaseActivity<P : IBasePresenter> : MvpBaseActivity<P>() {

}
