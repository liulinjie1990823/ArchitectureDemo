package com.llj.adapter.listener;

import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;

public interface HeaderClickListener {
    void onHeaderClicked(UniversalAdapter adapter, ViewHolder headerHolder, int position);

    void onHeaderDoubleClicked(UniversalAdapter adapter, ViewHolder headerHolder, int position);
}
