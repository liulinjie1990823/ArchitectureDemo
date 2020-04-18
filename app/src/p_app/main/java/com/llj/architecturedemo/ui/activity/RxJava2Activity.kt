package com.llj.architecturedemo.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.Keep
import androidx.viewbinding.ViewBinding
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.IUiHandler
import com.llj.lib.utils.LogUtil
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/13
 */
@Keep
@Route(path = CRouter.APP_RXJAVA2_ACTIVITY)
class RxJava2Activity : AppMvcBaseActivity<ViewBinding>() {
    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: androidx.recyclerview.widget.RecyclerView

    override fun initData() {
    }

    override fun initViews(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_FULLSCREEN)


        val arrayList = arrayListOf<Data?>()
        arrayList.add(Data("map", "map"))
        arrayList.add(Data("zip", "zip"))
        arrayList.add(Data("concat", "concat"))
        arrayList.add(Data("concatEager", "concatEager"))

        arrayList.add(Data("take", ""))
        arrayList.add(Data("take", "take"))
        arrayList.add(Data("takeLast", "takeLast"))
        arrayList.add(Data("takeUntil", "takeUntil"))
        arrayList.add(Data("takeWhile", "takeWhile"))

        arrayList.add(Data("skip", ""))
        arrayList.add(Data("skip", "skip"))
        arrayList.add(Data("skipLast", "skipLast"))

        arrayList.add(Data("first", ""))
        arrayList.add(Data("first", "first"))
        arrayList.add(Data("firstElement", "firstElement"))
        arrayList.add(Data("firstOrError", "firstOrError"))

        arrayList.add(Data("last", ""))
        arrayList.add(Data("last", "last"))
        arrayList.add(Data("lastElement", "lastElement"))
        arrayList.add(Data("lastOrError", "lastOrError"))

        arrayList.add(Data("filter", "filter"))
        arrayList.add(Data("ofType", "ofType"))

        var adapter = UniversalBind.Builder(mRecyclerView, MyAdapter(arrayList))
                .setLinearLayoutManager()
                .build()
                .getAdapter()
    }

    override fun layoutId(): Int {
        return R.layout.activity_rxjava
    }


    private inner class MyAdapter : ListBasedAdapter<Data, ViewHolderHelper>, IUiHandler {


        constructor(list: MutableList<Data?>?) : super(list) {
            addItemLayout(R.layout.item_home_fragment)
        }


        override fun onBindViewHolder(viewHolder: ViewHolderHelper, item: Data?, position: Int) {
            if (item == null) {
                return
            }
            val textView = viewHolder.getView<TextView>(R.id.tv_text)
            setText(textView, item.text)
            if (isEmpty(item.method)) {
                textView.setTypeface(Typeface.DEFAULT_BOLD, 0)
            } else {
                textView.setTypeface(Typeface.DEFAULT, 0)
            }

            viewHolder.itemView.setOnClickListener {
                if (isEmpty(item.method)) {
                    return@setOnClickListener
                }
                val declaredMethod = RxJava2Activity::class.java.getDeclaredMethod(item.method)
                declaredMethod.isAccessible = true
                declaredMethod.invoke(this@RxJava2Activity)
            }
        }
    }

    private inner class Data(var text: String, var method: String)
    private inner class Student(var text: String, var age: Int)

    private fun error() {
        Observable.error<String>(IOException())
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {
                        Log.e(mTagLog, "onNext:$s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:")
                    }
                })

    }

    private fun never() {
        Observable.never<String>()
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {
                        Log.e(mTagLog, "onNext:$s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:")
                    }
                })

    }

    private fun empty() {
        Observable.empty<String>()
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {
                        Log.e(mTagLog, "onNext:$s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:")
                    }
                })

    }

    private fun timer() {
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: Long) {
                        Log.e(mTagLog, "onNext:$s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:")
                    }
                })
    }

    private fun interval() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: Long) {
                        Log.e(mTagLog, "onNext:$s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:")
                    }
                })

    }

    private fun range() {
        Observable.range(1, 5)
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: Int) {
                        Log.e(mTagLog, "onNext:$s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:")
                    }
                })
    }

    private fun fromRunnable() {
        Completable.fromRunnable(object : Runnable {
            override fun run() {
                Log.e(mTagLog, "fromAction")
            }
        }).subscribe(object : CompletableObserver {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    private fun fromAction() {
        Completable.fromAction(object : Action {
            override fun run() {
                Log.e(mTagLog, "fromAction")
            }
        }).subscribe(object : CompletableObserver {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

        })
    }

    private fun fromCallable() {
        Observable.fromCallable(object : Callable<String> {
            override fun call(): String {
                return "Hello World!"
            }
        }).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(s: String) {
                Log.e(mTagLog, "onNext:$s")
            }

            override fun onError(e: Throwable) {
                Log.e(mTagLog, "onError:" + e.message)
            }

            override fun onComplete() {
                Log.e(mTagLog, "onComplete:")
            }
        })
    }

    private fun just() {
        Observable.just("1", "A", "3.2", "def")
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {
                        Log.e(mTagLog, "onNext:$s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:")
                    }
                })

    }

    private fun last() {
        Observable.just(1, 2, 3)
                .last(100)
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "first value = $t")
                    }
                })
    }

    private fun lastElement() {
        Observable.just(1, 2, 3)
                .lastElement()
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "first value = $t")
                    }
                })
    }

    private fun lastOrError() {
        Observable.just(1, 2, 3)
                .lastOrError()
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "first value = $t")
                    }
                })
    }

    private fun first() {
        //elementAt(0L, defaultItem);
        Observable.just(1, 2, 3)
                .first(100)
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "first value = $t")
                    }
                })
    }

    private fun firstElement() {
        //elementAt(0L);
        Observable.just(1, 2, 3)
                .firstElement()
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "first value = $t")
                    }
                })
    }

    private fun firstOrError() {
        //elementAtOrError(0L);
        Observable.just(1, 2, 3)
                .firstOrError()
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "first value = $t")
                    }
                })
    }


    private fun take() {
        //忽略前3个数据
        Observable.just(0, 1, 2, 3, 4, 5)
                .take(3)
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "take value = $t")
                    }

                })
    }

    private fun takeLast() {
        //忽略前3个数据
        Observable.just(0, 1, 2, 3, 4, 5)
                .takeLast(3)
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "take value = $t")
                    }

                })
    }

    private fun takeUntil() {
        Observable.just(0, 1, 2, 3, 4, 5)
                .takeUntil(object : Predicate<Int> {
                    override fun test(t: Int): Boolean {
                        return t > 3
                    }
                })
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "takeUntil value = $t")
                    }

                })
    }

    private fun takeWhile() {
        Observable.just(0, 1, 2, 3, 4, 5)
                .takeWhile(object : Predicate<Int> {
                    override fun test(t: Int): Boolean {
                        return t > 3
                    }
                })
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "takeUntil value = $t")
                    }

                })
    }

    private fun skip() {
        //忽略前3个数据
        Observable.just(0, 1, 2, 3, 4, 5)
                .skip(3)
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "skip remain value = $t")
                    }

                })
    }

    private fun skipLast() {
        //忽略后3个数据
        Observable.just(0, 1, 2, 3, 4, 5)
                .skipLast(3)
                .subscribe(object : Consumer<Int> {
                    override fun accept(t: Int?) {
                        LogUtil.e(mTagLog, "skip remain value = $t")
                    }

                })
    }


    private fun filter() {
        Observable.fromArray(Student("Stay", 28), Student("谷歌小弟", 23), Student("Star", 25))
                .filter(object : Predicate<Student> {
                    override fun test(t: Student): Boolean {
                        return t.age > 25
                    }
                })
                .subscribe(object : Consumer<Student> {
                    override fun accept(student: Student?) {
                        println("student = $student")
                    }

                })
    }

    private fun ofType() {
        Observable.just("a", 2, 3.0).ofType(String::class.java)
                .subscribe(object : Consumer<String> {
                    override fun accept(value: String?) {
                        println("ofType value = $value")
                    }
                })
    }

    private fun concatDelayError() {

    }

    private fun concatEager2() {

        val obs1 = Observable.create(ObservableOnSubscribe<String> { emitter ->
            Log.e(mTagLog, "obs1thread:" + Thread.currentThread())

            Thread.sleep(2000)
            emitter.onNext("a1")
            emitter.onNext("a2")
            emitter.onNext("a3")

            //            emitter.onComplete()
        })


        val obs2 = Observable.create(ObservableOnSubscribe<Int> { emitter ->
            Log.e(mTagLog, "obs2thread:" + Thread.currentThread())

            Thread.sleep(3000)
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)

            emitter.onComplete()
        })

        Observable.zip(obs1, obs2, object : BiFunction<String, Int, Boolean> {
            override fun apply(t1: String, t2: Int): Boolean {

                return true
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: Boolean) {
                        Log.e(mTagLog, "onNext:$s")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(mTagLog, "onError:" + e.message)
                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:")
                    }
                })
    }

    private fun concatEager() {

        val obs1 = Observable.create(ObservableOnSubscribe<String> { emitter ->
            Log.e(mTagLog, "obs1thread:" + Thread.currentThread())

            Thread.sleep(2000)
            emitter.onNext("a1")
            emitter.onNext("a2")
            emitter.onNext("a3")

            //            emitter.onComplete()
        })


        val obs2 = Observable.create(ObservableOnSubscribe<String> { emitter ->
            Log.e(mTagLog, "obs2thread:" + Thread.currentThread())

            Thread.sleep(3000)
            emitter.onNext("b1")
            emitter.onNext("b2")
            emitter.onNext("b3")

            emitter.onComplete()
        })
        val obs3 = Observable.create(ObservableOnSubscribe<String> { emitter ->
            Log.e(mTagLog, "obs3thread:" + Thread.currentThread())

            emitter.onNext("c1")
            emitter.onNext("c2")
            emitter.onNext("c3")

            emitter.onComplete()
        })

        val observableList = arrayListOf<ObservableSource<String>>()
        observableList.add(obs1)
        observableList.add(obs2)
        observableList.add(obs3)

        Observable.concatEager<String>(observableList).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(s: String) {
                Log.e(mTagLog, "onNext:$s")
            }

            override fun onError(e: Throwable) {
                Log.e(mTagLog, "onError:" + e.message)
            }

            override fun onComplete() {
                Log.e(mTagLog, "onComplete:")
            }
        })
    }

    private fun concatWith() {

        val integerObservable = Observable.create(ObservableOnSubscribe<String> { emitter ->
            Log.e(mTagLog, "---------")
            Log.e(mTagLog, "integerObservable:Thread " + Thread.currentThread().id)
            emitter.onNext("a0")
            emitter.onNext("a1")
            emitter.onNext("a2")
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

        val integerObservable2 = Observable.create(ObservableOnSubscribe<Int> { emitter ->
            Log.e(mTagLog, "integerObservable2:Thread " + Thread.currentThread().id)
            emitter.onNext(3)
            emitter.onNext(4)
            emitter.onNext(5)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

        val integerObservable3 = Observable.create(ObservableOnSubscribe<Int> { emitter ->
            Log.e(mTagLog, "integerObservable2:Thread " + Thread.currentThread().id)
            emitter.onNext(6)
            emitter.onNext(7)
            emitter.onNext(8)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())

        integerObservable2.concatWith(integerObservable3).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Int> {

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(integer: Int) {
                        Log.e(mTagLog, "onNext:$integer")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:Thread " + Thread.currentThread().id)

                    }
                })
    }

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

        Observable.concat(
                integerObservable,
                integerObservable2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Int> {

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(integer: Int) {
                        Log.e(mTagLog, "onNext:$integer")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        Log.e(mTagLog, "onComplete:Thread " + Thread.currentThread().id)

                    }
                })

    }

    private fun zip() {
        val firstNames = Observable.fromArray("James", "Jean-Luc", "Benjamin")
        val lastNames = Observable.fromArray("Kirk", "Picard", "Sisko")
        firstNames.zipWith(lastNames, BiFunction<String, String, String> { t1, t2 -> t1 + t2 })
                .subscribe { item -> println(item) }
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