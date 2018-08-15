package com.llj.architecturedemo.ui.fragment

import android.os.Bundle
import butterknife.BindView
import com.facebook.drawee.view.SimpleDraweeView
import com.llj.architecturedemo.R
import com.llj.lib.base.BaseFragment
import com.llj.lib.image.loader.FrescoImageLoader

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
class HomeFragment : BaseFragment() {
    @BindView(R.id.iv_image) lateinit var mSvImage: SimpleDraweeView

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViews(savedInstanceState: Bundle?) {
        FrescoImageLoader.getInstance(mContext.applicationContext).loadImage("http://pic7.photophoto.cn/20080407/0034034859692813_b.jpg", 300, 300, mSvImage)
    }

    override fun initData() {

    }
}
