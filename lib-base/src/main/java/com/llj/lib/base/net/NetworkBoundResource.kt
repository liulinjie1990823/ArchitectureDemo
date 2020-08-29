package com.llj.lib.base.net

import android.util.ArrayMap
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.llj.lib.base.mvp.IBaseActivityView
import com.llj.lib.base.utils.AppExecutors
import com.llj.lib.net.RxApiManager
import com.llj.lib.net.observer.BaseApiObserver
import com.llj.lib.net.response.BaseResponse
import com.llj.lib.net.response.IResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.Response

/**
 * ArchitectureDemo
 *
 * describe:同时处理网络和本地数据
 *
 * @author liulj
 * @date 2018/6/5
 */
abstract class NetworkBoundResource<ResultType> @MainThread constructor(private val appExecutors: AppExecutors) {

  private val result = MediatorLiveData<IResponse<ResultType?>?>()


  init {
    result.value = BaseResponse.loading(null)
    //先从数据库中加载数据
    val dbSource = loadFromDb()
    result.addSource(dbSource) { data: ResultType? ->
      //仅仅是从result移除观察dbSource源
      result.removeSource(dbSource)
      //判定数据是否需要重新从网络获取
      if (shouldFetch(data)) {
        //需要从网络加载
        fetchFromNetwork(dbSource)
      } else {
        //不需要从网络拉取，直接使用之前从本地db加载的数据dbSource
        result.addSource(dbSource) { newData: ResultType? ->
          setValue(BaseResponse.success(newData))
        }
      }
    }
  }

  @MainThread
  private fun setValue(newValue: IResponse<ResultType?>) {
    if (result.value != newValue) {
      result.value = newValue
    }
  }

  private fun fetchFromNetwork(dbSource: LiveData<ResultType?>) {
    //先返回数据库中的数据
    result.addSource(dbSource) { newData ->
      result.removeSource(dbSource)
      setValue(BaseResponse.loading(newData))
    }

    //subscribe
    RxApiManager.get().subscribeApi(createCall(), object : BaseApiObserver<ResultType?>(requestId
    ()) {
      override fun onSubscribe(d: Disposable) {
        super.onSubscribe(d)
        val disposableMap = disposableMap()
        disposableMap?.run {
          disposableMap[getRequestId()] = getDisposable()
        }

      }

      override fun onSuccess(response: BaseResponse<ResultType?>) {
        super.onSuccess(response)
        if (response.isOk) {
          //code为0
          appExecutors.diskIO().execute {
            //处理数据
            val data: ResultType? = processResponse(response)
            //有数据就保存到数据库
            data?.run { saveCallResult(this) }
            //post到主线程
            dispatchSuccessResult(response)
          }
        } else {
          //code非0，一般直接走onError了，因为已经在HttpResponseFunction中处理了
          dispatchSuccessResult(response)
        }
      }

      override fun onError(throwable: Throwable) {
        super.onError(throwable)
        dispatchFailureResult(throwable)
      }
    })
  }

  private fun dispatchSuccessResult(response: BaseResponse<ResultType?>) {
    appExecutors.mainThread().execute { //重新通过loadFromDb加载数据库中的数据
      result.addSource(loadFromDb()) { newData ->
        setValue(BaseResponse.success(newData))
      }
    }
  }

  private fun dispatchFailureResult(throwable: Throwable) {
    appExecutors.mainThread().execute { //重新通过loadFromDb加载数据库中的数据
      result.addSource(loadFromDb()) { newData ->
        setValue(BaseResponse.error(newData, throwable))
      }
    }
  }

  fun asLiveData() = result

  @MainThread
  protected abstract fun loadFromDb(): LiveData<ResultType?>

  @MainThread
  protected abstract fun shouldFetch(dbResult: ResultType?): Boolean

  @MainThread
  protected abstract fun requestId(): Int

  protected open fun disposableMap(): ArrayMap<Int, Disposable>?{
    return null
  }

  @MainThread
  protected abstract fun createCall(): Single<Response<BaseResponse<ResultType?>?>>

  @WorkerThread
  protected abstract fun saveCallResult(netResult: ResultType)

  @WorkerThread
  fun processResponse(response: IResponse<ResultType?>): ResultType? {
    return response.data
  }

  protected open fun onFetchFailed() {}

}