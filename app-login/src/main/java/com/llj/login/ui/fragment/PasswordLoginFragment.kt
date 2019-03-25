package com.llj.login.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import com.llj.component.service.permission.PermissionManager
import com.llj.component.service.utils.CharInputFilter
import com.llj.component.service.vo.UserInfoVo
import com.llj.lib.base.listeners.MyTextWatcher
import com.llj.lib.base.listeners.OnMyClickListener
import com.llj.lib.utils.ARegexUtils
import com.llj.lib.utils.AToastUtils
import com.llj.login.LoginMvpBaseFragment
import com.llj.login.R
import com.llj.login.R2
import com.llj.login.ui.presenter.PhoneLoginPresenter
import com.llj.login.ui.view.PhoneLoginView
import java.util.*

/**
 * ArchitectureDemo.
 * describe:密码登录
 * author llj
 * date 2018/10/12
 */
class PasswordLoginFragment : LoginMvpBaseFragment<PhoneLoginPresenter>(), PhoneLoginView {

    @BindView(R2.id.et_mobile) lateinit var mEtMobile: EditText
    @BindView(R2.id.et_pwd) lateinit var mEtPwd: EditText
    @BindView(R2.id.btn_login) lateinit var mBtnLogin: Button


    companion object {
        public fun getInstance(): PasswordLoginFragment {
            return PasswordLoginFragment()
        }
    }

    override fun layoutId(): Int {
        return R.layout.login_fragment_password_login
    }

    override fun initViews(savedInstanceState: Bundle?) {
        mBtnLogin.isEnabled = false

        val charInputFilter = CharInputFilter()
        charInputFilter.setFilterModel(CharInputFilter.MODEL_NUMBER)
        charInputFilter.setMaxInputLength(11)
        mEtMobile.filters = arrayOf(charInputFilter)

        val myTextWatcher = object : MyTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mBtnLogin.isEnabled = mEtMobile.text.toString().length == 11 && mEtPwd.text.toString().length == 6
            }
        }
        mEtMobile.addTextChangedListener(myTextWatcher)
        mEtPwd.addTextChangedListener(myTextWatcher)

        mBtnLogin.setOnClickListener(object : OnMyClickListener() {
            override fun onCanClick(v: View?) {
                login()
            }
        })

        setText(mEtMobile, "18767152095")
        setText(mEtPwd, "123456")
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        mPresenter.getMobileInfo("18767152095", true)
    }

    private fun login() {

        if (!ARegexUtils.isMobileSimple(mEtMobile.text.toString())) {
            AToastUtils.show("请输入正确的手机号")
            return
        }
        val hashMap = HashMap<String, Any>()
        hashMap["username"] = mEtMobile.text.toString()
        hashMap["password"] = mEtPwd.text.toString()

        PermissionManager.checkPhoneState(mContext, PermissionManager.PermissionListener {
            mPresenter.accountLogin(hashMap, true)
        })

    }


    override fun onSuccessUserInfo(userInfoVo: UserInfoVo?) {
        if (userInfoVo == null) {
            return
        }

    }
}