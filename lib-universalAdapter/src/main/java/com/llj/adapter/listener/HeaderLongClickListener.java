package com.llj.adapter.listener;

import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;

public interface HeaderLongClickListener {
    boolean onHeaderLongClicked(UniversalAdapter adapter, ViewHolder footerHolder, int position);
}
