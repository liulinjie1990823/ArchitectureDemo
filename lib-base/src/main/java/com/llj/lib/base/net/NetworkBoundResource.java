package com.llj.lib.base.net;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.llj.lib.base.mvp.IBaseActivityView;
import com.llj.lib.net.RxApiManager;
import com.llj.lib.net.observer.BaseApiObserver;
import com.llj.lib.net.response.BaseResponse;
import com.llj.lib.net.response.IResponse;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import retrofit2.Response;


/**
 * ArchitectureDemo
 *
 * describe:同时处理网络和本地数据
 *
 * @author liulj
 * @date 2018/6/5
 */
public abstract class NetworkBoundResource<Data> {

  private final MediatorLiveData<IResponse<Data>> mLiveData = new MediatorLiveData<>();


  @MainThread
  public NetworkBoundResource() {

    //先从数据库中加载数据
    LiveData<Data> dbSource = loadFromDb();

    mLiveData.addSource(dbSource, new Observer<Data>() {
      @Override
      public void onChanged(Data data) {
        //仅仅是从mLiveData移除观察dbSource源
        mLiveData.removeSource(dbSource);

        //判定数据是否需要重新从网络获取
        if (shouldFetch(data)) {
          //需要从网络加载
          fetchFromNetwork(dbSource);
        } else {
          //不需要从网络拉取，直接使用之前从本地db加载的数据dbSource
          mLiveData.addSource(dbSource, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
              mLiveData.setValue(BaseResponse.success(data));
            }
          });
        }
      }
    });
  }

  private void fetchFromNetwork(final LiveData<Data> dbSource) {
    IBaseActivityView view = view();

    //Single
    Single<Response<BaseResponse<Data>>> apiCall = createCall().doOnSubscribe(view).doFinally(view);

    //Observer
    BaseApiObserver<Data> baseApiObserver = new BaseApiObserver<Data>(view.getLoadingDialog()) {

      @Override
      public void onSubscribe(@NotNull Disposable d) {
        super.onSubscribe(d);
        view.addDisposable(getRequestId(), getDisposable());
      }

      @Override
      public void onSuccess(@NonNull BaseResponse<Data> response) {
        super.onSuccess(response);

        if (response.isOk()) {
          //code为0
          Data data = processResponse(response);

          //有数据就保存到数据库
          if (data != null) {
            saveCallResult(data);
          }

          //post到主线程
          dispatchSuccessResult(response);
        } else {
          //code非0，一般直接走onError了，因为已经在HttpResponseFunction中处理了
          dispatchSuccessResult(response);
        }
      }

      @Override
      public void onError(@NonNull Throwable throwable) {
        super.onError(throwable);
        dispatchFailureResult(throwable);
      }
    };

    //subscribe
    RxApiManager.get().subscribeApi(apiCall, view().bindRequestLifecycle(), baseApiObserver);
  }

  private void dispatchSuccessResult(@NonNull BaseResponse<Data> response) {
    AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
      @Override
      public void run() {
        //重新通过loadFromDb加载数据库中的数据
        mLiveData.addSource(loadFromDb(), new Observer<Data>() {
          @Override
          public void onChanged(Data newData) {
            response.setData(newData);
            mLiveData.setValue(response);
          }
        });
      }
    });
  }

  private void dispatchFailureResult(@NonNull Throwable throwable) {
    AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
      @Override
      public void run() {
        //重新通过loadFromDb加载数据库中的数据
        mLiveData.addSource(loadFromDb(), new Observer<Data>() {
          @Override
          public void onChanged(Data newData) {
            mLiveData.setValue(BaseResponse.error(newData, throwable));
          }
        });
      }
    });
  }


  @NonNull
  @MainThread
  protected abstract LiveData<Data> loadFromDb();


  @MainThread
  protected abstract boolean shouldFetch(@Nullable Data data);


  @NonNull
  @MainThread
  protected abstract IBaseActivityView view();

  @NonNull
  @MainThread
  protected abstract Object tag();

  @NonNull
  @MainThread
  protected abstract Single<Response<BaseResponse<Data>>> createCall();


  @WorkerThread
  protected abstract void saveCallResult(@NonNull Data item);

  @WorkerThread
  Data processResponse(IResponse<Data> response) {
    return response.getData();
  }

  protected void onFetchFailed() {
  }


  public LiveData<IResponse<Data>> asLiveData() {
    return mLiveData;
  }
}
