package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.facebook.drawee.view.GenericDraweeView
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.architecturedemo.ui.fragment.*
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.architecturedemo.ui.model.TabVo
import com.llj.architecturedemo.ui.presenter.MainPresenter
import com.llj.architecturedemo.ui.view.MainContractView
import com.llj.component.service.arouter.CRouter
import com.llj.component.service.preference.ConfigPreference
import com.llj.lib.base.BaseTabActivity
import com.llj.lib.base.IUiHandler
import com.llj.lib.image.loader.FrescoImageLoader
import com.llj.lib.image.loader.ICustomImageLoader
import com.llj.lib.net.response.BaseResponse

@Route(path = CRouter.APP_MAIN_ACTIVITY)
class MainActivity : BaseTabActivity<MainPresenter>(), MainContractView {


    @BindView(R.id.ll_footer_bar) lateinit var mLlFooterBar: LinearLayout

    private lateinit var mTabAdapter: TabAdapter

    override fun getFragmentId(): Int {
        return R.id.fl_contain
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews(savedInstanceState: Bundle?) {

        mTabAdapter = UniversalBind.Builder(mLlFooterBar, TabAdapter(null))
                .build()
                .getAdapter()

        updateTabByLocal()

        super.initViews(savedInstanceState)
    }

    private fun getDefaultTabList(): ArrayList<TabVo> {
        val tabList = ArrayList<TabVo>()

        tabList.add(TabVo(getString(R.string.tab_home), R.drawable.ic_tab_home_normal, R.drawable.ic_tab_home_selected, TAB_INDEX))
        tabList.add(TabVo(getString(R.string.tab_community), R.drawable.ic_tab_baby_show_normal, R.drawable.ic_tab_baby_show_selected, TAB_TONGSHANG))
        tabList.add(TabVo("测试", R.drawable.ic_tab_ybs, R.drawable.ic_tab_ybs, TAB_EXPO))
        tabList.add(TabVo(getString(R.string.tab_coupon), R.drawable.ic_tab_coupon_normal, R.drawable.ic_tab_coupon_selected, TAB_CASH))
        tabList.add(TabVo(getString(R.string.tab_mine), R.drawable.ic_tab_mine_normal, R.drawable.ic_tab_mine_selected, TAB_MY))

        return tabList
    }

    private fun updateTabByLocal() {
        val tabList = ConfigPreference.getInstance().getTabList()
        val list: List<TabVo>
        if (isEmpty(tabList)) {
            //使用本地的
            list = getDefaultTabList()
        } else {
            //使用缓存的
            list = Gson().fromJson<List<TabVo>>(tabList, object : TypeToken<List<TabVo>>() {}.type)
        }
        mTabAdapter.addAll(list)
    }

    override fun initData() {
    }


    private inner class TabAdapter(list: ArrayList<TabVo>?) : ListBasedAdapter<TabVo, ViewHolderHelper>(list), IUiHandler {

        private val mImageLoad: ICustomImageLoader<GenericDraweeView> = FrescoImageLoader.getInstance(mContext.applicationContext)

        init {
            addItemLayout(R.layout.item_main_activity_tab)
        }

        override fun onBindViewHolder(viewHolder: ViewHolderHelper, item: TabVo?, position: Int) {
            if (item == null) {
                return
            }
            val image = viewHolder.getView<SimpleDraweeView>(R.id.iv_tab_image)
            val text = viewHolder.getView<TextView>(R.id.tv_tab_text)

            //设置图片改变
            if (item.default_img_id != 0) {
                val imageId: Int = if (mShowItem == item.type) item.hover_img_id else item.default_img_id
                mImageLoad.loadImage(imageId, 120, 120, image)
            } else {
                val imageUrl: String? = if (mShowItem == item.type) item.hover_img else item.default_img
                mImageLoad.loadImage(imageUrl, 120, 120, image)
            }

            setText(text, item.title)
            //设置字体颜色的改变
            viewHolder.itemView.isSelected = mShowItem == item.type

            viewHolder.itemView.tag = item.type

            viewHolder.itemView.setOnClickListener {
                selectItemFromTagByClick(it)
            }
        }
    }

    companion object {
        private const val TAB_INDEX = "index"
        private const val TAB_TONGSHANG = "tongshang"
        private const val TAB_EXPO = "expo"
        private const val TAB_CASH = "cash"
        private const val TAB_MY = "my"
    }

    override fun makeFragment(showItem: String): Fragment {
        when (showItem) {
            TAB_INDEX -> return HomeFragment()
            TAB_TONGSHANG -> return VLayoutFragment()
            TAB_EXPO -> return VLayoutFragment2()
            TAB_CASH -> return ScrollableLayoutFragment()
            TAB_MY -> return MineFragment()
        }
        return HomeFragment()
    }

    override fun changeSelectImage(showItem: String, hideItem: String) {
        var showPosition = 0
        var hidePosition = 0
        val childCount = mLlFooterBar.childCount
        for (i in 0 until childCount) {
            if (mTabAdapter[i]?.type == showItem) {
                showPosition = i
            } else if (showItem != hideItem && mTabAdapter[i]?.type == hideItem) {
                hidePosition = i
            }
        }
        mTabAdapter.onItemRangeChanged(showPosition, 1)
        mTabAdapter.onItemRangeChanged(hidePosition, 1)
    }

    override fun getParams(): HashMap<String, Any> {
        return HashMap()
    }

    override fun onDataSuccess(result: BaseResponse<TabListVo?>) {
    }

    override fun onDataError(e: Throwable) {
    }
}
