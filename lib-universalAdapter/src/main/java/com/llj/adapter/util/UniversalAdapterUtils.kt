package com.llj.adapter.util

import android.view.View
import com.llj.adapter.R

/**
 * PROJECT:CommonAdapter DESCRIBE: Created by llj on 2017/2/11.
 */
object UniversalAdapterUtils {
    @JvmStatic
    fun setViewHolder(view: View?, holder: Any?) {
        view?.setTag(R.id.com_viewholderTagID, holder)
    }

    @JvmStatic
    fun <Holder> getViewHolder(view: View): Holder {
        return view.getTag(R.id.com_viewholderTagID) as Holder
    }

    @JvmStatic
    fun getIndex(view: View): Int {
        return view.getTag(R.id.com_viewholderIndexID) as Int
    }
}