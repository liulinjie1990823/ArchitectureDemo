package com.llj.lib.base.api.template;

import com.example.lib.base.annotation.callback.JumpCallback;

import java.util.Map;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-16
 */
public interface IRouteGroup {

    void loadInto(Map<String, JumpCallback> map);
}
