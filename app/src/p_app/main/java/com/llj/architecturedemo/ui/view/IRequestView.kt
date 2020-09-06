package com.llj.architecturedemo.ui.view

import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.lib.base.mvp.IBaseActivityView

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/20
 */
interface IRequestView : IBaseActivityView {

    fun toast(mobile: MobileEntity?)
}