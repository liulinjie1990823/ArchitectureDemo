package com.llj.architecturedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.llj.lib.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    public static final String TAG = MainActivity.class.getSimpleName();

    private Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Log.e(TAG, "onNext:" + s);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError:" + e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.e(TAG, "onComplete:");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.toast();
        Observable<String> obs1 = Observable.create(emitter -> {
            Log.e(TAG, "obs1thread:" + Thread.currentThread());

            emitter.onNext("a1");
            emitter.onNext("a2");
            emitter.onNext("a3");

            emitter.onComplete();
        });

//        Observable.interval(2000000, TimeUnit.MILLISECONDS).map(new Function<Long, String>() {
//            @Override
//            public String apply(Long aLong) throws Exception {
//                return aLong + "";
//            }
//        }).compose(this.<String>bindToLifecycle()).subscribe(mObserver);

        List<ObservableSource<String>> observableList = new ArrayList<>();
        Observable<String> obs2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "obs2thread:" + Thread.currentThread());

                Thread.sleep(3000);
                emitter.onNext("b1");
                emitter.onNext("b2");
                emitter.onNext("b3");


                emitter.onComplete();
            }
        });
        Observable<String> obs3 = Observable.just("c1", "c2", "c3");

        observableList.add(obs1);
        observableList.add(obs2);
        observableList.add(obs3);

//        Observable.concat(observableList).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e(TAG, "onNext:" + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError:" + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG, "onComplete:" );
//            }
//        });

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void getIntentData(Intent intent) {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
        }
    }

    @Override
    protected void initData() {

    }
}
