package com.llj.login.ui.fragment

import android.os.Bundle
import com.llj.lib.base.BaseFragment
import com.llj.login.R

/**
 * ArchitectureDemo.
 * describe:验证码登录
 * author llj
 * date 2018/10/12
 */
class CodeLoginFragment : BaseFragment() {
    companion object {
        public fun getInstance(): CodeLoginFragment {
            return CodeLoginFragment()
        }
    }

    override fun layoutId(): Int {
        return R.layout.login_fragment_code_login
    }

    override fun initViews(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }
}
