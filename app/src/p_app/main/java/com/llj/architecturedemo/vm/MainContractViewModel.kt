package com.llj.architecturedemo.vm

import androidx.lifecycle.LiveData

import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.lib.base.mvp.IBaseActivityView
import com.llj.lib.base.mvvm.BaseViewModel
import com.llj.lib.net.response.IResponse

import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/29
 */
class MainContractViewModel @Inject constructor(private val mMobileRepository: MobileRepository)
    : BaseViewModel() {

    private val test: LiveData<MobileEntity>? = null


    val result: LiveData<String>
        get() = mMobileRepository.getResult()

    fun getTest(): LiveData<MobileEntity> {
        return mMobileRepository.getTest()
    }

    fun getMobile(phone: String, view: IBaseActivityView): LiveData<IResponse<MobileEntity>> {
        return mMobileRepository.getMobile(phone, view)
    }

    fun setQuery(originalInput: String) {
        mMobileRepository.setQuery(originalInput)
    }
}
