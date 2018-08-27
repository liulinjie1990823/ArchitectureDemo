package com.llj.lib.base

import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.llj.lib.base.mvp.IPresenter
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.net.observer.ITag
import com.llj.lib.utils.LogUtil
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/24
 */
abstract class MvpBaseFragment<P : IPresenter> : Fragment(), IFragment, IFragmentLazy, ICommon, IUiHandler, ILoadingDialogHandler {

    val mTagLog: String = this.javaClass.simpleName
    var mContext: Context? = null

    private var mIsInit: Boolean = false
    private val mIsVisible: Boolean = false

    @Inject lateinit var mPresenter: P
    private lateinit var mUnBinder: Unbinder

    private var mRequestDialog: ITag? = null

    //<editor-fold desc="生命周期">
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View
        val layoutView = layoutView()
        if (layoutView != null) {
            view = layoutView
        } else {
            view = inflater.inflate(layoutId(), null)
        }
        mUnBinder = ButterKnife.bind(this, view)

        checkRequestDialog()

        getArgumentsData(arguments)

        initViews(savedInstanceState)

        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //当fragment在viewPager中的时候需要实现懒加载的模式
        //当使用viewPager进行预加载fragment的时候,先调用setUserVisibleHint,后调用onViewCreated
        //所以刚开始是mIsInit=true,mIsVisible为false
        if (hasInitAndVisible()) {
            onLazyLoad()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 已经完成初始化
        mIsInit = true
        //
        initViews(savedInstanceState)
        //
        if (useLazyLoad()) {
            if (hasInitAndVisible()) {
                onLazyLoad()
            }
        } else {
            initData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //防止窗口泄漏
        val requestDialog = getLoadingDialog() as Dialog?
        if (requestDialog!!.isShowing) {
            requestDialog.cancel()
        }

        mUnBinder.unbind()

        mPresenter.destroy()
    }
    //</editor-fold >

    //<editor-fold desc="IFragment">
    override fun initLifecycleObserver(lifecycle: Lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
        lifecycle.addObserver(mPresenter)
    }
    //</editor-fold >

    //<editor-fold desc="IFragmentLazy">
    override fun hasInitAndVisible(): Boolean {
        return mIsInit && userVisibleHint
    }

    override fun onLazyLoad() {
        LogUtil.e(mTagLog, "mIsInit:$mIsInit,mIsVisible:$mIsVisible")
    }
    //</editor-fold >

    //<editor-fold desc="ILoadingDialogHandler">
    override fun getLoadingDialog(): ITag? {
        return mRequestDialog
    }

    override fun initLoadingDialog(): ITag? {
        return null
    }

    private fun checkRequestDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog()
        }
        if (mRequestDialog == null) {
            mRequestDialog = LoadingDialog(mContext!!)
        }
        setRequestTag(hashCode())
    }


    override fun setRequestTag(tag: Any) {
        if (getLoadingDialog() != null) {
            getLoadingDialog()!!.setRequestTag(tag)
        }
    }

    override fun getRequestTag(): Any {
        return if (getLoadingDialog() != null) {
            getLoadingDialog()!!.getRequestTag()
        } else -1
    }
    //</editor-fold >
}
