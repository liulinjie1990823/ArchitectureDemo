package com.llj.adapter.listener;

import com.llj.adapter.UniversalAdapter;
import com.llj.adapter.ViewHolder;

public interface FooterLongClickListener {
    boolean onFooterLongClicked(UniversalAdapter adapter, ViewHolder footerHolder, int position);
}
