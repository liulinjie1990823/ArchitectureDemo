package com.llj.architecturedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Observable<String> obs1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG,"obs1thread:"+Thread.currentThread());

                Thread.sleep(2000);
                emitter.onNext("a1");
                emitter.onNext("a2");
                emitter.onNext("a3");

                emitter.onComplete();
            }
        });

        List<ObservableSource<String>> observableList=new ArrayList<>() ;
        Observable<String> obs2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG,"obs2thread:"+Thread.currentThread());

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

        Observable.concat(observableList).subscribe(new Observer<String>() {
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
                Log.e(TAG, "onComplete:" );
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();


    }
}
