package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.facebook.drawee.view.GenericDraweeView
import com.facebook.drawee.view.SimpleDraweeView
import com.google.gson.Gson
import com.llj.adapter.ListBasedAdapter
import com.llj.adapter.UniversalBind
import com.llj.adapter.util.ViewHolderHelper
import com.llj.architecturedemo.R
import com.llj.architecturedemo.db.entity.MobileEntity
import com.llj.architecturedemo.presenter.MainPresenter
import com.llj.architecturedemo.ui.fragment.HomeFragment
import com.llj.architecturedemo.ui.fragment.MineFragment
import com.llj.architecturedemo.ui.fragment.SecondFragment
import com.llj.architecturedemo.ui.fragment.ThirdFragment
import com.llj.architecturedemo.view.MainContractView
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.BaseTabActivity
import com.llj.lib.base.IUiHandler
import com.llj.lib.image.loader.FrescoImageLoader
import com.llj.lib.image.loader.ICustomImageLoader
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

@Route(path = CRouter.APP_MAIN_ACTIVITY)
class MainActivity : BaseTabActivity<MainPresenter>(), MainContractView {

    @BindView(R.id.ll_footer_bar) lateinit var mLlFooterBar: LinearLayout

    private lateinit var mTabAdapter: TabAdapter

    override fun getFragmentId(): Int {
        return R.id.fl_contain
    }


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


        //        val channel = WalleChannelReader.getChannel(this.applicationContext) ?: ""
        //        AToastUtils.show(channel)
        //
        //        val obs1 = Observable.create<String> { emitter ->
        //            Log.e(mTag, "obs1thread:" + Thread.currentThread())
        //
        //            emitter.onNext("a1")
        //            emitter.onNext("a2")
        //            emitter.onNext("a3")
        //
        //            emitter.onComplete()
        //        }
        //
        //        //        Observable.interval(2000000, TimeUnit.MILLISECONDS).map(new Function<Long, String>() {
        //        //            @Override
        //        //            public String apply(Long aLong) throws Exception {
        //        //                return aLong + "";
        //        //            }
        //        //        }).compose(this.<String>bindToLifecycle()).subscribe(mObserver);
        //
        //        val observableList = ArrayList<ObservableSource<String>>()
        //        val obs2 = Observable.create(ObservableOnSubscribe<String> { emitter ->
        //            Log.e(mTag, "obs2thread:" + Thread.currentThread())
        //
        //            Thread.sleep(3000)
        //            emitter.onNext("b1")
        //            emitter.onNext("b2")
        //            emitter.onNext("b3")
        //
        //
        //            emitter.onComplete()
        //        })
        //        val obs3 = Observable.just("c1", "c2", "c3")
        //
        //        observableList.add(obs1)
        //        observableList.add(obs2)
        //        observableList.add(obs3)
        //
        //        //        Observable.concat(observableList).subscribe(new Observer<String>() {
        //        //            @Override
        //        //            public void onSubscribe(Disposable d) {
        //        //
        //        //            }
        //        //
        //        //            @Override
        //        //            public void onNext(String s) {
        //        //                Log.e(TAG, "onNext:" + s);
        //        //            }
        //        //
        //        //            @Override
        //        //            public void onError(Throwable e) {
        //        //                Log.e(TAG, "onError:" + e.getMessage());
        //        //            }
        //        //
        //        //            @Override
        //        //            public void onComplete() {
        //        //                Log.e(TAG, "onComplete:" );
        //        //            }
        //        //        });

    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews(savedInstanceState: Bundle?) {


        val arrayListOf = arrayListOf<Tab>()
        arrayListOf.add(Tab("首页", "http://pic7.photophoto.cn/20080407/0034034859692813_b.jpg",
                "https://img.tthunbohui.cn/dmp/h/cms/1525881600/jh-img-orig-ga_994489188457562112_75_75_1307.png", true))
        arrayListOf.add(Tab("首页", "https://img.tthunbohui.cn/dmp/h/cms/1526140800/jh-img-orig-ga_995601190265470976_70_70_626.png",
                "https://img.tthunbohui.cn/dmp/h/cms/1525881600/jh-img-orig-ga_994489188457562112_75_75_1307.png", false))
        arrayListOf.add(Tab("首页", "https://img.tthunbohui.cn/dmp/h/cms/1526140800/jh-img-orig-ga_995601190265470976_70_70_626.png",
                "https://img.tthunbohui.cn/dmp/h/cms/1525881600/jh-img-orig-ga_994489188457562112_75_75_1307.png", false))
        arrayListOf.add(Tab("我的", "https://img.tthunbohui.cn/dmp/h/cms/1526140800/jh-img-orig-ga_995601190265470976_70_70_626.png",
                "https://img.tthunbohui.cn/dmp/h/cms/1525881600/jh-img-orig-ga_994489188457562112_75_75_1307.png", false))

        mTabAdapter = UniversalBind.Builder(mLlFooterBar, TabAdapter(arrayListOf))
                .build()
                .getAdapter()

        super.initViews(savedInstanceState)

    }

    override fun initData() {
    }

    private inner class TabAdapter(list: ArrayList<Tab>?) : ListBasedAdapter<Tab, ViewHolderHelper>(list), IUiHandler {

        private val mImageLoad: ICustomImageLoader<GenericDraweeView> = FrescoImageLoader.getInstance(mContext.applicationContext)


        init {
            addItemLayout(R.layout.item_main_activity_tab)
        }

        override fun onBindViewHolder(viewHolder: ViewHolderHelper, data: Tab?, position: Int) {
            if (data == null) {
                return
            }
            val image = viewHolder.getView<SimpleDraweeView>(R.id.iv_tab_image)
            val text = viewHolder.getView<TextView>(R.id.tv_tab_text)

            val imageUrl: String? = if (data.select) data.selectImage else data.normalImage
            mImageLoad.loadImage(imageUrl, 120, 120, image)
            setText(text, data.text)
            viewHolder.itemView.tag = position

            viewHolder.itemView.isSelected = data.select

            viewHolder.itemView.setOnClickListener {
                selectItemFromTagByClick(it)
            }
        }
    }

    override fun makeFragment(showItem: Int): Fragment {
        when (showItem) {
            0 -> return HomeFragment()
            1 -> return SecondFragment()
            2 -> return ThirdFragment()
            3 -> return MineFragment()
        }
        return HomeFragment()
    }

    override fun setSelectImage(showItem: Int) {
        val childCount = mLlFooterBar.childCount
        for (i in 0 until childCount) {
            mTabAdapter[i]?.select = (showItem == i)
        }
        mTabAdapter.notifyDataSetChanged()
    }

   private inner class Tab(var text: String,
                    var normalImage: String,
                    var selectImage: String,
                    var select: Boolean
    )


}
