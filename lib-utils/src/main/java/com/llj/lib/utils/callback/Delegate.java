package com.llj.lib.utils.callback;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/26
 */
public interface Delegate<T> {
    void execute(T param);
}
