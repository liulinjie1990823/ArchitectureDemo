package com.llj.login.ui.fragment

import android.os.Bundle
import com.llj.lib.base.BaseFragment
import com.llj.login.R

/**
 * ArchitectureDemo.
 * describe:密码登录
 * author llj
 * date 2018/10/12
 */
class PasswordLoginFragment : BaseFragment() {
    companion object {
        public fun getInstance(): PasswordLoginFragment {
            return PasswordLoginFragment()
        }
    }

    override fun layoutId(): Int {
        return R.layout.login_fragment_password_login
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }
}