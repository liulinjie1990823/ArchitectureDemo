package com.llj.lib.base;

import com.llj.lib.base.event.BaseEvent;
import org.greenrobot.eventbus.EventBus;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2019-06-26
 */
public interface IEvent {

  default void post(Object object) {
    EventBus.getDefault().post(object);
  }

  default void registerEventBus(Object object) {
    if (useEventBus() && !EventBus.getDefault().isRegistered(object)) {
      EventBus.getDefault().register(object);
    }
  }

  default void unregisterEventBus(Object object) {
    if (useEventBus() && EventBus.getDefault().isRegistered(object)) {
      EventBus.getDefault().unregister(object);
    }
  }

  default boolean useEventBus() {
    return false;
  }

  //每个页面的Event事件分发
  <T> void onReceiveEvent(BaseEvent<T> event);

  //是否是当前页面，用来控制刷新，关闭等
  <T> boolean inCurrentPage(BaseEvent<T> event);
}
