package com.llj.lib.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.llj.lib.base.listeners.OnMyClickListener;
import com.llj.lib.utils.ACollectionUtils;
import com.llj.lib.utils.ATextUtils;
import com.llj.lib.utils.AToastUtils;

import java.util.Collection;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
public interface IUiHandler {

    ///////////////////////////////////////////////////////////////////////////
    // ui相关操作
    ///////////////////////////////////////////////////////////////////////////


    default void setOnClickListener(View view, @Nullable View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            view.setOnClickListener(null);
        } else {
            view.setOnClickListener(new OnMyClickListener() {
                @Override
                public void onCanClick(View v) {
                    onClickListener.onClick(v);
                }
            });
        }
    }

    default CharSequence getTextStr(TextView textView) {
        return ATextUtils.getText(textView);
    }


    default void setText(TextView textView, CharSequence destination) {
        ATextUtils.setText(textView, destination);
    }

    default void setText(TextView textView, CharSequence destination, CharSequence defaultStr) {
        ATextUtils.setText(textView, destination, defaultStr);
    }

    default void showToast(String content) {
        AToastUtils.show(content);
    }

    default void showToast(@StringRes int resId) {
        AToastUtils.show(resId);
    }

    default void showLongToast(String content) {
        AToastUtils.showLong(content);
    }

    default void showLongToast(@StringRes int resId) {
        AToastUtils.showLong(resId);
    }

    default boolean isEmpty(CharSequence text) {
        return android.text.TextUtils.isEmpty(text);
    }

    default boolean isEmpty(TextView textView) {
        return android.text.TextUtils.isEmpty(getTextStr(textView));
    }

    default boolean isEmpty(Collection list) {
        return ACollectionUtils.isEmpty(list);
    }

    default void setTextColor(TextView textView, @ColorRes int id) {
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), id));
    }

    default int getCompatColor(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }


    default Drawable getCompatDrawable(Context context, @DrawableRes int id) {
        return ContextCompat.getDrawable(context, id);
    }


}
