package com.llj.lib.utils;

import java.util.Collection;

/**
 * Created by liulj on 16/7/5.
 */

public class ACollectionUtils {
    /**
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> int getSize(Collection<T> list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /**
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T[] list) {
        return list == null || list.length == 0;
    }

}
