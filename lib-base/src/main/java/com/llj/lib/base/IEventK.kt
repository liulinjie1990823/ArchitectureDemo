package com.llj.lib.base

import com.llj.lib.base.event.BaseEvent
import org.greenrobot.eventbus.EventBus

/**
 * ArchitectureDemo
 * describe:事件控制
 * @author llj
 * @date 2018/5/24
 */
interface IEventK {

    fun post(`object`: Any) {
        EventBus.getDefault().post(`object`)
    }

    fun registerEventBus(`object`: Any) {
        if (!EventBus.getDefault().isRegistered(`object`)) {
            EventBus.getDefault().register(`object`)
        }
    }

    fun unregisterEventBus(`object`: Any) {
        if (EventBus.getDefault().isRegistered(`object`)) {
            EventBus.getDefault().unregister(`object`)
        }
    }

    fun useEventBus(): Boolean {
        return false
    }

    fun <T> onRecieveEvent(event: BaseEvent<T>)

}
