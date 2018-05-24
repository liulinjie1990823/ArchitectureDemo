package com.llj.lib.base;

import org.greenrobot.eventbus.EventBus;

/**
 * ArchitectureDemo
 * describe:事件总线相关方法
 * author liulj
 * date 2018/5/24
 */
public interface IEvent {


    default void post(Object object) {
        EventBus.getDefault().post(object);
    }

    default void register(Object object) {
        if (useEventBus() && !EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    default void unregister(Object object) {
        if (useEventBus() && EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    default boolean useEventBus() {
        return true;
    }

}
