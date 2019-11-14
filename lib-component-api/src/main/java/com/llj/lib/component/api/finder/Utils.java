package com.llj.lib.component.api.finder;

import androidx.annotation.IdRes;
import android.view.View;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/13
 */
public class Utils {
    public static View findRequiredView(View source, @IdRes int id, String who) {
        View view = source.findViewById(id);
        if (view != null) {
            return view;
        }
        String name = getResourceEntryName(source, id);
        throw new IllegalStateException("Required view '"
                + name
                + "' with ID "
                + id
                + " for "
                + who
                + " was not found. If this view is optional add '@Nullable' (fields) or '@Optional'"
                + " (methods) annotation.");
    }

    public static <T extends View> T findViewById(View source, @IdRes int id) {
        return source.findViewById(id);
    }

    public static <T extends View> T findRequiredViewAsType(View source, @IdRes int id, String who, Class<T> cls) {
        View view = findRequiredView(source, id, who);
        return castView(view, id, who, cls);
    }

    public static <T> T castView(View view, @IdRes int id, String who, Class<T> cls) {
        try {
            return cls.cast(view);
        } catch (ClassCastException e) {
            String name = getResourceEntryName(view, id);
            throw new IllegalStateException("View '"
                    + name
                    + "' with ID "
                    + id
                    + " for "
                    + who
                    + " was of the wrong type. See cause for more info.", e);
        }
    }

    private static String getResourceEntryName(View view, @IdRes int id) {
        if (view.isInEditMode()) {
            return "<unavailable while editing>";
        }
        return view.getContext().getResources().getResourceEntryName(id);
    }
}
