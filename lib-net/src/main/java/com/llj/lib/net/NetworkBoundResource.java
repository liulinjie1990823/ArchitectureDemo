package com.llj.lib.net;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import io.reactivex.Observable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/5
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {


    private final MediatorLiveData<ResultType> mLiveData = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource( ) {

        LiveData<ResultType> dbSource = loadFromDb();

        mLiveData.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                mLiveData.removeSource(dbSource);

                if (shouldFetch(resultType)) {
                    fetchFromNetwork(dbSource);
                } else {
                    mLiveData.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            mLiveData.setValue(resultType);
                        }
                    });
                }
            }
        });

    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        Observable<ResultType> call = createCall();

    }


    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Observable<ResultType> createCall();

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    public LiveData<ResultType> asLiveData() {
        return mLiveData;
    }
}
