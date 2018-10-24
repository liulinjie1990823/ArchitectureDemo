package com.llj.architecturedemo.ui.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.facebook.drawee.view.GenericDraweeView
import com.facebook.drawee.view.SimpleDraweeView
import com.github.demono.AutoScrollViewPager
import com.github.demono.adapter.InfinitePagerAdapter
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.architecturedemo.ui.model.BabyHomeModuleItemVo
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.model.HomeModelType
import com.llj.architecturedemo.ui.presenter.VlayoutPresenter
import com.llj.architecturedemo.ui.view.IVlayoutView
import com.llj.component.service.indicator.ScaleCircleNavigator
import com.llj.component.service.refreshLayout.JHSmartRefreshLayout
import com.llj.component.service.statusbar.LightStatusBarCompat
import com.llj.component.service.statusbar.StatusBarCompat
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.base.listeners.OnMyClickListener
import com.llj.lib.image.loader.FrescoImageLoader
import com.llj.lib.image.loader.ICustomImageLoader
import com.llj.lib.net.response.BaseResponse
import com.llj.lib.utils.helper.Utils
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import net.lucode.hackware.magicindicator.MagicIndicator
import java.util.*
import kotlin.collections.ArrayList

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/24
 */
class VlayoutFragment : MvpBaseFragment<VlayoutPresenter>(), IVlayoutView {

    override fun getParams(): HashMap<String, Any> {
        return HashMap()
    }


    override fun onDataSuccess(result: BaseResponse<List<BabyHomeModuleVo?>?>) {
        mRefreshLayout.finishRefresh()
        if (result == null || isEmpty(result.data)) {
            return
        }

        //初始化
        //创建VirtualLayoutManager对象
        val layoutManager = VirtualLayoutManager(mContext)
        mRecyclerView.layoutManager = layoutManager

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        val viewPool = RecyclerView.RecycledViewPool()
        mRecyclerView.setRecycledViewPool(viewPool)
        mRecyclerView.setItemViewCacheSize(2)

        //设置适配器
        val delegateAdapter = DelegateAdapter(layoutManager, true)
        mRecyclerView.adapter = delegateAdapter

        //添加适配器
        mAdapters.clear()
        val data = result.data
        for (babyHomeModuleVo in data!!) {
            if (babyHomeModuleVo == null || isEmpty(babyHomeModuleVo.block_tmpl)) {
                continue
            }
            switchBabyHomeModuleVo(babyHomeModuleVo)
        }
        //设置适配器
        (mRecyclerView.adapter as DelegateAdapter).setAdapters(mAdapters as List<DelegateAdapter.Adapter<RecyclerView.ViewHolder>>?)
    }

    override fun onDataError(e: Throwable) {
        mRefreshLayout.finishRefresh()
    }

    @BindView(R.id.v_status_bar) lateinit var mVStatusBar: View
    @BindView(R.id.tv_city) lateinit var mTvCity: TextView
    @BindView(R.id.v_search_bg) lateinit var mVSearchBg: View
    @BindView(R.id.tv_search) lateinit var mTvSearch: TextView
    @BindView(R.id.cv_header) lateinit var mCvHeader: ConstraintLayout
    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.refresh_layout) lateinit var mRefreshLayout: JHSmartRefreshLayout

    private val mAdapters = ArrayList<DelegateAdapter.Adapter<ViewHolderHelper>>()

    private val mImageLoad: ICustomImageLoader<GenericDraweeView> = FrescoImageLoader.getInstance(Utils.getApp())


    override fun initData() {

    }

    override fun initViews(bundle: Bundle?) {
        StatusBarCompat.translucentStatusBar(mContext as Activity, true)
        LightStatusBarCompat.setLightStatusBar((mContext as Activity).window, false)


        val statusBarHeight = DisplayHelper.STATUS_BAR_HEIGHT
        mVStatusBar.layoutParams.height = statusBarHeight

        mRefreshLayout.setOnRefreshListener {
            mPresenter.getHomeData(false)
        }.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            fun onHeaderPulling(header: RefreshHeader, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {

            }

            fun onHeaderReleasing(header: RefreshHeader, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {

            }
        }).setEnableLoadMore(false).setEnableOverScrollDrag(true).setEnableOverScrollBounce(false)

        mPresenter.getHomeData(true)

    }

    override fun layoutId(): Int {
        return R.layout.fragment_vlayout
    }


    //banner
    private fun initBannerAdapter(list: List<BabyHomeModuleItemVo?>): VlayoutFragment.MyDelegateAdapter {
        val layoutHelper = LinearLayoutHelper()
        return object : VlayoutFragment.MyDelegateAdapter(mContext, layoutHelper, R.layout.item_baby_home_banner, 1, ViewType.BANNER) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val indicator = holder.getView<MagicIndicator>(R.id.magic_indicator)
                val viewPager = holder.getView<AutoScrollViewPager>(R.id.vp_image)

                //设置banner
                val layoutParams = viewPager.layoutParams as FrameLayout.LayoutParams
                layoutParams.height = ((DisplayHelper.SCREEN_WIDTH_PIXELS - dip2px(mContext, 40f)) * 335 / 670f + dip2px(mContext, 25f)).toInt()
                layoutParams.width = -1
                viewPager.layoutParams = layoutParams
                val imageAdapter = BannerImageAdapter(mContext, list)


                //设置指示器
                val circleNavigator = ScaleCircleNavigator(mContext)
                circleNavigator.setCircleCount(list.size)
                circleNavigator.setMaxRadius(10)
                circleNavigator.setMinRadius(10)
                circleNavigator.setNormalCircleColor(Color.parseColor("#4dffffff"))
                circleNavigator.setSelectedCircleColor(Color.WHITE)
                indicator.navigator = circleNavigator
                viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                        indicator.onPageScrolled(imageAdapter.getPosition(position), positionOffset, positionOffsetPixels)
                    }

                    override fun onPageSelected(position: Int) {
                        indicator.onPageSelected(imageAdapter.getPosition(position))
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        indicator.onPageScrollStateChanged(state)
                    }
                })

                viewPager.adapter = imageAdapter

                //开启轮播
                if (list.size == 1) {
                    viewPager.stopAutoScroll()
                } else {
                    viewPager.startAutoScroll()
                }
            }
        }
    }


    //导航入口
    private fun initNavigationAdapter(list: List<BabyHomeModuleItemVo?>): MyDelegateAdapter {

        val gridLayoutHelper = GridLayoutHelper(5)
        gridLayoutHelper.setPadding(dip2px(mContext, 12f), 0, dip2px(mContext, 12f), 0)
        gridLayoutHelper.bgColor = Color.WHITE
        gridLayoutHelper.setAutoExpand(false)

        return object : MyDelegateAdapter(mContext, gridLayoutHelper, R.layout.item_baby_home_gridview, list.size, ViewType.NAVIGATION) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val svIcon = holder.getView<SimpleDraweeView>(R.id.sv_icon)
                val tvTitle = holder.getView<TextView>(R.id.tv_title)

                val data = list[position]
                if (data != null) {
                    mImageLoad.loadImage(data.img_url, 90, 90, svIcon)

                    setText(tvTitle, data.title)
                    holder.itemView.setOnClickListener(object : OnMyClickListener() {
                        override fun onCanClick(v: View?) {
                        }

                    })
                }
            }
        }
    }

    //通栏广告
    private fun initAdAdapter(list: List<BabyHomeModuleItemVo?>): MyDelegateAdapter {
        val layoutHelper = LinearLayoutHelper()
        layoutHelper.bgColor = Color.WHITE

        return object : MyDelegateAdapter(mContext, layoutHelper, R.layout.item_baby_home_ad, 1, ViewType.AD) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val viewPager = holder.getView<AutoScrollViewPager>(R.id.vp_image)

                //设置banner

                val height = ((DisplayHelper.SCREEN_WIDTH_PIXELS - dip2px(mContext, 40f)) * 120 / 670f + dip2px(mContext, 20f)).toInt()
                val width = -1
                viewPager.layoutParams = VirtualLayoutManager.LayoutParams(width, height)
                val imageAdapter = AdImageAdapter(mContext, list)

                viewPager.adapter = imageAdapter

                //开启轮播
                if (list.size == 1) {
                    viewPager.stopAutoScroll()
                } else {
                    viewPager.startAutoScroll()
                }
            }
        }

    }

    //展会模块title
    private fun initExhibitionModuleTitleAdapter(babyHomeModuleVo: BabyHomeModuleVo?): MyDelegateAdapter {
        val layoutHelper = LinearLayoutHelper()
        layoutHelper.setPadding(dip2px(mContext, 20f), dip2px(mContext, 10f), dip2px(mContext, 20f), dip2px(mContext, 10f))
        layoutHelper.bgColor = Color.WHITE
        return object : MyDelegateAdapter(mContext, layoutHelper, R.layout.item_baby_home_exhibition_module_title, 1, ViewType.EXHIBITION_TITLE) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val title = holder.getView<TextView>(R.id.tv_exhibition_module_title)
                val subTitle = holder.getView<TextView>(R.id.tv_exhibition_module_sub_title)

                if (babyHomeModuleVo != null) {
                    setText(title, babyHomeModuleVo.block_name)
                    setText(subTitle, babyHomeModuleVo.block_desc)
                }
            }
        }
    }

    //展会模块
    private fun initExhibitionModuleAdapter(list: List<BabyHomeModuleItemVo?>): MyDelegateAdapter {
        val gridLayoutHelper = GridLayoutHelper(2)
        gridLayoutHelper.setPadding(dip2px(mContext, 15f), 0, dip2px(mContext, 15f), 0)
        gridLayoutHelper.bgColor = Color.WHITE

        return object : MyDelegateAdapter(mContext, gridLayoutHelper, R.layout.item_baby_home_exhibition_module, list.size, ViewType.EXHIBITION) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val svImage = holder.getView<SimpleDraweeView>(R.id.sv_image)
                val title = holder.getView<TextView>(R.id.tv_title)
                val subTitle = holder.getView<TextView>(R.id.tv_sub_title)

                val data = list[position]
                if (data != null) {
                    mImageLoad.loadImage(data.img_url, 110, 110, svImage)

                    setText(title, data.title)
                    setText(subTitle, data.title)
                    holder.itemView.setOnClickListener(object : OnMyClickListener() {
                        override fun onCanClick(v: View) {
                            //                            CiwHelper.startActivity(data.getLink())
                        }
                    })
                }
            }
        }
    }

    //限时抢购title
    private fun initLimitedTimeTitleAdapter(babyHomeModuleVo: BabyHomeModuleVo?): MyDelegateAdapter {
        val layoutHelper = LinearLayoutHelper()
        layoutHelper.setPadding(dip2px(mContext, 20f), dip2px(mContext, 15f), dip2px(mContext, 20f), dip2px(mContext, 15f))
        layoutHelper.bgColor = Color.WHITE
        return object : MyDelegateAdapter(mContext, layoutHelper, R.layout.item_baby_home_limited_time_title, 1, ViewType.LIMITED_TIME_TITLE) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val title = holder.getView<TextView>(R.id.tv_title)
                if (babyHomeModuleVo != null) {
                    setText(title, babyHomeModuleVo.block_name)
                }
            }
        }
    }

    //限时抢购
    private fun initLimitedTimeAdapter(list: List<BabyHomeModuleItemVo?>): MyDelegateAdapter {
        val layoutHelper = LinearLayoutHelper()
        layoutHelper.bgColor = Color.WHITE
        layoutHelper.setDividerHeight(dip2px(mContext, 10f))
        layoutHelper.setPadding(dip2px(mContext, 20f), 0, dip2px(mContext, 20f), 0)
        return object : MyDelegateAdapter(mContext, layoutHelper, R.layout.item_baby_home_limited_time, list.size, ViewType.LIMITED_TIME) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val svImage = holder.getView<SimpleDraweeView>(R.id.sv_image)
                val title = holder.getView<TextView>(R.id.tv_title)
                val subTitle = holder.getView<TextView>(R.id.tv_sub_title)

                val data = list[position]
                if (data != null) {

                    mImageLoad.loadImage(data.img_url, 220, 220, svImage)

                    setText(title, data.title)
                    setText(subTitle, data.title)
                    holder.itemView.setOnClickListener(object : OnMyClickListener() {
                        override fun onCanClick(v: View?) {
                        }
                    })
                }
            }
        }
    }

    //推荐活动title
    private fun initRecommendedActivityTitleAdapter(babyHomeModuleVo: BabyHomeModuleVo?): MyDelegateAdapter {
        val layoutHelper = LinearLayoutHelper()
        layoutHelper.setPadding(dip2px(mContext, 20f), dip2px(mContext, 15f), dip2px(mContext, 20f), dip2px(mContext, 15f))
        layoutHelper.bgColor = Color.WHITE
        return object : MyDelegateAdapter(mContext, layoutHelper, R.layout.item_baby_home_recommended_activity_title, 1, ViewType.RECOMMENDED_ACTIVITY_TITLE) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val title = holder.getView<TextView>(R.id.tv_title)
                if (babyHomeModuleVo != null) {
                    setText(title, babyHomeModuleVo.block_name)
                }
            }
        }
    }

    //推荐活动
    private fun initRecommendedActivityAdapter(list: List<BabyHomeModuleItemVo?>): MyDelegateAdapter {
        val layoutHelper = LinearLayoutHelper()
        layoutHelper.setPadding(dip2px(mContext, 20f), 0, dip2px(mContext, 20f), 0)
        layoutHelper.bgColor = Color.WHITE
        return object : MyDelegateAdapter(mContext, layoutHelper, R.layout.item_baby_home_recommended_activity, list.size, ViewType.RECOMMENDED_ACTIVITY) {

            override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
                super.onBindViewHolder(holder, position)
                val svImage = holder.getView<SimpleDraweeView>(R.id.sv_image)
                val title = holder.getView<TextView>(R.id.tv_title)
                holder.itemView.setPadding(0, if (position == 0) 0 else dip2px(mContext, 20f), 0, 0)

                val data = list[position]
                if (data != null) {
                    mImageLoad.loadImage(data.img_url, 750, 750, svImage)

                    setText(title, data.title)
                    holder.itemView.setOnClickListener(object : OnMyClickListener() {
                        override fun onCanClick(v: View?) {
                        }
                    })
                }
            }
        }
    }


    private fun switchBabyHomeModuleVo(babyHomeModuleVo: BabyHomeModuleVo) {
        when (babyHomeModuleVo.block_tmpl) {
            HomeModelType.BANNER //
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val myDelegateAdapter = initBannerAdapter(babyHomeModuleVo.data!!)
                mAdapters.add(myDelegateAdapter)
            }
            HomeModelType.HOME_NAV //导航
            -> {
                val spanCount = 5
                if (!isEmpty(babyHomeModuleVo.data) && babyHomeModuleVo.data!!.size >= spanCount) {
                    val h = babyHomeModuleVo.data.size / spanCount

                    val babyHomeModuleItemVos = babyHomeModuleVo.data.subList(0, h * spanCount)
                    val myDelegateAdapter = initNavigationAdapter(babyHomeModuleItemVos)
                    mAdapters.add(myDelegateAdapter)
                }
            }

            HomeModelType.HOME_ADV //通栏广告
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val myDelegateAdapter = initAdAdapter(babyHomeModuleVo.data!!)
                mAdapters.add(myDelegateAdapter)
            }

            HomeModelType.HOME_EXPO //展会模块
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val myDelegateAdapter1 = initExhibitionModuleTitleAdapter(babyHomeModuleVo)
                val myDelegateAdapter2 = initExhibitionModuleAdapter(babyHomeModuleVo.data!!)
                mAdapters.add(myDelegateAdapter1)
                mAdapters.add(myDelegateAdapter2)
            }

            HomeModelType.COUNT_DOWN //限时抢购
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val myDelegateAdapter1 = initLimitedTimeTitleAdapter(babyHomeModuleVo)
                val myDelegateAdapter2 = initLimitedTimeAdapter(babyHomeModuleVo.data!!)
                mAdapters.add(myDelegateAdapter1)
                mAdapters.add(myDelegateAdapter2)
            }

            HomeModelType.ACTIVITY //推荐活动
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val myDelegateAdapter1 = initRecommendedActivityTitleAdapter(babyHomeModuleVo)
                val myDelegateAdapter2 = initRecommendedActivityAdapter(babyHomeModuleVo.data!!)
                mAdapters.add(myDelegateAdapter1)
                mAdapters.add(myDelegateAdapter2)
            }
        }
    }

    private val REQUEST_CODE_GET_CITY = 1

    @OnClick(R.id.tv_city, R.id.v_search_bg)
    fun onViewClicked(view: View) {
        //        if (!clickEnable()) {
        //            return
        //        }
        //        when (view.id) {
        //            R.id.tv_city -> {
        //                val intent = Intent(activity, CitySelectListActivity::class.java)
        //                startActivityForResult(intent, REQUEST_CODE_GET_CITY)
        //                activity.overridePendingTransition(R.anim.city_list_activity_open, 0)
        //            }
        //            R.id.v_search_bg -> JHRoute.start(JHRoute.APP_COMMON_SEARCH_BEFORE_ACTIVITY)
        //        }
    }


    //    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    //        super.onActivityResult(requestCode, resultCode, data)
    //        if (requestCode == REQUEST_CODE_GET_CITY && resultCode == RESULT_OK) {
    //            var selectCityName: String? = data.getStringExtra("selectCityName")
    //            if (selectCityName != null && !selectCityName.contains(mTvCity.text.toString())) {
    //                //设置城市
    //                selectCityName = if (selectCityName.endsWith("市")) selectCityName.substring(0, selectCityName.length - 1) else selectCityName
    //                mTvCity.text = selectCityName
    //
    //                //刷新数据
    //                mRefreshLayout.autoRefresh()
    //
    //                //发送城市变更消息
    //                val response = BaseResponse()
    //                response.setCmd(EventConstants.EVENT_SELECTCITY_SUCCESS)
    //                EventBus.getDefault().post(response)
    //            }
    //        }
    //    }


    private inner class BannerImageAdapter internal constructor(private val mContext: Context,
                                                                private val mList: List<BabyHomeModuleItemVo?>)
        : InfinitePagerAdapter() {

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun getItemView(i: Int, view: View?, viewGroup: ViewGroup?): View {
            val inflate = View.inflate(mContext, R.layout.item_baby_home_banner_item, null)

            val banner = inflate.findViewById<SimpleDraweeView>(R.id.sv_banner)
            banner.layoutParams.width = -1
            banner.layoutParams.height = ((DisplayHelper.SCREEN_WIDTH_PIXELS - dip2px(mContext, 40f)) * 335 / 670f).toInt()

            val data = mList[i]
            if (data != null) {
                mImageLoad.loadImage(data.img_url, 750, 750, banner)
            }

            return inflate
        }
    }

    private inner class AdImageAdapter internal constructor(private val mContext: Context,
                                                            private val mList: List<BabyHomeModuleItemVo?>)
        : InfinitePagerAdapter() {

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun getItemView(i: Int, view: View?, viewGroup: ViewGroup?): View {
            val inflate = LayoutInflater.from(mContext).inflate(R.layout.item_baby_home_ad_item, null)
            val svAd = inflate.findViewById<SimpleDraweeView>(R.id.sv_ad)

            val data = mList[i]
            if (data != null) {

                mImageLoad.loadImage(data.img_url, 750, 750, svAd)
                svAd.setOnClickListener(object : OnMyClickListener() {
                    override fun onCanClick(v: View?) {
                    }
                })
            }
            return inflate
        }
    }

    interface ViewType {
        companion object {
            val BANNER = 1
            val NAVIGATION = 2
            val AD = 3
            val EXHIBITION = 4
            val EXHIBITION_TITLE = 5
            val LIMITED_TIME_TITLE = 6
            val LIMITED_TIME = 7
            val RECOMMENDED_ACTIVITY_TITLE = 8
            val RECOMMENDED_ACTIVITY = 9
        }
    }

    open class MyDelegateAdapter(private val mContext: Context,
                                 private val mLayoutHelper: LayoutHelper,
                                 private val mLayoutId: Int,
                                 private val mCount: Int,
                                 private val mViewTypeItem: Int) : DelegateAdapter.Adapter<ViewHolderHelper>() {

        override fun onCreateLayoutHelper(): LayoutHelper {
            return mLayoutHelper
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHelper {
            return ViewHolderHelper(LayoutInflater.from(mContext).inflate(mLayoutId, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {

        }

        override fun getItemViewType(position: Int): Int {
            return mViewTypeItem
        }

        override fun getItemCount(): Int {
            return mCount
        }
    }


}
