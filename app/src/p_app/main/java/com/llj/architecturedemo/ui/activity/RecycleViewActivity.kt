package com.llj.architecturedemo.ui.activity

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.ui.fragment.ItemFragment
import com.llj.architecturedemo.widget.tab.ITab
import com.llj.architecturedemo.widget.tab.ITabs
import com.llj.component.service.arouter.CRouter
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import java.util.*

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/16
 */
@Route(path = CRouter.APP_RECYCLE_VIEW_ACTIVITY)
class RecycleViewActivity : AppMvcBaseActivity() {
    @BindView(R.id.vp_container)
    lateinit var mVpContent: ViewPager

    @BindView(R.id.tabs)
    lateinit var mTabs: MagicIndicator

    override fun layoutId(): Int {
        return R.layout.activity_recycle_view
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    private fun initTabData(result: ITabs) {
        if (isEmpty(result.getTables())) {
            mTabs.visibility = View.GONE
            mVpContent.visibility = View.GONE
            return
        }

        mTabs.visibility = View.VISIBLE
        mVpContent.visibility = View.VISIBLE

        val types = ArrayList<String?>()
        val titles = ArrayList<String?>()

        val tables = result.getTables()

        tables.forEach {
            if (it != null && !isEmpty(it.name)) {
                types.add(it.type)
                titles.add(it.name)
            }
        }

        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.setPadding(dip2px(mContext, 15f), 0, dip2px(mContext, 15f), 0)
                simplePagerTitleView.normalColor = getCompatColor(context, R.color.black)
                simplePagerTitleView.selectedColor = getCompatColor(context, R.color.royalblue)
                simplePagerTitleView.text = titles[index]
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
                simplePagerTitleView.setTypeface(simplePagerTitleView.typeface, Typeface.BOLD)
                simplePagerTitleView.setOnClickListener {
                    mVpContent.post {
                        mVpContent.currentItem = index
                    }
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.lineHeight = dip2px(context, 3f).toFloat()
                indicator.roundRadius = dip2px(context, 1f).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(getCompatColor(context, R.color.royalblue))
                return indicator
            }
        }
        mTabs.navigator = commonNavigator

        mVpContent.offscreenPageLimit = tables.size
        mVpContent.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return types.size
            }

            override fun getItem(position: Int): androidx.fragment.app.Fragment {
                return switchFragment(types[position])
            }
        }
        ViewPagerHelper.bind(mTabs, mVpContent)

        //设置显示的页面
        mVpContent.currentItem = types.indexOf(result.getShowType())
    }


    override fun initData() {
        initTabData(MyTabs())
    }


    private fun switchFragment(type: String?): androidx.fragment.app.Fragment {
        if (isEmpty(type)) {
            return ItemFragment.getInstance()
        }
        return when (type) {
            ITab.SHOW_TYPE_ALBUM -> ItemFragment.getInstance()
            ITab.SHOW_TYPE_PRODUCT -> ItemFragment.getInstance()
            ITab.SHOW_TYPE_STORE -> ItemFragment.getInstance()
            else -> ItemFragment.getInstance()
        }
    }

    inner class MyTabs : ITabs {
        override fun getTables(): List<ITab> {
            val arrayList = arrayListOf<ITab>()

            arrayList.add(MyTab("客照", "album"))
            arrayList.add(MyTab("商品", "product"))
            arrayList.add(MyTab("商家", "store"))

            return arrayList
        }

        override fun getShowType(): String {
            return "album"
        }
    }

    inner class MyTab(override var name: String?, override var type: String?) : ITab
}