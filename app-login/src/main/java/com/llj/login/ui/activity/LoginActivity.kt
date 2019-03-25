package com.llj.login.ui.activity

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.TypedValue
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.component.service.ComponentMvcBaseActivity
import com.llj.component.service.arouter.CRouter
import com.llj.login.R
import com.llj.login.ui.fragment.CodeLoginFragmentMvc
import com.llj.login.ui.fragment.PasswordLoginFragment
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import java.util.*

/**
 * ArchitectureDemo.
 * describe:登录界面
 * author llj
 * date 2018/8/22
 */
@Route(path = CRouter.LOGIN_LOGIN_ACTIVITY)
class LoginActivity : ComponentMvcBaseActivity() {
    @BindView(com.llj.login.R2.id.login_tabs) lateinit var mTabs: MagicIndicator
    @BindView(com.llj.login.R2.id.login_viewpager) lateinit var mViewPager: ViewPager

    override fun layoutId(): Int {
        return R.layout.login_activity_login
    }

    override fun initViews(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        initTab()
    }


    private fun initTab() {
        val titles = ArrayList<String>()
        titles.add("验证码登录")
        titles.add("密码登录")

        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = ColorTransitionPagerTitleView(context)
                simplePagerTitleView.setPadding(dip2px(context, 15f), 0, dip2px(context, 15f), 0)
                simplePagerTitleView.normalColor = getCompatColor(context, R.color.color_8C959F)
                simplePagerTitleView.selectedColor = getCompatColor(context, R.color.black)
                simplePagerTitleView.text = titles[index]
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17f)
                simplePagerTitleView.setTypeface(simplePagerTitleView.typeface, Typeface.BOLD)
                simplePagerTitleView.setOnClickListener {
                    mViewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        }
        mTabs.navigator = commonNavigator

        mViewPager.offscreenPageLimit = 2
        mViewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return titles.size
            }

            override fun getItem(position: Int): Fragment? {

                when (position) {
                    0 -> return CodeLoginFragmentMvc.getInstance()
                    1 -> return PasswordLoginFragment.getInstance()
                }
                return null
            }
        }
        ViewPagerHelper.bind(mTabs, mViewPager)

        mViewPager.currentItem = 0
    }
}