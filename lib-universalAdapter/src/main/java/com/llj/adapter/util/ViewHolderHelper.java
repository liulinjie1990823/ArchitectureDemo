package com.llj.adapter.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llj.adapter.ViewHolder;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/2/11.
 */

public class ViewHolderHelper extends ViewHolder implements IHolder {

    private HolderHelper mHolderHelper;

    public ViewHolderHelper(View itemView) {
        super(itemView);
        mHolderHelper = new HolderHelper(itemView);
    }

    public static ViewHolderHelper createViewHolder(View itemView) {
        return new ViewHolderHelper(itemView);
    }

    public static ViewHolderHelper createViewHolder(ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolderHelper(itemView);
    }


    @Override
    public Context getContext() {
        return mHolderHelper.getContext();
    }

    @Override
    public void removeFromCache(@IdRes int id) {
        mHolderHelper.removeFromCache(id);
    }

    @Override
    public void removeAll() {
        mHolderHelper.removeAll();
    }

    @Override
    public <T extends View> T getView(@IdRes int id) {
        return mHolderHelper.getView(id);
    }

    @Override
    public CharSequence getText(@IdRes int id) {
        return mHolderHelper.getText(id);
    }

    @Override
    public IHolder setText(@IdRes int id, CharSequence text) {
        return mHolderHelper.setText(id, text);
    }

    @Override
    public IHolder setText(@IdRes int id, @StringRes int stringId) {
        return mHolderHelper.setText(id, stringId);
    }

    @Override
    public IHolder setTextTrim(@IdRes int id, CharSequence text) {
        return mHolderHelper.setTextTrim(id, text);
    }

    @Override
    public IHolder setTextTrim(@IdRes int id, @StringRes int stringId) {
        return mHolderHelper.setTextTrim(id, stringId);
    }

    @Override
    public IHolder setTextWithVisibility(@IdRes int id, CharSequence text) {
        return mHolderHelper.setTextWithVisibility(id, text);
    }

    @Override
    public IHolder setTextWithVisibility(@IdRes int id, @StringRes int stringId) {
        return mHolderHelper.setTextWithVisibility(id, stringId);
    }

    @Override
    public IHolder setTextColor(@IdRes int id, @ColorInt int textColor) {
        return mHolderHelper.setTextColor(id, textColor);
    }

    @Override
    public IHolder setTextColorRes(@IdRes int id, @ColorRes int textColor) {
        return mHolderHelper.setTextColorRes(id, textColor);
    }

    @Override
    public IHolder setSelected(@IdRes int id, boolean selected) {
        return mHolderHelper.setSelected(id, selected);
    }

    @Override
    public IHolder setEnabled(@IdRes int id, boolean enabled) {
        return mHolderHelper.setEnabled(id, enabled);
    }

    @Override
    public IHolder setVisibility(@IdRes int id, int visibility) {
        return mHolderHelper.setVisibility(id, visibility);
    }

    @Override
    public IHolder setVisible(@IdRes int id) {
        return mHolderHelper.setVisible(id);
    }

    @Override
    public IHolder setInvisible(@IdRes int id) {
        return mHolderHelper.setInvisible(id);
    }

    @Override
    public IHolder setGone(@IdRes int id) {
        return mHolderHelper.setGone(id);
    }

    @Override
    public IHolder setImageResource(@IdRes int id, @DrawableRes int res) {
        return mHolderHelper.setImageResource(id, res);
    }

    @Override
    public IHolder setImageBitmap(@IdRes int id, Bitmap bitmap) {
        return mHolderHelper.setImageBitmap(id, bitmap);
    }

    @Override
    public IHolder setImageDrawable(@IdRes int id, Drawable drawable) {
        return mHolderHelper.setImageDrawable(id, drawable);
    }

    @Override
    public IHolder setBackgroundResource(@IdRes int id, @DrawableRes int res) {
        return mHolderHelper.setBackgroundResource(id, res);
    }

    @Override
    public IHolder setBackgroundBitmap(@IdRes int id, Bitmap bitmap) {
        return mHolderHelper.setBackgroundBitmap(id, bitmap);
    }

    @Override
    public IHolder setBackgroundDrawable(@IdRes int id, Drawable drawable) {
        return mHolderHelper.setBackgroundDrawable(id, drawable);
    }

    @Override
    public IHolder setOnItemClickListener(View.OnClickListener listener) {
        return mHolderHelper.setOnItemClickListener(listener);
    }

    @Override
    public IHolder setOnClickListener(@IdRes int id, View.OnClickListener onClickListener) {
        return mHolderHelper.setOnClickListener(id, onClickListener);
    }

    @Override
    public IHolder setOnLongClickListener(@IdRes int id, View.OnLongClickListener onLongClickListener) {
        return mHolderHelper.setOnLongClickListener(id, onLongClickListener);
    }

    @Override
    public IHolder setTag(@IdRes int viewId, Object tag) {
        return mHolderHelper.setTag(viewId, tag);
    }

    @Override
    public IHolder setTag(@IdRes int viewId, int key, Object tag) {
        return mHolderHelper.setTag(viewId, key, tag);
    }

    @Override
    public IHolder setTypeface(Typeface typeface, int... viewIds) {
        return mHolderHelper.setTypeface(typeface, viewIds);
    }

    @Override
    public IHolder setProgress(@IdRes int viewId, int progress) {
        return mHolderHelper.setProgress(viewId, progress);
    }

    @Override
    public IHolder setProgress(@IdRes int viewId, int progress, int max) {
        return mHolderHelper.setProgress(viewId, progress, max);
    }

    @Override
    public IHolder setMax(@IdRes int viewId, int max) {
        return mHolderHelper.setMax(viewId, max);
    }

    @Override
    public IHolder setRating(@IdRes int viewId, float rating) {
        return mHolderHelper.setRating(viewId, rating);
    }

    @Override
    public IHolder setRating(@IdRes int viewId, float rating, int max) {
        return mHolderHelper.setRating(viewId, rating, max);
    }

    @Override
    public IHolder setChecked(@IdRes int viewId, boolean checked) {
        return mHolderHelper.setChecked(viewId, checked);
    }
}

