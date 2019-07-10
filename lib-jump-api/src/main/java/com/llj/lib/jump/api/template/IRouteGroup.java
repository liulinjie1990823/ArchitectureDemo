package com.llj.lib.jump.api.template;

import com.example.lib.jump.annotation.callback.JumpCallback;

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
