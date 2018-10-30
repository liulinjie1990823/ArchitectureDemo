package com.llj.architecturedemo.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import butterknife.BindView
import com.facebook.drawee.view.GenericDraweeView
import com.facebook.drawee.view.SimpleDraweeView
import com.github.demono.AutoScrollViewPager
import com.github.demono.adapter.InfinitePagerAdapter
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.architecturedemo.ui.model.BabyHomeModuleItemVo
import com.llj.architecturedemo.ui.model.BabyHomeModuleVo
import com.llj.architecturedemo.ui.presenter.ScrollableLayoutPresenter
import com.llj.architecturedemo.ui.view.IScrollableLayoutView
import com.llj.component.service.indicator.ScaleCircleNavigator
import com.llj.component.service.refreshLayout.JHSmartRefreshLayout
import com.llj.component.service.scrollableLayout.ScrollableLayout
import com.llj.lib.base.MvpBaseFragment
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.image.loader.FrescoImageLoader
import com.llj.lib.image.loader.ICustomImageLoader
import com.llj.lib.net.response.BaseResponse
import com.llj.lib.utils.helper.Utils
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import net.lucode.hackware.magicindicator.MagicIndicator
import java.util.*

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/10/30
 */
class ScrollableLayoutFragment : MvpBaseFragment<ScrollableLayoutPresenter>(), IScrollableLayoutView {
    @BindView(R.id.v_status_bar) lateinit var mVStatusBar: View
    @BindView(R.id.cv_toolbar) lateinit var mCvToolbar: ConstraintLayout
    @BindView(R.id.refreshLayout) lateinit var mRefreshLayout: JHSmartRefreshLayout
    @BindView(R.id.scrollableLayout) lateinit var mscrollableLayout: ScrollableLayout
    @BindView(R.id.ll_header) lateinit var mLiHeader: LinearLayout
    @BindView(R.id.tab) lateinit var mTab: MagicIndicator
    @BindView(R.id.viewpager) lateinit var mViewpager: ViewPager

    private val mImageLoad: ICustomImageLoader<GenericDraweeView> = FrescoImageLoader.getInstance(Utils.getApp())

    override fun layoutId(): Int {
        return R.layout.fragment_scrollable_layout
    }

    override fun initViews(savedInstanceState: Bundle?) {

        val statusBarHeight = DisplayHelper.STATUS_BAR_HEIGHT
        mVStatusBar.layoutParams.height = statusBarHeight

        mRefreshLayout.setOnRefreshListener {
            mPresenter.getWeddingHome(false)
        }.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            fun onHeaderPulling(header: RefreshHeader, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {

            }

            fun onHeaderReleasing(header: RefreshHeader, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {

            }
        }).setEnableLoadMore(false).setEnableOverScrollDrag(true).setEnableOverScrollBounce(false)

        mPresenter.getWeddingHome(true)
    }

    override fun initData() {

    }

    override fun getParams(): HashMap<String, Any> {
        return HashMap()
    }

    override fun onDataSuccess(result: BaseResponse<List<BabyHomeModuleVo?>?>) {

    }

    override fun onDataError(e: Throwable) {

    }

    //banner
    private fun initBannerAdapter(list: List<BabyHomeModuleItemVo?>): MyDelegateAdapter<BabyHomeModuleItemVo?> {

        return object : MyDelegateAdapter<BabyHomeModuleItemVo?>(R.layout.item_baby_home_banner, 1) {

            override fun onBindViewHolder(holder: ViewHolderHelper, item: BabyHomeModuleItemVo?, position: Int) {

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
                if (list.size == 1) {
                    viewPager.stopAutoScroll()
                } else {
                    viewPager.startAutoScroll()
                }
            }
        }
    }

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
