package com.llj.architecturedemo.ui.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import butterknife.BindView
import com.facebook.drawee.view.GenericDraweeView
import com.facebook.drawee.view.SimpleDraweeView
import com.github.demono.AutoScrollViewPager
import com.github.demono.adapter.InfinitePagerAdapter
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.MergedUniversalAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.converter.UniversalConverterFactory
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.architecturedemo.ui.model.BabyHomeModuleItemVo
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.model.HomeModelType
import com.llj.architecturedemo.ui.presenter.ScrollableLayoutPresenter
import com.llj.architecturedemo.ui.view.IScrollableLayoutView
import com.llj.component.service.ADMvpBaseFragment
import com.llj.component.service.indicator.ScaleCircleNavigator
import com.llj.component.service.refreshLayout.JHSmartRefreshLayout
import com.llj.component.service.scrollableLayout.ScrollableHelper
import com.llj.component.service.scrollableLayout.ScrollableLayout
import com.llj.component.service.statusbar.LightStatusBarCompat
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
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import java.util.*
import kotlin.collections.ArrayList

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/30
 */
class ScrollableLayoutFragment : ADMvpBaseFragment<ScrollableLayoutPresenter>(), IScrollableLayoutView {
    @BindView(R.id.cv_toolbar) lateinit var mCvToolbar: ConstraintLayout
    @BindView(R.id.refreshLayout) lateinit var mRefreshLayout: JHSmartRefreshLayout
    @BindView(R.id.scrollableLayout) lateinit var mScrollableLayout: ScrollableLayout
    @BindView(R.id.ll_header) lateinit var mLiHeader: LinearLayout
    @BindView(R.id.tab) lateinit var mTab: MagicIndicator
    @BindView(R.id.viewpager) lateinit var mViewpager: ViewPager

    private val mImageLoad: ICustomImageLoader<GenericDraweeView> = FrescoImageLoader.getInstance(Utils.getApp())


    override fun layoutId(): Int {
        return R.layout.fragment_scrollable_layout
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        mRefreshLayout.setOnRefreshListener {
            mPresenter.getWeddingHome(false)
        }.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            fun onHeaderPulling(header: RefreshHeader, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {
                mPresenter.getWeddingHome(false)
            }

            fun onHeaderReleasing(header: RefreshHeader, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {

            }
        }).setEnableLoadMore(false).setEnableOverScrollDrag(true).setEnableOverScrollBounce(false).isEnabled = true

        mScrollableLayout.setOnScrollListener(object : ScrollableLayout.OnScrollListener {
            override fun onScroll(currentY: Int, maxY: Int) {
            }

        })


        mPresenter.getWeddingHome(true)
    }

    override fun initData() {

    }

    override fun getParams(): HashMap<String, Any> {
        return HashMap()
    }

    private fun initTabs() {
        val commonNavigator = CommonNavigator(context)
        commonNavigator.isAdjustMode = false
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return mTabTitleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = mTabTitleList[index]
                simplePagerTitleView.normalColor = getCompatColor(context, R.color.color_CCCCCC)
                simplePagerTitleView.selectedColor = getCompatColor(context, R.color.color_FF6363)
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)
                simplePagerTitleView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                simplePagerTitleView.setPadding(dip2px(mContext, 15f), 0, dip2px(mContext, 15f), 0)

                simplePagerTitleView.setOnClickListener {
                    mViewpager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.lineHeight = dip2px(context, 2.5f).toFloat()
                indicator.roundRadius = dip2px(context, 1f).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(getCompatColor(context, R.color.color_FF6363))
                return indicator
            }
        }
        mTab.navigator = commonNavigator

        ViewPagerHelper.bind(mTab, mViewpager)


        mViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                mScrollableLayout.helper.setCurrentScrollableContainer(mFragments[p0] as ScrollableHelper.ScrollableContainer)
            }

        })
        mScrollableLayout.helper.setCurrentScrollableContainer(mFragments[0] as ScrollableHelper.ScrollableContainer)
        mViewpager.adapter = HomeGoodsPagerAdapter(childFragmentManager, mFragments, mTabTitleList)

        mViewpager.currentItem = 0
    }

    private var mAdapters = MergedUniversalAdapter()
    override fun onDataSuccess(result: BaseResponse<List<BabyHomeModuleVo?>?>) {
        mRefreshLayout.finishRefresh()

        mAdapters = MergedUniversalAdapter()

        val data = result.data
        for (babyHomeModuleVo in data!!) {
            if (babyHomeModuleVo == null || isEmpty(babyHomeModuleVo.block_tmpl)) {
                continue
            }
            switchBabyHomeModuleVo(babyHomeModuleVo)
        }

        initTabs()

        UniversalConverterFactory.createGeneric(mAdapters, mLiHeader)
    }

    private val mTabTitleList = ArrayList<String?>()
    private val mFragments = ArrayList<Fragment>()

    private fun switchBabyHomeModuleVo(babyHomeModuleVo: BabyHomeModuleVo) {
        when (babyHomeModuleVo.block_tmpl) {
            HomeModelType.BANNER //
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val initBannerAdapter = initBannerAdapter(babyHomeModuleVo)

                val arrayListOf = arrayListOf<BabyHomeModuleVo?>()
                arrayListOf.add(babyHomeModuleVo)
                initBannerAdapter.itemsList = arrayListOf

                mAdapters.addAdapter(initBannerAdapter)
            }

            HomeModelType.HOME_ADV_BANNER //广告
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val initAdAdapter = initBannerAdAdapter(babyHomeModuleVo)

                val arrayListOf = arrayListOf<BabyHomeModuleVo?>()
                arrayListOf.add(babyHomeModuleVo)
                initAdAdapter.itemsList = arrayListOf

                mAdapters.addAdapter(initAdAdapter)
            }
            HomeModelType.HOME_BROADCAST //结婚头条
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val initBroadcastAdapter = initBroadcastAdapter(babyHomeModuleVo)

                val arrayListOf = arrayListOf<BabyHomeModuleVo?>()
                arrayListOf.add(babyHomeModuleVo)
                initBroadcastAdapter.itemsList = arrayListOf

                mAdapters.addAdapter(initBroadcastAdapter)
            }


            HomeModelType.HOME_NAV //导航
            -> {
                val spanCount = 5
                if (!isEmpty(babyHomeModuleVo.data) && babyHomeModuleVo.data!!.size >= spanCount) {
                    val myDelegateAdapter = initNavigationAdapter(babyHomeModuleVo)

                    val arrayListOf = arrayListOf<BabyHomeModuleVo?>()
                    arrayListOf.add(babyHomeModuleVo)
                    myDelegateAdapter.itemsList = arrayListOf

                    mAdapters.addAdapter(myDelegateAdapter)
                }
            }
            HomeModelType.HOME_JIEHUN_TOOL //结婚小工具
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                val initBannerAdapter = initJhToolsAdapter(babyHomeModuleVo)

                val arrayListOf = arrayListOf<BabyHomeModuleVo?>()
                arrayListOf.add(babyHomeModuleVo)
                initBannerAdapter.itemsList = arrayListOf

                mAdapters.addAdapter(initBannerAdapter)
            }

            HomeModelType.HOME_ADV //通栏广告
            -> if (!isEmpty(babyHomeModuleVo.data)) {
            }

            HomeModelType.HOME_EXPO //展会模块
            -> if (!isEmpty(babyHomeModuleVo.data)) {
            }

            HomeModelType.COUNT_DOWN //限时抢购
            -> if (!isEmpty(babyHomeModuleVo.data)) {
            }

            HomeModelType.ACTIVITY //推荐活动
            -> if (!isEmpty(babyHomeModuleVo.data)) {
            }

            HomeModelType.GUESS
            -> if (!isEmpty(babyHomeModuleVo.data)) {
                for (i in 0 until babyHomeModuleVo.data!!.size) {
                    val pagerItemData = babyHomeModuleVo.data[i]
                    if (pagerItemData == null) {
                        continue
                    }
                    mTabTitleList.add(pagerItemData.title)
                    mFragments.add(ItemFragment.getInstance())
                }
            }
        }
    }

    override fun onDataError(e: Throwable) {

    }

    private fun initBottomView(babyHomeModuleVo: BabyHomeModuleVo) {

    }

    //banner
    private fun initBannerAdapter(babyHomeModuleVo: BabyHomeModuleVo): MyDelegateAdapter<BabyHomeModuleVo?> {

        return object : MyDelegateAdapter<BabyHomeModuleVo?>(R.layout.item_baby_home_banner, 1) {
            override fun getCount(): Int {
                return 1
            }

            override fun onBindViewHolder(holder: ViewHolderHelper, item: BabyHomeModuleVo?, position: Int) {

                if (item == null || isEmpty(item.data)) {
                    return
                }

                val indicator = holder.getView<MagicIndicator>(R.id.magic_indicator)
                val viewPager = holder.getView<AutoScrollViewPager>(R.id.vp_image)

                //设置banner
                val layoutParams = viewPager.layoutParams as FrameLayout.LayoutParams
                layoutParams.height = ((DisplayHelper.SCREEN_WIDTH_PIXELS - dip2px(mContext, 40f)) * 335 / 670f + dip2px(mContext, 25f)).toInt()
                layoutParams.width = -1
                viewPager.layoutParams = layoutParams
                val imageAdapter = BannerImageAdapter(mContext, babyHomeModuleVo.data!!)


                //设置指示器
                val circleNavigator = ScaleCircleNavigator(mContext)
                circleNavigator.setCircleCount(babyHomeModuleVo.data.size)
                circleNavigator.setMaxRadius(10)
                circleNavigator.setMinRadius(8)
                circleNavigator.setCircleSpacing(16)
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
                if (babyHomeModuleVo.data.size == 1) {
                    viewPager.stopAutoScroll()
                } else {
                    viewPager.startAutoScroll()
                }
            }
        }
    }

    //导航入口
    private fun initNavigationAdapter(babyHomeModuleVo: BabyHomeModuleVo): MyDelegateAdapter<BabyHomeModuleVo?> {
        return object : MyDelegateAdapter<BabyHomeModuleVo?>(R.layout.item_baby_home_gridview2, ViewType.NAVIGATION) {
            override fun onBindViewHolder(holder: ViewHolderHelper, item: BabyHomeModuleVo?, position: Int) {
                if (item == null || isEmpty(item.data)) {
                    return
                }

                val recyclerView = holder.itemView as RecyclerView
                val spanCount = 5
                val h = item.data!!.size / spanCount
                val babyHomeModuleItemVos = item.data.subList(0, h * spanCount)

                val initNavigationItemAdapter = initNavigationItemAdapter(babyHomeModuleItemVos)
                initNavigationItemAdapter.itemsList = babyHomeModuleItemVos
                UniversalBind.Builder(recyclerView, initNavigationItemAdapter)
                        .setGridLayoutManager(4)
                        .build()
            }
        }
    }

    //导航item
    private fun initNavigationItemAdapter(list: List<BabyHomeModuleItemVo?>): MyDelegateAdapter<BabyHomeModuleItemVo?> {
        return object : MyDelegateAdapter<BabyHomeModuleItemVo?>(R.layout.item_baby_home_gridview_item, ViewType.NAVIGATION) {
            override fun onBindViewHolder(holder: ViewHolderHelper, item: BabyHomeModuleItemVo?, position: Int) {
                if (item == null) {
                    return
                }

                val svIcon = holder.getView<SimpleDraweeView>(R.id.sv_icon)
                val tvTitle = holder.getView<TextView>(R.id.tv_title)

                mImageLoad.loadImage(item.img_url, 90, 90, svIcon)
                setText(tvTitle, item.title)
                holder.itemView.setOnClickListener(object : OnMyClickListener() {
                    override fun onCanClick(v: View?) {
                    }

                })
            }
        }
    }


    //banner广告
    private fun initBannerAdAdapter(babyHomeModuleVo: BabyHomeModuleVo): MyDelegateAdapter<BabyHomeModuleVo?> {
        return object : MyDelegateAdapter<BabyHomeModuleVo?>(R.layout.item_baby_home_ad_item, ViewType.BANNER_AD) {
            override fun onBindViewHolder(holder: ViewHolderHelper, item: BabyHomeModuleVo?, position: Int) {
                if (item == null || isEmpty(item.data)) {
                    return
                }

                val view = holder.getView<SimpleDraweeView>(R.id.sv_ad)

                val data = item.data!![0]
                if (data != null) {
                    mImageLoad.loadImage(data.img_url, 90, 90, view)
                }
            }
        }
    }

    //broadcast
    private fun initBroadcastAdapter(babyHomeModuleVo: BabyHomeModuleVo): MyDelegateAdapter<BabyHomeModuleVo?> {
        return object : MyDelegateAdapter<BabyHomeModuleVo?>(R.layout.item_baby_home_brordcast_item, ViewType.BROADCAST) {
            override fun onBindViewHolder(holder: ViewHolderHelper, item: BabyHomeModuleVo?, position: Int) {
                if (item == null || isEmpty(item.data)) {
                    return
                }

                val viewFlipper = holder.getView<ViewFlipper>(R.id.vf_broadcast)

                val data = item.data!!
                for (dataListVo in data) {
                    val textView = TextView(viewFlipper.context)
                    if (dataListVo != null) {
                        textView.text = dataListVo.title
                        textView.setTextColor(getCompatColor(viewFlipper.context, R.color.cl_333333))
                        textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)
                        textView.gravity = Gravity.CENTER_VERTICAL
                        textView.maxLines = 2
                        textView.ellipsize = TextUtils.TruncateAt.END
                        textView.isClickable = true
                    }
                    viewFlipper.addView(textView)
                }
                if (data.size > 1) {
                    viewFlipper.inAnimation = AnimationUtils.loadAnimation(viewFlipper.context, R.anim.push_up_in)
                    viewFlipper.outAnimation = AnimationUtils.loadAnimation(viewFlipper.context, R.anim.push_up_out)
                }
            }
        }
    }

    //结婚小工具
    private fun initJhToolsAdapter(babyHomeModuleVo: BabyHomeModuleVo): MyDelegateAdapter<BabyHomeModuleVo?> {

        return object : MyDelegateAdapter<BabyHomeModuleVo?>(R.layout.item_baby_home_jiehun_tools, ViewType.JIEHUN_TOOLS) {
            override fun onBindViewHolder(holder: ViewHolderHelper, item: BabyHomeModuleVo?, position: Int) {
                if (item == null || isEmpty(item.data)) {
                    return
                }

                val tvTitle = holder.getView<TextView>(R.id.tv_title)
                val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)

                setText(tvTitle, item.block_name)
                val initJhToolsItemAdapter = initJhToolsItemAdapter(item.data!!)
                initJhToolsItemAdapter.itemsList = item.data
                UniversalBind.Builder(recyclerView, initJhToolsItemAdapter)
                        .setGridLayoutManager(4)
                        .build()
            }
        }
    }

    //结婚小工具item
    private fun initJhToolsItemAdapter(list: List<BabyHomeModuleItemVo?>): MyDelegateAdapter<BabyHomeModuleItemVo?> {
        return object : MyDelegateAdapter<BabyHomeModuleItemVo?>(R.layout.item_baby_home_jiehun_tools_item, ViewType.JIEHUN_TOOLS) {

            override fun onBindViewHolder(holder: ViewHolderHelper, item: BabyHomeModuleItemVo?, position: Int) {
                if (item == null) {
                    return
                }
                val svIcon = holder.getView<SimpleDraweeView>(R.id.sv_icon)
                val tvTitle = holder.getView<TextView>(R.id.tv_title)

                mImageLoad.loadImage(item.img_url, 90, 90, svIcon)
                setText(tvTitle, item.title)
                holder.itemView.setOnClickListener(object : OnMyClickListener() {
                    override fun onCanClick(v: View?) {
                    }

                })
            }
        }
    }


    private inner class BannerImageAdapter internal constructor(private val mContext: Context,
                                                                private val mList: ArrayList<BabyHomeModuleItemVo?>) : InfinitePagerAdapter() {

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

    open class MyDelegateAdapter<Item>(private val mLayoutId: Int,
                                       private val mViewTypeItem: Int) : ListBasedAdapter<Item, ViewHolderHelper>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHelper {
            return ViewHolderHelper(inflateView(parent, mLayoutId))
        }

        override fun onBindViewHolder(holder: ViewHolderHelper, item: Item?, position: Int) {
        }

        override fun getItemViewType(position: Int): Int {
            return mViewTypeItem
        }

    }


}
