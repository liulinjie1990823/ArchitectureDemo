package com.llj.architecturedemo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.architecturedemo.ui.model.BabyHomeModuleItemVo
import com.llj.component.service.MiddleMvcBaseFragment
import com.llj.component.service.arouter.CRouter
import com.llj.lib.scrollable.ScrollableHelper
import com.llj.lib.webview.CWebViewActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
class HomeFragment : MiddleMvcBaseFragment(), ScrollableHelper.ScrollableContainer {
    override fun getScrollableView(): View {
        return mRecyclerView
    }

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: androidx.recyclerview.widget.RecyclerView

    @BindView(R.id.tv_update)
    lateinit var mUpdate: TextView

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
        arrayList.add(Data("AdjustResizeActivity", CRouter.APP_ADJUST_RESIZE_ACTIVITY2))
        arrayList.add(Data("ViewPager2Activity", CRouter.APP_VIEWPAGER2_ACTIVITY))
        arrayList.add(Data("SvgActivity", CRouter.APP_SVG_ACTIVITY))
        arrayList.add(Data("RunnableActivity", CRouter.APP_RUNNABLE_ACTIVITY))
        arrayList.add(Data("InjectActivity", CRouter.SETTING_INJECT_ACTIVITY))
        arrayList.add(Data("SecondActivity", CRouter.APP_FIRST_ACTIVITY))
        arrayList.add(Data("FirstActivity", CRouter.APP_FIRST_ACTIVITY))
        arrayList.add(Data("KodoActivity", CRouter.APP_KODO_ACTIVITY))
        arrayList.add(Data("RecordVideoActivity", CRouter.APP_RECORD_VIDEO_ACTIVITY))
        arrayList.add(Data("RecordVideo2Activity", CRouter.APP_RECORD_VIDEO2_ACTIVITY))
        arrayList.add(Data("GLSurfaceViewActivity", CRouter.APP_GLSURFACE_VIEW_ACTIVITY))
        arrayList.add(Data("LoginActivity", CRouter.LOGIN_LOGIN_ACTIVITY))
        arrayList.add(Data("QrCodeActivity", CRouter.SETTING_QRCODE_ACTIVITY))
        arrayList.add(Data("PermissionActivity", CRouter.APP_PERMISSION_ACTIVITY))
        arrayList.add(Data("EventActivity", CRouter.APP_EVENT_ACTIVITY))
        arrayList.add(Data("RewardLayoutActivity", CRouter.APP_REWARD_LAYOUT_ACTIVITY))
        arrayList.add(Data("SwipeBackLayoutActivity", CRouter.APP_SWIPE_BACK_LAYOUT_ACTIVITY))
        arrayList.add(Data("JavaTypeActivity", CRouter.APP_JAVA_TYPE_ACTIVITY))
        arrayList.add(Data("RequestActivity", CRouter.APP_REQUEST_ACTIVITY))
        arrayList.add(Data("RxJava2Activity", CRouter.APP_RXJAVA2_ACTIVITY))
        arrayList.add(Data("ShareActivity", CRouter.APP_SHARE_ACTIVITY))
        arrayList.add(Data("MemoryLeakActivity", CRouter.APP_MEMORY_LEAK_ACTIVITY))
        arrayList.add(Data("AptActivity", CRouter.APP_APT_ACTIVITY))
        arrayList.add(Data("AptActivity2", CRouter.APP_APT_ACTIVITY2))
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

            viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    when (item.text) {
                        "CWebViewActivity" -> CWebViewActivity.start(mContext, "http://m.reallycar.cn/ocert")
                        "FirstActivity" -> {
                            val intent = Intent()
                            intent.setClassName("com.llj.architecturedemo", "com.llj.architecturedemo.ui.activity.FirstActivity")
                            mContext.startActivity(intent)
                        }
                        "SecondActivity" -> {
                            val intent = Intent()
                            intent.setClassName("com.llj.architecturedemo", "com.llj.architecturedemo.ui.activity.SecondActivity")
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            mContext.startActivity(intent)
                        }
                        else -> CRouter.start(item.path)
                    }
                }
            })
        }
    }

    private inner class Data(var text: String, var path: String)
}