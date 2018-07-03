package com.llj.architecturedemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.llj.architecturedemo.MyBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.architecturedemo.presenter.MainPresenter;
import com.llj.architecturedemo.view.MainContractView;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.listeners.OnMyClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@Route(path = CRouter.APP_MAIN_ACTIVITY)
public class MainActivity extends MyBaseActivity<MainPresenter> implements MainContractView {
    @BindView(R.id.tv_click) TextView mTvClick;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

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
    public void toast(MobileEntity mobile) {
        showLongToast(new Gson().toJson(mobile));
    }

    @Override
    protected void onResume() {
        super.onResume();

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
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

        mTvClick.setOnClickListener(new OnMyClickListener() {
            @Override
            public void onCanClick(View v) {
                SecondActivity.start(mContext);
            }
        });

    }

}
