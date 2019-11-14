package com.llj.lib.image.select.ui.loader;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.llj.lib.image.select.ui.MediaPickConfig;

import java.lang.ref.WeakReference;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-28
 */
public class MediaLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;

    private WeakReference<Context> mContext;
    private LoaderManager          mLoaderManager;
    private MediaPickConfig        mConfig;
    private MediaCallback          mCallbacks;

    private boolean mLoadFinished;

    public MediaLoaderHelper(FragmentActivity activity, MediaPickConfig config, MediaCallback callbacks) {
        mContext = new WeakReference<>(activity);
        mLoaderManager = activity.getSupportLoaderManager();
        mConfig = config;
        mCallbacks = callbacks;
    }

    public void loadData() {
        mLoaderManager.initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        mLoadFinished = false;
        return MediaLoader.newInstance(context, mConfig);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        mCallbacks.onMediaLoad(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }

        mCallbacks.onMediaReset();
    }

    public interface MediaCallback {
        void onMediaLoad(Cursor cursor);

        void onMediaReset();
    }
}
