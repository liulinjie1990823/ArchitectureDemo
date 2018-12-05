package com.llj.lib.component.api.finder;


import com.llj.lib.component.api.provider.Provider;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
public interface Finder<T> {
    void inject(T host, Object source, Provider provider);
}
