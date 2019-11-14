package com.llj.adapter.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import android.view.View;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/3/15.
 */

public interface IHolder {
    Context getContext();

    void removeFromCache(@IdRes int id);

    void removeAll();

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////
    <T extends View> T getView(@IdRes int id);

    CharSequence getText(@IdRes int id);

    IHolder setText(@IdRes int id, CharSequence text);

    IHolder setText(@IdRes int id, @StringRes int stringId);

    IHolder setTextTrim(@IdRes int id, CharSequence text);

    IHolder setTextTrim(@IdRes int id, @StringRes int stringId);

    IHolder setTextWithVisibility(@IdRes int id, CharSequence text);

    IHolder setTextWithVisibility(@IdRes int id, @StringRes int stringId);

    IHolder setTextColor(@IdRes int id, @ColorInt int textColor);

    IHolder setTextColorRes(@IdRes int id, @ColorRes int textColor);

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////

    IHolder setSelected(@IdRes int id, boolean selected);

    IHolder setEnabled(@IdRes int id, boolean enabled);

    IHolder setVisibility(@IdRes int id, int visibility);

    IHolder setVisible(@IdRes int id);

    IHolder setInvisible(@IdRes int id);

    IHolder setGone(@IdRes int id);

    ///////////////////////////////////////////////////////////////////////////
    // 
    ///////////////////////////////////////////////////////////////////////////
    IHolder setImageResource(@IdRes int id, @DrawableRes int res);

    IHolder setImageBitmap(@IdRes int id, Bitmap bitmap);

    IHolder setImageDrawable(@IdRes int id, Drawable drawable);

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    IHolder setBackgroundResource(@IdRes int id, @DrawableRes int res);

    IHolder setBackgroundBitmap(@IdRes int id, Bitmap bitmap);

    IHolder setBackgroundDrawable(@IdRes int id, Drawable drawable);

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    IHolder setOnItemClickListener(View.OnClickListener listener);

    IHolder setOnClickListener(@IdRes int id, View.OnClickListener onClickListener);

    IHolder setOnLongClickListener(@IdRes int id, View.OnLongClickListener onLongClickListener);

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    IHolder setTag(@IdRes int viewId, Object tag);

    IHolder setTag(@IdRes int viewId, int key, Object tag);

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    IHolder setTypeface(Typeface typeface, int... viewIds);

    IHolder setProgress(@IdRes int viewId, int progress);

    IHolder setProgress(@IdRes int viewId, int progress, int max);

    IHolder setMax(@IdRes int viewId, int max);

    IHolder setRating(@IdRes int viewId, float rating);

    IHolder setRating(@IdRes int viewId, float rating, int max);

    IHolder setChecked(@IdRes int viewId, boolean checked);

}
