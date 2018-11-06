package com.llj.architecturedemo.ui.activity

import android.Manifest
import android.app.AlertDialog
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
import com.llj.architecturedemo.ui.fragment.HomeFragmentMvc
import com.llj.architecturedemo.ui.fragment.MineFragment
import com.llj.architecturedemo.ui.fragment.ScrollableLayoutFragment
import com.llj.architecturedemo.ui.fragment.VlayoutFragment
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
import com.llj.lib.utils.AGsonUtils
import com.llj.lib.utils.ATimeUtils
import com.llj.lib.utils.AToastUtils
import com.llj.lib.utils.helper.Utils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import java.util.*

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

        //获取deviceId需要权限
        AndPermission.with(Utils.getApp())
                .runtime()
                .permission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted {
                }
                .onDenied { permissions ->
                    AToastUtils.show(permissions?.toString())
                }
                .rationale { context, permissions, executor ->
                    val permissionNames = Permission.transformText(context, permissions)
                    val message = "读取电话状态"

                    AlertDialog.Builder(context)
                            .setCancelable(false)
                            .setTitle("提示")
                            .setMessage(message)
                            .setPositiveButton("继续") { dialog, which -> executor.execute() }
                            .setNegativeButton("取消") { dialog, which -> executor.cancel() }
                            .show()
                }
                .start()

        mTabAdapter = UniversalBind.Builder(mLlFooterBar, TabAdapter(null))
                .build()
                .getAdapter()

        super.initViews(savedInstanceState)

        //不同天就更新
        val tabListUpdateDate = ConfigPreference.getInstance().getTabListUpdateDate()
        val millisecondsToString = ATimeUtils.millisecondsToString(ATimeUtils.FORMAT_EIGHT, System.currentTimeMillis())
        if (tabListUpdateDate != millisecondsToString) {
            mPresenter.getTabBar(false)
        } else {
            updateTabByLocal()
        }

    }

    private fun updateTabByLocal() {
        val tabList = ConfigPreference.getInstance().getTabList()
        if (isEmpty(tabList)) {
            //使用本地的
        } else {
            //使用缓存的
            val list = Gson().fromJson<List<TabVo>>(tabList, object : TypeToken<List<TabVo>>() {}.type)
            mTabAdapter.addAll(list)
        }

        performSelectItem(mHideItem, mShowItem, true)
    }

    override fun initData() {
    }

    override fun getParams(): HashMap<String, Any> {
        return HashMap()
    }

    override fun onDataSuccess(result: BaseResponse<TabListVo?>) {

        val data: TabListVo? = result.data
        if (data == null || isEmpty(data.tabbar)) {
            updateTabByLocal()
            return
        }
        //保存到本地
        ConfigPreference.getInstance().saveTabList(AGsonUtils.toJson(data.tabbar!!))

        //更新tab
        mTabAdapter.addAll(data.tabbar)

        performSelectItem(mHideItem, mShowItem, true)
    }

    override fun onDataError(e: Throwable) {
        updateTabByLocal()
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
            val imageUrl: String? = if (mShowItem == item.type) item.hover_img else item.default_img
            mImageLoad.loadImage(imageUrl, 120, 120, image)

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
        private const val TAB_INDEX = "TAB_INDEX"
        private const val TAB_TONGSHANG = "TAB_TONGSHANG"
        private const val TAB_EXPO = "TAB_EXPO"
        private const val TAB_CASH = "TAB_CASH"
        private const val TAB_MY = "TAB_MY"
    }

    override fun makeFragment(showItem: String): Fragment {
        when (showItem) {
            TAB_INDEX -> return HomeFragmentMvc()
            TAB_TONGSHANG -> return VlayoutFragment()
            TAB_EXPO -> return ScrollableLayoutFragment()
            TAB_CASH -> return ScrollableLayoutFragment()
            TAB_MY -> return MineFragment()
        }
        return HomeFragmentMvc()
    }

    override fun changeSelectImage(showItem: String) {
        var showPosition = 0
        var hidePosition = 0
        val childCount = mLlFooterBar.childCount
        for (i in 0 until childCount) {
            if (mTabAdapter[i]?.type == showItem) {
                showPosition = i
            } else if (mTabAdapter[i]?.type == mHideItem) {
                hidePosition = i
            }
        }
        mTabAdapter.onItemRangeChanged(showPosition, 1)
        mTabAdapter.onItemRangeChanged(hidePosition, 1)
    }
}
