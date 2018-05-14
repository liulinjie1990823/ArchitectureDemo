package com.llj.lib.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * @author liulj
 */
public class ViewHolderUtil {
    /**
     * 简化viewhold的过程
     *
     * @param view
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        // 试图从view中获取SparseArray
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        // 如果没有则创建一个
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        // 从SparseArray获取view
        View childView = viewHolder.get(id);
        // 如果view不存在，则实例化并加入到SparseArray中
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
