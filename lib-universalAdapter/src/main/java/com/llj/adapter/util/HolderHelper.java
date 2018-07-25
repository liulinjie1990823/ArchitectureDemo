package com.llj.adapter.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/3/15.
 */

public class HolderHelper implements IHolder {
    protected SparseArray<View> views = new SparseArray<>();
    protected View itemView;

    public HolderHelper(View itemView) {
        this.itemView = itemView;
    }

    @Override
    public Context getContext() {
        return itemView.getContext();
    }

    @Override
    public void removeFromCache(@IdRes int id) {
        views.remove(id);
    }

    @Override
    public void removeAll() {
        views.clear();
    }

    @Override
    public <T extends View> T getView(@IdRes int id) {
        T result = null;
        try {
            if ((result = (T) views.get(id)) != null)
                return result;
            result = (T) itemView.findViewById(id);
            if (result == null)
                return result;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return result;
        }
        //add to cache
        views.put(id, View.class.cast(result));
        return result;
    }

    @Override
    public CharSequence getText(@IdRes int id) {
        TextView targetTxt = getView(id);
        if (targetTxt != null)
            return targetTxt.getText().toString().trim();
        return "";
    }

    @Override
    public IHolder setText(@IdRes int id, CharSequence text) {
        TextView targetTxt = getView(id);
        if (targetTxt != null)
            targetTxt.setText(emptyIfNull(text));
        return this;
    }


    @Override
    public IHolder setText(@IdRes int id, @StringRes int stringId) {
        TextView targetTxt = getView(id);
        if (targetTxt != null)
            targetTxt.setText(emptyIfNull(getContext().getString(stringId)));
        return this;
    }

    @Override
    public IHolder setTextTrim(@IdRes int id, @StringRes int stringId) {
        TextView targetTxt = getView(id);
        if (targetTxt != null)
            targetTxt.setText(emptyIfNull(getContext().getString(stringId).trim()));
        return this;
    }

    @Override
    public IHolder setTextTrim(@IdRes int id, CharSequence text) {
        TextView targetTxt = getView(id);
        if (targetTxt != null)
            targetTxt.setText(emptyIfNull(text).toString().trim());
        return this;
    }

    private CharSequence emptyIfNull(@Nullable CharSequence str) {
        return str == null ? "" : str;
    }

    @Override
    public IHolder setTextWithVisibility(@IdRes int id, CharSequence text) {
        TextView targetTxt = getView(id);
        if (!TextUtils.isEmpty(text)) {
            targetTxt.setVisibility(View.VISIBLE);
            targetTxt.setText(text);
        } else {
            targetTxt.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public IHolder setTextWithVisibility(@IdRes int id, @StringRes int stringId) {
        TextView targetTxt = getView(id);
        if (stringId > 0) {
            targetTxt.setVisibility(View.VISIBLE);
            targetTxt.setText(getContext().getString(stringId));
        } else {
            targetTxt.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public IHolder setTextColor(@IdRes int id, @ColorInt int textColor) {
        TextView targetTxt = getView(id);
        targetTxt.setTextColor(textColor);
        return this;
    }

    @Override
    public IHolder setTextColorRes(@IdRes int id, @ColorRes int textColor) {
        TextView targetTxt = getView(id);
        targetTxt.setTextColor(ContextCompat.getColor(getContext(), textColor));
        return this;
    }

    @Override
    public IHolder setSelected(@IdRes int id, boolean selected) {
        View targetTxt = getView(id);
        targetTxt.setSelected(selected);
        return this;
    }

    @Override
    public IHolder setEnabled(@IdRes int id, boolean enabled) {
        View targetTxt = getView(id);
        targetTxt.setEnabled(enabled);
        return this;
    }

    @Override
    public IHolder setVisibility(@IdRes int id, int visibility) {
        View targetTxt = getView(id);
        targetTxt.setVisibility(visibility);
        return this;
    }

    @Override
    public IHolder setVisible(@IdRes int id) {
        View targetTxt = getView(id);
        targetTxt.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public IHolder setInvisible(@IdRes int id) {
        View targetTxt = getView(id);
        targetTxt.setVisibility(View.INVISIBLE);
        return this;
    }

    @Override
    public IHolder setGone(@IdRes int id) {
        View targetTxt = getView(id);
        targetTxt.setVisibility(View.GONE);
        return this;
    }

    @Override
    public IHolder setImageResource(@IdRes int id, @DrawableRes int res) {
        ImageView view = getView(id);
        view.setImageResource(res);
        return this;
    }

    @Override
    public IHolder setImageBitmap(@IdRes int id, Bitmap bitmap) {
        ImageView view = getView(id);
        view.setImageBitmap(bitmap);
        return this;
    }

    @Override
    public IHolder setImageDrawable(@IdRes int id, Drawable drawable) {
        ImageView view = getView(id);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public IHolder setBackgroundResource(@IdRes int id, @DrawableRes int res) {
        View view = getView(id);
        view.setBackgroundResource(res);
        return this;
    }

    @Override
    public IHolder setBackgroundBitmap(@IdRes int id, Bitmap bitmap) {
        View view = getView(id);
        view.setBackgroundDrawable(new BitmapDrawable(getContext().getResources(), bitmap));
        return this;
    }

    @Override
    public IHolder setBackgroundDrawable(@IdRes int id, Drawable drawable) {
        View view = getView(id);
        view.setBackgroundDrawable(drawable);
        return this;
    }

    @Override
    public IHolder setOnItemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }

    @Override
    public IHolder setOnClickListener(@IdRes int id, View.OnClickListener onClickListener) {
        getView(id).setOnClickListener(onClickListener);
        return this;
    }

    @Override
    public IHolder setOnLongClickListener(@IdRes int id, View.OnLongClickListener onLongClickListener) {
        getView(id).setOnLongClickListener(onLongClickListener);
        return null;
    }

    @Override
    public IHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    @Override
    public IHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    @Override
    public IHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    @Override
    public IHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    @Override
    public IHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    @Override
    public IHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    @Override
    public IHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    @Override
    public IHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    @Override
    public IHolder setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }
}
