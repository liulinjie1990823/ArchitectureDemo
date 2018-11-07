package com.llj.architecturedemo.ui.view

import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.lib.base.mvp.IBaseActivityView

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/4
 */
interface SecondView : IBaseActivityView {

    fun toast(mobile: MobileEntity?)
}
