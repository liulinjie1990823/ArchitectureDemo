package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.support.annotation.Keep
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.MvcBaseActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/13
 */
@Keep
@Route(path = CRouter.APP_RXJAVA2_ACTIVITY)
class RxJava2Activity : MvcBaseActivity() {
    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView
    override fun initData() {
    }

    override fun initViews(savedInstanceState: Bundle?) {

        val arrayList = arrayListOf<Data>()
        arrayList.add(Data("map", "map"))

        UniversalBind.Builder(mRecyclerView, MyAdapter(arrayList))
                .setLinearLayoutManager()
                .build()
                .getAdapter()
    }

    override fun layoutId(): Int {
        return R.layout.activity_rxjava
    }


    private inner class MyAdapter(list: MutableList<Data>?) : ListBasedAdapter<Data, ViewHolderHelper>(list) {
        init {
            addItemLayout(R.layout.item_home_fragment)
        }

        override fun onBindViewHolder(viewHolder: ViewHolderHelper, item: Data?, position: Int) {
            if (item == null) {
                return
            }
            val textView = viewHolder.getView<TextView>(R.id.tv_text)
            setText(textView, item.text)

            viewHolder.itemView.setOnClickListener {
                val declaredMethod = RxJava2Activity::class.java.getDeclaredMethod(item.method)
                declaredMethod.isAccessible = true
                declaredMethod.invoke(this@RxJava2Activity)
            }
        }
    }

    private inner class Data(var text: String, var method: String)

    private fun concat() {

        val integerObservable = Observable.create(ObservableOnSubscribe<Int> { emitter ->
            Log.e(mTagLog, "---------")
            Log.e(mTagLog, "integerObservable:Thread " + Thread.currentThread().id)
            emitter.onNext(0)
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

        val integerObservable2 = Observable.create(ObservableOnSubscribe<Int> { emitter ->
            Log.e(mTagLog, "integerObservable2:Thread " + Thread.currentThread().id)
            emitter.onNext(3)
            emitter.onNext(4)
            emitter.onNext(5)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

//        Observable.concat(
//                integerObservable,
//                integerObservable2)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Observer<Int> {
//
//                    override fun onSubscribe(d: Disposable) {
//                    }
//
//                    override fun onNext(integer: Int) {
//                        Log.e(mTagLog, "onNext:$integer")
//                    }
//
//                    override fun onError(e: Throwable) {
//
//                    }
//                    override fun onComplete() {
//                        Log.e(mTagLog, "onComplete:Thread " + Thread.currentThread().id)
//
//                    }
//                })

    }

    private fun map() {
        Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
        }).map { t ->
            val temp = t + 1
            temp.toString()
        }.subscribe {
            Log.d(mTagLog, "accept: $it")
        }

    }


}