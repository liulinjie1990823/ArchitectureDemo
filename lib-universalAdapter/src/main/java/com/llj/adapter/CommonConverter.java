package com.llj.adapter;

import android.support.annotation.NonNull;

/**
 * PROJECT:UniversalAdapter
 * DESCRIBE:
 * Created by llj on 2017/1/15.
 */

public interface CommonConverter<Item, Holder extends ViewHolder> {

    void setAdapter(@NonNull UniversalAdapter<Item, Holder> listAdapter);

    UniversalAdapter<Item, Holder> getAdapter();

    void cleanup();
}
