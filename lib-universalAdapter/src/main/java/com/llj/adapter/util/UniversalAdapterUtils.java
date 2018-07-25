package com.llj.adapter.util;

import android.view.View;

import com.llj.adapter.R;

/**
 * PROJECT:CommonAdapter
 * DESCRIBE:
 * Created by llj on 2017/2/11.
 */

public class UniversalAdapterUtils {

    public static void setViewHolder(View view, Object holder) {
        if (view != null) {
            view.setTag(R.id.com_viewholderTagID, holder);
        }
    }

    @SuppressWarnings("unchecked")
    public static <Holder> Holder getViewHolder(View view) {
        if (view == null) {
            return null;
        }
        return (Holder) view.getTag(R.id.com_viewholderTagID);
    }

    public static int getIndex(View view) {
        return (int) view.getTag(R.id.com_viewholderIndexID);
    }
}
