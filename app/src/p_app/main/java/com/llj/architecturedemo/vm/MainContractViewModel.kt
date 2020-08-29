package com.llj.architecturedemo.vm

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.repository.MobileRepository
import com.llj.lib.base.mvp.IBaseActivityView
import com.llj.lib.base.mvvm.BaseViewModel
import com.llj.lib.base.utils.AbsentLiveData
import com.llj.lib.net.response.IResponse
import com.llj.lib.utils.AParseUtils

import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * @author liulj
 * @date 2018/5/29
 */
class MainContractViewModel @Inject constructor(
    private val mMobileRepository: MobileRepository
) : BaseViewModel() {

  private val _query = MutableLiveData<HashMap<String, Any>?>()
  val query: LiveData<HashMap<String, Any>?> = _query

  fun setQuery(map: HashMap<String, Any>?) {
    _query.value = map
  }

  //监听参数_query的变化，会发起getMobile请求
  val mobile: LiveData<IResponse<MobileEntity?>?> = Transformations.switchMap(_query) {
    if (it == null) {
      AbsentLiveData.create()
    } else {
      mMobileRepository.getMobile(it["phone"].toString(), AParseUtils.parseInt(it["requestId"].toString()))
    }
  }
}
