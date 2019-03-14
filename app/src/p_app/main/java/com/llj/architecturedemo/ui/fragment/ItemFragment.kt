package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.component.service.ComponentMvcBaseFragment
import com.llj.component.service.scrollableLayout.ScrollableHelper

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
class ItemFragment : ComponentMvcBaseFragment(), ScrollableHelper.ScrollableContainer {
    override fun getScrollableView(): View {
        return mRecyclerView
    }

    companion object {
        fun getInstance(): Fragment {
            return ItemFragment()
        }
    }

    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView

    override fun layoutId(): Int {
        return R.layout.fragment_item
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val arrayList = arrayListOf<Data>()

        for (i in 0 until 100) {
            arrayList.add(Data("text$i"))
        }
        UniversalBind.Builder(mRecyclerView, MyAdapter(arrayList))
                .setLinearLayoutManager()
                .build()
                .getAdapter()
    }

    override fun initData() {
    }


    private inner class MyAdapter(list: MutableList<Data>?) : ListBasedAdapter<Data, ViewHolderHelper>(list) {
        init {
            addItemLayout(R.layout.item_home_fragment)
        }

        override fun onBindViewHolder(viewHolder: ViewHolderHelper, item: Data?, position: Int) {
            if (item == null) {
                return
            }
            val textView = viewHolder.getView<TextView>(R.id.tv_text)
            setText(textView, item.text)
        }
    }

    private inner class Data(var text: String)
}