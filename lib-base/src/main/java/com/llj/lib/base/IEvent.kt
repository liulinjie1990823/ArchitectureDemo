package com.llj.lib.base

import org.greenrobot.eventbus.EventBus

/**
 * ArchitectureDemo
 * describe:事件控制
 * @author llj
 * @date 2018/5/24
 */
interface IEvent {

    fun post(`object`: Any) {
        EventBus.getDefault().post(`object`)
    }

    fun register(`object`: Any) {
        if (useEventBus() && !EventBus.getDefault().isRegistered(`object`)) {
            EventBus.getDefault().register(`object`)
        }
    }

    fun unregister(`object`: Any) {
        if (useEventBus() && EventBus.getDefault().isRegistered(`object`)) {
            EventBus.getDefault().unregister(`object`)
        }
    }

    fun useEventBus(): Boolean {
        return false
    }

}
