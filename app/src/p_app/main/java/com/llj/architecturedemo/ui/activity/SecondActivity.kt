package com.llj.architecturedemo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.refresh.RefreshHelper
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.MyBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.presenter.SecondPresenter
import com.llj.architecturedemo.view.SecondView
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.listeners.MyTextWatcher
import com.llj.lib.utils.LogUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * ArchitectureDemo
 * describe:
 * author llj
 * date 2018/7/3
 */
@Route(path = CRouter.APP_SECOND_ACTIVITY)
class SecondActivity : MyBaseActivity<SecondPresenter>(), SecondView {
    @BindView(R.id.mTvClick) lateinit var mTvClick: TextView
    @BindView(R.id.mRefreshLayout) lateinit var mRefreshLayout: SmartRefreshLayout
    @BindView(R.id.mRecyclerView) lateinit var mRecyclerView: RecyclerView
    override fun toast(mobile: MobileEntity?) {
    }

    private lateinit var mRefreshHelper: RefreshHelper<Test, ViewHolderHelper>

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SecondActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }

        val handle2: Handler by lazy {
            object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message?) {
                    super.handleMessage(msg)
                }

            }
        }

    }

    override fun layoutId(): Int {
        return R.layout.activity_second
    }

    override fun initViews(savedInstanceState: Bundle?) {


        mTvClick.addTextChangedListener(object : MyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        mTvClick.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                LogUtil.LLJi(mTagLog, v?.hashCode())
                LogUtil.LLJi(mTagLog, v?.hashCode())
            }
        })
        mTvClick.setOnClickListener(View.OnClickListener { v ->
            LogUtil.LLJi(mTagLog, v.hashCode())
            LogUtil.LLJi(mTagLog, v.hashCode())
        })
        mTvClick.setOnClickListener { v ->
            LogUtil.LLJi(mTagLog, v?.hashCode())
            LogUtil.LLJi(mTagLog, v?.hashCode())
        }
        mTvClick.setOnClickListener {
            LogUtil.LLJi(mTagLog, it.hashCode())
            LogUtil.LLJi(mTagLog, it.hashCode())
        }

        mTvClick.setOnClickListener {
            CRouter.start(CRouter.APP_MAIN_ACTIVITY)
        }

        val arrayList = arrayListOf(Test("test1")
                , Test("test2")
                , Test("test3")
                , Test("test4")
                , Test("test5"))


        val adapter = UniversalBind.Builder(mRecyclerView, MyAdapter(null))
                .setLinearLayoutManager()
                .setNestedScrollingEnabled(false)
                .build()
                .getAdapter()
        mRefreshHelper = RefreshHelper(mRefreshLayout, adapter)

        mRefreshLayout.setOnRefreshListener {

            mRefreshHelper.resetPageNum()

            mTvClick.postDelayed({
                mRefreshHelper.finishRefreshOrLoadMore(true)
                mRefreshHelper.handleData(true, arrayList)
            }, 1000)

        }.setOnLoadMoreListener {
            mTvClick.postDelayed({
                mRefreshHelper.finishRefreshOrLoadMore(true)
                mRefreshHelper.handleData(true, arrayList)
            }, 3000)
        }


        mRefreshLayout.autoRefresh()

    }

    override fun initData() {
    }


    private class MyAdapter : ListBasedAdapter<Test, ViewHolderHelper> {

        constructor(list: MutableList<Test>?) : super(list) {
            addItemLayout(R.layout.item_test)
        }

        override fun onBindViewHolder(viewHolder: ViewHolderHelper, item: Test?, position: Int) {
            if (item == null) {
                return
            }
            viewHolder.setText(R.id.tv_title, item.title)

        }

    }

    data class Test(var title: String? = null) {
        var name: String? = null
    }


    private val mHandler: MyHandler = MyHandler()

    inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            mContext.resources
        }
    }

    val handle: Handler by lazy {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                mContext.resources
            }

        }
    }

    val handle2 = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            mContext.resources
        }

    }


}
