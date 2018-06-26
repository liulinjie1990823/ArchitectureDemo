package com.llj.lib.net;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.llj.lib.net.response.BaseResponse;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/5
 */
public abstract class NetworkBoundResource<Data, RequestType> {


    private final MediatorLiveData<BaseResponse<Data>> mLiveData = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource( ) {
        LiveData<Data> dbSource = loadFromDb();

        mLiveData.addSource(dbSource, resultType -> {
            mLiveData.removeSource(dbSource);

            if (shouldFetch(resultType)) {
                fetchFromNetwork(dbSource);
            } else {
                mLiveData.addSource(dbSource, resultType1 -> mLiveData.setValue(BaseResponse.success(resultType1)));
            }
        });

    }

    private void fetchFromNetwork(final LiveData<Data> dbSource) {
        Single<Response<BaseResponse<Data>>> apiCall = createCall();

        RxApiManager.get().subscribeApi(
                apiCall,
                view.bindRequestLifecycle(),
                new ApiObserver<MobileEntity>(view) {
                    @Override
                    public void onSuccess(@NonNull BaseResponse<MobileEntity> response) {
                        super.onSuccess(response);
                        mutableLiveData.setValue(response.getData());
                    }
                });

        saveCallResult(processResponse(response));
    }


    @NonNull
    @MainThread
    protected abstract LiveData<Data> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Single<Response<BaseResponse<Data>>> createCall();

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable Data data);

    public LiveData<BaseResponse<Data>> asLiveData() {
        return mLiveData;
    }
}
