package com.llj.login.ui.presenter

import com.llj.lib.base.mvp.BasePresenter
import com.llj.lib.net.RxApiManager
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import com.llj.login.ui.model.UserInfoVo
import com.llj.login.ui.repository.LoginRepository
import com.llj.login.ui.view.PhoneLoginView
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.util.*
import javax.inject.Inject

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/18
 */
class PhoneLoginPresenter @Inject constructor(mRepository: LoginRepository, mView: PhoneLoginView)
    : BasePresenter<LoginRepository, PhoneLoginView>(mRepository, mView) {


    //手机登录
    fun phoneLogin(map: HashMap<String, Any>, showLoading: Boolean) {
        //Single
        var phoneLogin = mRepository?.phoneLogin(map)
        if (showLoading) {
            phoneLogin = phoneLogin?.doOnSubscribe(mView)?.doFinally(mView)
        }
        if (phoneLogin != null) {
            subscribeLogin(phoneLogin)
        }
    }

    //账号登录
    fun accountLogin(map: HashMap<String, Any>, showLoading: Boolean) {
        //Single
        var phoneLogin = mRepository?.accountLogin(map)
        if (showLoading) {
            phoneLogin = phoneLogin?.doOnSubscribe(mView)?.doFinally(mView)
        }
        if (phoneLogin != null) {
            subscribeLogin(phoneLogin)
        }
    }

    private fun subscribeLogin(single: Single<Response<BaseResponse<UserInfoVo>>>) {
        //Observer
        val baseApiObserver = object : BaseApiObserver<UserInfoVo>(mView.getRequestTag()) {

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                //将请求添加到请求map中
                mView.addDisposable(getRequestTag(), d)
            }

            override fun onSuccess(response: BaseResponse<UserInfoVo>) {
                super.onSuccess(response)
                mView.onSuccessUserInfo(response.data)

            }

            override fun onError(t: Throwable) {
                super.onError(t)
            }
        }

        //subscribe
        RxApiManager.get().subscribeApi<UserInfoVo>(
                single,
                mView.bindRequestLifecycle(),
                baseApiObserver)
    }


}