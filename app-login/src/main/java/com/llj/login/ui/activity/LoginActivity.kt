package com.llj.login.ui.activity

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.application.router.CJump
import com.llj.application.router.CRouter
import com.llj.lib.base.AppManager
import com.llj.lib.jump.annotation.Jump
import com.llj.login.LoginMvcBaseActivity
import com.llj.login.R
import com.llj.login.databinding.LoginActivityLoginBinding
import com.llj.login.ui.fragment.CodeLoginFragmentMvc
import com.llj.login.ui.fragment.PasswordLoginFragment
import com.llj.socialization.login.LoginPlatformType
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
 * @author llj
 * @date 2018/8/22
 */
@Jump(outPath = CJump.JUMP_LOGIN_ACTIVITY, inPath = CRouter.LOGIN_LOGIN_ACTIVITY, needLogin = true, desc = "LoginActivity")
@Route(path = CRouter.LOGIN_LOGIN_ACTIVITY)
class LoginActivity : LoginMvcBaseActivity<LoginActivityLoginBinding>() {

  @Autowired(name = CRouter.AROUTER_FORWARD_PATH)
  lateinit var mForwardPath: String

  override fun layoutId(): Int {
    return R.layout.login_activity_login
  }

  override fun initViews(savedInstanceState: Bundle?) {
    ARouter.getInstance().inject(this)
  }

  override fun initData() {
    initList()
    initTab()
  }

  private fun initList() {
    val arrayList = arrayListOf<Data?>()
    arrayList.add(Data(R.drawable.def_user_header, "qq", LoginPlatformType.QQ))
    arrayList.add(Data(R.drawable.def_user_header, "weixin", LoginPlatformType.WECHAT))
    arrayList.add(Data(R.drawable.def_user_header, "sina", LoginPlatformType.SINA))

    UniversalBind.Builder(mViewBinder.rvLogin, MyAdapter(arrayList))
        .setLinearLayoutManager(androidx.recyclerview.widget.RecyclerView.HORIZONTAL)
        .build()
        .getAdapter()
  }


  private inner class MyAdapter(list: MutableList<Data?>) : ListBasedAdapter<Data, ViewHolderHelper>(list) {
    init {
      addItemLayout(R.layout.login_item_third_login)
    }

    override fun onBindViewHolder(viewHolder: ViewHolderHelper, item: Data?, position: Int) {
      if (item == null) {
        return
      }

      val imageView = viewHolder.getView<ImageView>(R.id.iv_login)
      val textView = viewHolder.getView<TextView>(R.id.tv_login)
      imageView?.setImageResource(item.resId)
      setText(textView, position.toString() + "  " + item.text)

      viewHolder.itemView.setOnClickListener {

        finish()
        AppManager.getInstance().userInfoConfig.isLogin = true
        ARouter.getInstance().build(mForwardPath)
            .navigation()

        //                LoginUtil.login(mContext, item.platform, object : LoginListener() {
        //                    override fun onLoginResponse(result: LoginResult?) {
        //                        LogUtil.LLJi(result.toString())
        //                    }
        //                }, true)
      }
    }
  }

  private inner class Data(var resId: Int, var text: String, var platform: Int)

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
          mViewBinder!!.loginViewpager.currentItem = index
        }
        return simplePagerTitleView
      }

      override fun getIndicator(context: Context): IPagerIndicator? {
        return null
      }
    }
    mViewBinder!!.loginTabs.navigator = commonNavigator

    mViewBinder!!.loginViewpager.offscreenPageLimit = 2
    mViewBinder!!.loginViewpager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager) {
      override fun getCount(): Int {
        return titles.size
      }

      override fun getItem(position: Int): Fragment {

        when (position) {
          0 -> return CodeLoginFragmentMvc.getInstance()
          1 -> return PasswordLoginFragment.getInstance()
        }
        return CodeLoginFragmentMvc.getInstance()
      }
    }
    ViewPagerHelper.bind(mViewBinder!!.loginTabs, mViewBinder!!.loginViewpager)

    mViewBinder!!.loginViewpager.currentItem = 0
  }
}