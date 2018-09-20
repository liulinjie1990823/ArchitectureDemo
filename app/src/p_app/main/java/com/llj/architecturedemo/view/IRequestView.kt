package com.llj.architecturedemo.view

import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.lib.base.mvp.IView

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
interface IRequestView : IView {

    fun toast(mobile: MobileEntity?)
}