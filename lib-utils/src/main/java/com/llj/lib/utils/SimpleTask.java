package com.llj.lib.utils;

import android.os.AsyncTask;

/**
 * AsyncTask的基类
 *
 * @param <T>
 * @author liulj
 */
public abstract class SimpleTask<T> extends AsyncTask<Void, Void, T> {
    public final String error = "当前网络不稳定，请稍后再试";

    public SimpleTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected abstract T doInBackground(Void... params);

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
    }

}
