package com.llj.login.ui.fragment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import com.llj.component.service.utils.CharInputFilter
import com.llj.lib.base.MvcBaseFragment
import com.llj.lib.base.listeners.SimpleTextWatcher
import com.llj.login.R
import com.llj.login.R2

/**
 * ArchitectureDemo.
 * describe:验证码登录
 * author llj
 * date 2018/10/12
 */
class CodeLoginFragmentMvc : MvcBaseFragment() {
    @BindView(R2.id.et_mobile) lateinit var mEtMobile: EditText
    @BindView(R2.id.et_code) lateinit var mEtCode: EditText
    @BindView(R2.id.tv_get_code) lateinit var mTvGetCode: TextView
    @BindView(R2.id.btn_login) lateinit var mBtnLogin: Button

    companion object {
        public fun getInstance(): CodeLoginFragmentMvc {
            return CodeLoginFragmentMvc()
        }
    }

    override fun layoutId(): Int {
        return R.layout.login_fragment_code_login
    }

    override fun initViews(savedInstanceState: Bundle?) {
        mBtnLogin.isEnabled = false

        val charInputFilter = CharInputFilter()
        charInputFilter.setFilterModel(CharInputFilter.MODEL_NUMBER)
        charInputFilter.setMaxInputLength(11)
        mEtMobile.filters = arrayOf(charInputFilter)

        charInputFilter.setMaxInputLength(4)
        mEtCode.filters = arrayOf(charInputFilter)

        val myTextWatcher = object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mBtnLogin.isEnabled = mEtMobile.text.toString().length == 11 && mEtCode.text.toString().length == 4
            }
        }
        mEtMobile.addTextChangedListener(myTextWatcher)
        mEtCode.addTextChangedListener(myTextWatcher)
    }

    override fun initData() {

    }
}
