package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.architecturedemo.ui.model.BabyHomeModuleItemVo
import com.llj.component.service.ComponentMvcBaseFragment
import com.llj.component.service.arouter.CRouter
import com.llj.component.service.scrollableLayout.ScrollableHelper
import com.llj.lib.webview.CWebViewActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
class HomeFragment : ComponentMvcBaseFragment(), ScrollableHelper.ScrollableContainer {
    override fun getScrollableView(): View {
        return mRecyclerView
    }

    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.tv_update) lateinit var mUpdate: TextView

    companion object {
        fun getInstance(data: BabyHomeModuleItemVo, position: Int): HomeFragment {
            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putSerializable("data", data)
            val dataFragment = HomeFragment()
            dataFragment.arguments = bundle
            return dataFragment
        }
    }


    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        val arrayList = arrayListOf<Data>()
        arrayList.add(Data("LoginActivity", CRouter.LOGIN_LOGIN_ACTIVITY))
        arrayList.add(Data("QrCodeActivity", CRouter.QRCODE_ACTIVITY))
        arrayList.add(Data("PermissionActivity", CRouter.APP_PERMISSION_ACTIVITY))
        arrayList.add(Data("EventActivity",CRouter.APP_EVENT_ACTIVITY))
        arrayList.add(Data("RewardLayoutActivity", CRouter.APP_REWARD_LAYOUT_ACTIVITY))
        arrayList.add(Data("SwipeBackLayoutActivity", CRouter.APP_SWIPE_BACK_LAYOUT_ACTIVITY))
        arrayList.add(Data("JavaTypeActivity", CRouter.APP_JAVA_TYPE_ACTIVITY))
        arrayList.add(Data("RequestActivity", CRouter.APP_REQUEST_ACTIVITY))
        arrayList.add(Data("RxJava2Activity", CRouter.APP_RXJAVA2_ACTIVITY))
        arrayList.add(Data("ShareActivity", CRouter.APP_SHARE_ACTIVITY))
        arrayList.add(Data("MemoryLeakActivity", CRouter.APP_MEMORY_LEAK_ACTIVITY))
        arrayList.add(Data("AptActivity", CRouter.APP_APT_ACTIVITY))
        arrayList.add(Data("ConstraintActivity", CRouter.WIDGET_CONSTRAINT_ACTIVITY))
        arrayList.add(Data("SecondActivity", CRouter.APP_SECOND_ACTIVITY))
        arrayList.add(Data("TouchEventActivity", CRouter.APP_TOUCH_EVENT_ACTIVITY))
        arrayList.add(Data("RecycleViewActivity", CRouter.APP_RECYCLE_VIEW_ACTIVITY))
        arrayList.add(Data("NestedScrollViewActivity", CRouter.APP_RECYCLE_VIEW_ACTIVITY))
        arrayList.add(Data("LinearLayoutActivity", CRouter.APP_LINEAR_LAYOUT_ACTIVITY))
        arrayList.add(Data("ComponentActivity", CRouter.APP_COMPONENT_ACTIVITY))
        arrayList.add(Data("RegisterActivity", CRouter.LOGIN_REGISTER_ACTIVITY))
        arrayList.add(Data("HookActivity", CRouter.APP_HOOK_ACTIVITY))
        arrayList.add(Data("ProxyActivity", CRouter.APP_PROXY_ACTIVITY))
        arrayList.add(Data("CWebViewActivity", "CWebViewActivity"))
        arrayList.add(Data("PhoneLoginActivity", CRouter.LOGIN_PHONE_LOGIN_ACTIVITY))

        UniversalBind.Builder(mRecyclerView, MyAdapter(arrayList))
                .setLinearLayoutManager()
                .build()
                .getAdapter()


        mUpdate.setOnClickListener {
            mRecyclerView.adapter?.notifyDataSetChanged()
        }

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
            Log.e("onBindViewHolder", position.toString())

            val textView = viewHolder.getView<TextView>(R.id.tv_text)
            setText(textView, position.toString() + "  " + item.text)

            viewHolder.itemView.setOnClickListener {
                when (item.path) {
                    "CWebViewActivity" -> CWebViewActivity.start(mContext, "http://m.reallycar.cn/ocert")
                    else -> CRouter.start(item.path)
                }

            }
        }
    }

    private inner class Data(var text: String, var path: String)
}