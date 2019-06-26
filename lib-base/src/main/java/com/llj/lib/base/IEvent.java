package com.llj.lib.base;

import com.llj.lib.base.event.BaseEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-26
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
        return false;
    }

    <T>  void onReceiveEvent(BaseEvent<T> event);
}
