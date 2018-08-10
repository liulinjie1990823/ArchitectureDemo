package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.llj.architecturedemo.MyBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.presenter.MainPresenter
import com.llj.architecturedemo.view.MainContractView
import com.llj.component.service.arouter.CRouter
import com.llj.lib.utils.AToastUtils
import com.meituan.android.walle.WalleChannelReader
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@Route(path = CRouter.APP_MAIN_ACTIVITY)
class MainActivity : MyBaseActivity<MainPresenter>(), MainContractView {


    private val mObserver = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {

        }

        override fun onNext(s: String) {
            Log.e(mTag, "onNext:$s")
        }

        override fun onError(e: Throwable) {
            Log.e(mTag, "onError:" + e.message)
        }

        override fun onComplete() {
            Log.e(mTag, "onComplete:")
        }
    }

    override fun toast(mobile: MobileEntity?) {
        if (mobile != null) {
            showLongToast(Gson().toJson(mobile))
        }
    }

    override fun onResume() {
        super.onResume()


        val channel = WalleChannelReader.getChannel(this.applicationContext) ?: ""
        AToastUtils.show(channel)

        val obs1 = Observable.create<String> { emitter ->
            Log.e(mTag, "obs1thread:" + Thread.currentThread())

            emitter.onNext("a1")
            emitter.onNext("a2")
            emitter.onNext("a3")

            emitter.onComplete()
        }

        //        Observable.interval(2000000, TimeUnit.MILLISECONDS).map(new Function<Long, String>() {
        //            @Override
        //            public String apply(Long aLong) throws Exception {
        //                return aLong + "";
        //            }
        //        }).compose(this.<String>bindToLifecycle()).subscribe(mObserver);

        val observableList = ArrayList<ObservableSource<String>>()
        val obs2 = Observable.create(ObservableOnSubscribe<String> { emitter ->
            Log.e(mTag, "obs2thread:" + Thread.currentThread())

            Thread.sleep(3000)
            emitter.onNext("b1")
            emitter.onNext("b2")
            emitter.onNext("b3")


            emitter.onComplete()
        })
        val obs3 = Observable.just("c1", "c2", "c3")

        observableList.add(obs1)
        observableList.add(obs2)
        observableList.add(obs3)

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

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        setOnClickListener(mTvClick, View.OnClickListener {
            CRouter.start(CRouter.APP_SHARE_ACTIVITY)
        })
    }
}
