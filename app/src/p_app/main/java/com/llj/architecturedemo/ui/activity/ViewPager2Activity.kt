package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelperWrap
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivityViewpager2Binding
import com.llj.architecturedemo.databinding.MvItemAeMakePicBinding
import com.llj.application.router.CRouter
import com.llj.lib.base.help.DisplayHelper
import com.llj.lib.image.loader.ImageLoader
import timber.log.Timber

/**
 * describe ViewPager2
 *
 * @author liulinjie
 * @date 2020/3/22 1:39 PM
 */
@Route(path = CRouter.APP_VIEWPAGER2_ACTIVITY)
class ViewPager2Activity : MainMvcBaseActivity<ActivityViewpager2Binding>() {

  private var mPicWidth = 0
  private var mPicHeight = 0
  private lateinit var mImageLoader: ImageLoader


  override fun initViews(savedInstanceState: Bundle?) {
    mImageLoader = ImageLoader.getInstance()

    mViewBinder.vpPicture1.tag = "vpPicture1"
    mViewBinder.vpPicture2.tag = "vpPicture2"
    mViewBinder.vpPicture3.tag = "vpPicture3"

    mViewBinder.vpPicture1.setPadding(dip2px(mContext, 40f), 0, dip2px(mContext, 40f), 0)
    mViewBinder.vpPicture1.offscreenPageLimit = 1
    mViewBinder.vpPicture1.pageMargin = dip2px(mContext, 20f)

    //画面的宽度，按比例计算
    mPicWidth = (DisplayHelper.SCREEN_WIDTH * 348 / 750f).toInt()
    mPicHeight = (DisplayHelper.SCREEN_WIDTH * 348 * 16 / (750 * 9f)).toInt()

    //计算padding
    val left = ((DisplayHelper.SCREEN_WIDTH - mPicWidth) / 2f).toInt()
    val top = dip2px(mContext, 20f)
    //因为事件都在RecycleView，所以设置RecycleView的padding，可以保持RecycleView的宽度全屏
//    mViewBinder.vpPicture.getChildAt(0).setPadding(left, top, left, top)
    //让RecycleView显示出下一个view
    (mViewBinder.vpPicture2.getChildAt(0) as ViewGroup).clipToPadding = false

    //ViewPager2设置LayoutParams
//    val layoutParams = mViewBinder.vpPicture
//        .layoutParams as ConstraintLayout.LayoutParams
//    layoutParams.width = -1
//    layoutParams.height = mPicHeight + mViewBinder.vpPicture.paddingTop + mViewBinder.vpPicture
//        .paddingBottom

    mViewBinder.vpPicture2.offscreenPageLimit = 3
    val pageTransformer = object : ViewPager2.PageTransformer {
      val MIN_SCALE = 0.9f
      val MIN_ALPHA = 0.65f

      //final float transformPos = (float) (child.getLeft() - scrollX) / getClientWidth();
      private val mCenterPosition = mViewBinder.vpPicture2.paddingLeft / (mPicWidth * 1f)
      private val mMarginPx = 60
      override fun transformPage(page: View, position: Float) {
        Timber.tag(mTagLog).e("position:$position")
        val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(mCenterPosition - position))
        val alphaFactor = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - Math.abs(mCenterPosition - position))

        //View view = page.findViewById(R.id.v_cover);
        page.scaleX = scaleFactor
        page.scaleY = scaleFactor

        //page.setAlpha(1 - alphaFactor);
        val offset = mMarginPx * position
        page.translationX = offset
      }
    }

    //mViewBinder.vpPicture.setPageTransformer(pageTransformer)


    mViewBinder.vpPicture3.setPadding(dip2px(mContext, 40f), 0, dip2px(mContext, 40f), 0)
    (mViewBinder.vpPicture3.getChildAt(0) as ViewGroup).clipToPadding = false
    mViewBinder.vpPicture3.offscreenPageLimit = 1
    mViewBinder.vpPicture3.setPageTransformer(MarginPageTransformer(dip2px(mContext, 20f)))

    UniversalBind.Builder(mViewBinder.vpPicture1, ItemAdapter(mViewBinder.vpPicture1))
        .build().getAdapter()

    UniversalBind.Builder(mViewBinder.vpPicture2, ItemAdapter(mViewBinder.vpPicture2))
        .build().getAdapter()

    UniversalBind.Builder(mViewBinder.vpPicture3, ItemAdapter(mViewBinder.vpPicture3))
        .build().getAdapter()
  }

  override fun initData() {
  }

  inner class ItemAdapter : ListBasedAdapter<String?, ViewHolderHelperWrap<MvItemAeMakePicBinding>> {
    private val viewGroup: ViewGroup

    constructor(viewGroup: ViewGroup) : super() {
      this.viewGroup = viewGroup
    }

    override fun getCount(): Int {
      return 10
    }

    override fun onCreateViewBinding(parent: ViewGroup, viewType: Int): ViewBinding? {
      return MvItemAeMakePicBinding.inflate(layoutInflater, viewGroup, false)
    }


    override fun onBindViewHolder(holder: ViewHolderHelperWrap<MvItemAeMakePicBinding>, item: String?, position: Int) {

      Timber.tag(mTagLog).i(" onBindViewHolder:%s,view:%s,position:%d",
          holder.hashCode(), viewGroup.tag, position)

      val viewBinder = holder.mViewBinder
      val url = "https://img.hbhcdn.com/dmp/other/1584979200/jh-img-orig-ga_1242381413835505664_1079_1918_225146.jpg"
      mImageLoader.loadImage(viewBinder.sdvImage, url, mPicWidth, mPicHeight)
    }
  }
}