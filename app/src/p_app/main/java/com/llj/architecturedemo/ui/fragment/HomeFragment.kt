package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import butterknife.BindView
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.BaseFragment

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
class HomeFragment : BaseFragment() {
    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViews(savedInstanceState: Bundle?) {
        val arrayList = arrayListOf<Data>()
        arrayList.add(Data("ConstraintActivity", CRouter.WIDGET_CONSTRAINT_ACTIVITY))
        arrayList.add(Data("TouchEventActivity", CRouter.APP_TOUCH_EVENT_ACTIVITY))
        arrayList.add(Data("RecycleViewActivity", CRouter.APP_RECYCLE_VIEW_ACTIVITY))
        arrayList.add(Data("NestedScrollViewActivity", CRouter.APP_RECYCLE_VIEW_ACTIVITY))
        arrayList.add(Data("LinearLayoutActivity", CRouter.APP_LINEAR_LAYOUT_ACTIVITY))
        arrayList.add(Data("ComponentActivity", CRouter.APP_COMPONENT_ACTIVITY))
        arrayList.add(Data("LoginActivity", CRouter.LOGIN_LOGIN_ACTIVITY))
        arrayList.add(Data("RegisterActivity", CRouter.LOGIN_REGISTER_ACTIVITY))
        arrayList.add(Data("HookActivity", CRouter.APP_HOOK_ACTIVITY))

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
                CRouter.start(item.path)
            }
        }
    }

    private inner class Data(var text: String, var path: String)
}