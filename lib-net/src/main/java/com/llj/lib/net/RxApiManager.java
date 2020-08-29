package com.llj.lib.net;

import com.llj.lib.net.exception.ExceptionFunction;
import com.llj.lib.net.observer.BaseApiObserver;
import com.llj.lib.net.response.BaseResponse;
import com.llj.lib.net.response.HttpResponseFunction;
import com.uber.autodispose.AutoDisposeConverter;

import java.util.HashMap;
import java.util.Set;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * ArchitectureDemo describe: author liulj date 2018/5/10
 */
public class RxApiManager implements RxActionManager<Object> {

  private static RxApiManager sInstance = null;

  private HashMap<Object, Disposable> mArrayMap;

  public static RxApiManager get() {
    if (sInstance == null) {
      synchronized (RxApiManager.class) {
        if (sInstance == null) {
          sInstance = new RxApiManager();
        }
      }
    }
    return sInstance;
  }

  public RxApiManager() {
    mArrayMap = new HashMap<>();
  }

  @Override
  public void add(Object tag, Disposable disposable) {
    mArrayMap.put(tag, disposable);
  }

  @Override
  public void remove(Object tag) {
    if (!mArrayMap.isEmpty()) {
      mArrayMap.remove(tag);
    }
  }

  @Override
  public void removeAll() {
    if (!mArrayMap.isEmpty()) {
      mArrayMap.clear();
    }
  }

  @Override
  public void cancel(Object tag) {
    if (mArrayMap.isEmpty() || mArrayMap.get(tag) == null) {
      return;
    }
    if (!mArrayMap.get(tag).isDisposed()) {
      mArrayMap.get(tag).dispose();
      mArrayMap.remove(tag);
    }
  }

  @Override
  public void cancelAll() {
    if (mArrayMap.isEmpty()) {
      return;
    }
    Set<Object> keys = mArrayMap.keySet();
    for (Object apiKey : keys) {
      cancel(apiKey);
    }
  }

  public <Data> void subscribeApi(Single<Response<BaseResponse<Data>>> single,
      BaseApiObserver<Data> observer) {
    subscribeApi(single, new HttpResponseFunction<>(), new ExceptionFunction<>(),
        null, observer);
  }

  /**
   * @param single
   * @param autoDisposeConverter
   * @param observer
   * @param <Data>
   */
  public <Data> void subscribeApi(Single<Response<BaseResponse<Data>>> single,
      AutoDisposeConverter<BaseResponse<Data>> autoDisposeConverter,
      BaseApiObserver<Data> observer) {
    subscribeApi(single, new HttpResponseFunction<>(), new ExceptionFunction<>(),
        autoDisposeConverter, observer);
  }

  /**
   * @param single Single
   * @param httpResult http结果处理
   * @param error 错误处理
   * @param autoDisposeConverter 生命周期管理
   * @param observer 观察者
   * @param <Data> data数据
   */
  public <Data> void subscribeApi(Single<Response<BaseResponse<Data>>> single,
      Function<Response<BaseResponse<Data>>, BaseResponse<Data>> httpResult,
      Function<Throwable, Single<BaseResponse<Data>>> error,
      AutoDisposeConverter<BaseResponse<Data>> autoDisposeConverter,
      BaseApiObserver<Data> observer) {

    Single<BaseResponse<Data>> baseResponseSingle = single
        .subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(httpResult)
        .onErrorResumeNext(error);
    if (autoDisposeConverter != null) {
      baseResponseSingle.as(autoDisposeConverter).subscribe(observer);
    } else {
      baseResponseSingle.subscribe(observer);
    }
  }
}
