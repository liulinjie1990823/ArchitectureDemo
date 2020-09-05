package com.llj.architecturedemo.ui.service

import android.content.Intent
import com.llj.application.preference.ConfigPreference
import com.llj.architecturedemo.ui.model.TabListVo
import com.llj.architecturedemo.ui.presenter.PreLoadingPresenter
import com.llj.architecturedemo.ui.view.IPreLoadingView
import com.llj.lib.base.MvpBaseIntentService
import com.llj.lib.net.response.BaseResponse
import com.llj.lib.utils.AGsonUtils
import com.llj.lib.utils.ATimeUtils
import dagger.android.AndroidInjection

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/7
 */
class LoadingService(name: String) : MvpBaseIntentService<PreLoadingPresenter>(name), IPreLoadingView {

    override fun getParams(): HashMap<String, Any> {
        return HashMap()
    }

    override fun onDataSuccess(result: BaseResponse<TabListVo?>) {
        val data: TabListVo? = result.data
        if (data == null || isEmpty(data.tabbar)) {
            return
        }
        //保存到本地
        ConfigPreference.getInstance().saveTabList(AGsonUtils.toJson(data.tabbar!!))

    }

    override fun onDataError(e: Throwable) {

    }

    init {
        AndroidInjection.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        val tabListUpdateDate = ConfigPreference.getInstance().getTabListUpdateDate()
        val millisecondsToString = ATimeUtils.millisecondsToString(ATimeUtils.FORMAT_EIGHT, System.currentTimeMillis())
        if (tabListUpdateDate != millisecondsToString) {
            mPresenter.getTabBar()
        }
    }
}
