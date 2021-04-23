package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.application.preference.ConfigPreference
import com.llj.application.router.CRouter
import com.llj.architecturedemo.R
import com.llj.architecturedemo.R2
import com.llj.architecturedemo.ui.fragment.*
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.architecturedemo.ui.model.TabVo
import com.llj.architecturedemo.ui.presenter.MainPresenter
import com.llj.architecturedemo.ui.view.MainContractView
import com.llj.lib.base.BaseTabActivity
import com.llj.lib.base.IUiHandler
import com.llj.lib.image.loader.ImageLoader
import com.llj.lib.net.response.BaseResponse

@Route(path = CRouter.APP_MAIN_ACTIVITY)
class MainActivity : BaseTabActivity<ViewBinding, MainPresenter>(), MainContractView {
  @BindView(R2.id.v_root) lateinit var mVRoot: ViewGroup
  @BindView(R2.id.ll_footer_bar) lateinit var mLlFooterBar: LinearLayout

  private lateinit var mTabAdapter: TabAdapter

  override fun getFragmentId(): Int {
    return R.id.fl_contain
  }

  override fun layoutId(): Int {
    return R.layout.activity_main
  }

  override fun initViews(savedInstanceState: Bundle?) {

    applyNavigationInsets(mVRoot)


    mTabAdapter = UniversalBind.Builder(mLlFooterBar, TabAdapter())
        .build()
        .getAdapter()

    updateTabByLocal()

    super.initViews(savedInstanceState)


  }

  private fun getDefaultTabList(): ArrayList<TabVo> {
    return ArrayList<TabVo>().apply {
      add(TabVo(getString(R.string.tab_home), R.drawable.ic_tab_home_normal, R.drawable.ic_tab_home_selected, TAB_INDEX))
      add(TabVo(getString(R.string.tab_community), R.drawable.ic_tab_baby_show_normal, R.drawable.ic_tab_baby_show_selected, TAB_TONGSHANG))
      add(TabVo("测试", R.drawable.ic_tab_ybs, R.drawable.ic_tab_ybs, TAB_EXPO))
      add(TabVo(getString(R.string.tab_coupon), R.drawable.ic_tab_coupon_normal, R.drawable.ic_tab_coupon_selected, TAB_CASH))
      add(TabVo(getString(R.string.tab_mine), R.drawable.ic_tab_mine_normal, R.drawable.ic_tab_mine_selected, TAB_MY))
    }
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

  private fun jumpFromOut() {

  }


  private inner class TabAdapter : ListBasedAdapter<TabVo, ViewHolderHelper>, IUiHandler {


    private val mImageLoad: ImageLoader

    constructor() : super() {
      this.mImageLoad = ImageLoader.getInstance()
      addItemLayout(R.layout.item_main_activity_tab)
    }


    override fun onBindViewHolder(holder: ViewHolderHelper, item: TabVo?, position: Int) {
      if (item == null) {
        return
      }
      val image = holder.getView<SimpleDraweeView>(R.id.iv_tab_image)
      val text = holder.getView<TextView>(R.id.tv_tab_text)

      //设置图片改变
      if (item.default_img_id != 0) {
        val imageId: Int = if (mShowItem == item.type) item.hover_img_id else item.default_img_id
        mImageLoad.loadImage(image, imageId, 120, 120)
      } else {
        val imageUrl: String? = if (mShowItem == item.type) item.hover_img else item.default_img
        mImageLoad.loadImage(image, imageUrl, 120, 120)
      }

      setText(text, item.title)
      //设置字体颜色的改变
      holder.itemView.isSelected = mShowItem == item.type

      holder.itemView.tag = item.type

      holder.itemView.setOnClickListener {
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

  override fun makeFragment(showItem: String): androidx.fragment.app.Fragment {
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

  override fun getParams1(taskId: Int): java.util.HashMap<String, Any>? {
    return HashMap()
  }

  override fun onDataSuccess1(result: BaseResponse<TabListVo?>, taskId: Int) {
  }

  override fun onDataError(tag: Int, e: Throwable, taskId: Int) {
  }

  override fun onPause() {
    super.onPause()
  }

  override fun getModuleName(): String {
    return CRouter.MODULE_MAIN
  }
}
