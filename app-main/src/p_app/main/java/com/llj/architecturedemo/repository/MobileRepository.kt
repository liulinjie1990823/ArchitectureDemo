package com.llj.architecturedemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.llj.architecturedemo.api.TestApiService
import com.llj.architecturedemo.db.dao.MobileDao
import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.di.MainScope
import com.llj.lib.base.mvp.BaseRepository
import com.llj.lib.base.net.NetworkBoundResource
import com.llj.lib.base.utils.AppExecutors
import com.llj.lib.net.response.BaseResponse
import com.llj.lib.net.response.IResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * @author liulj
 * @date 2018/6/6
 */
@MainScope
class MobileRepository @Inject constructor(
    private val mMobileDao: MobileDao,
    private val mApiService: TestApiService,
    private val mAppExecutors: AppExecutors,
) : BaseRepository() {

  private val result: MediatorLiveData<String>
  private val testLive: MutableLiveData<String>

  init {
    this.result = MediatorLiveData<String>()
    this.testLive = MutableLiveData<String>()
  }

  fun getTest(): LiveData<MobileEntity> {
    val mMobileMutableLiveData = MediatorLiveData<MobileEntity>()
    val liveData = MutableLiveData<MobileEntity>()
    liveData.value = MobileEntity(1)

    val liveData2 = MutableLiveData<MobileEntity>()
    liveData2.value = MobileEntity(2)

    return mMobileMutableLiveData
  }

  fun getMobile(phone: String, requestId: Int): LiveData<IResponse<MobileEntity?>?> {
    return object : NetworkBoundResource<MobileEntity>(mAppExecutors) {
      override fun loadFromDb(): LiveData<MobileEntity?> {
        return mMobileDao.selectMobileByPhone("1318888")
      }

      //一般是用activity里设置的hashcode，也可以单独设置
      override fun requestId(): Int {
        return requestId
      }

      override fun shouldFetch(dbResult: MobileEntity?): Boolean {
        return dbResult == null
      }

      override fun createCall(): Single<Response<BaseResponse<MobileEntity?>?>> {
        return mApiService.getMobile(phone)
      }

      override fun saveCallResult(netResult: MobileEntity) {
        mMobileDao.insert(netResult)
      }
    }.asLiveData()
  }


  fun setQuery(originalInput: String) {
    result.removeSource(testLive)
    testLive.value = originalInput
    result.addSource(testLive) { str ->
      result.removeSource(testLive) //先移除
      if (str == null) {
      } else {
        result.addSource(testLive) { s -> result.value = "成功咯" + s!! } //双层嵌套，前提是前面有removeSource
      }
    }
    testLive.value = "test1" //注意这里和remove就是使用双层嵌套的原因
  }


  fun getResult(): LiveData<String> {
    return result
  }


}
