package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.billy.cc.core.component.CC
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/24
 */
@Route(path = CRouter.APP_COMPONENT_ACTIVITY)
class ComponentActivity : MvcBaseActivity() {
    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView

    override fun layoutId(): Int {
        return R.layout.activity_componet
    }

    override fun initViews(savedInstanceState: Bundle?) {

        val arrayList = arrayListOf<Data>()

        arrayList.add(Data("LoginComponent", "LoginComponent", "login"))
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

            viewHolder.itemView.setOnClickListener {
                CC.obtainBuilder(item.component)
                        .setActionName(item.action)
                        .build()
                        .call()
            }
        }
    }

    private inner class Data(var text: String, var component: String, var action: String)
}