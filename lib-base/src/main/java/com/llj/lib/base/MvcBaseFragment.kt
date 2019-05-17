package com.llj.lib.base

import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.net.observer.ITag
import com.llj.lib.utils.LogUtil
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
abstract class MvcBaseFragment : BaseFragment() {
    val mTagLog: String = this.javaClass.simpleName
    lateinit var mContext: Context

    private var mIsInit: Boolean = false
    private val mIsVisible: Boolean = false

    private lateinit var mUnBinder: Unbinder
    private var mRequestDialog: ITag? = null

    //<editor-fold desc="生命周期">
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = context!!

        if (arguments !== null) {
            getArgumentsData(arguments!!)
        }

        try {
            AndroidSupportInjection.inject(this)
        } catch (e: Exception) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutView = layoutView()
        val view = layoutView ?: inflater.inflate(layoutId(), null)

        mUnBinder = ButterKnife.bind(this, view)

        checkRequestDialog()

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

    override fun onDestroyView() {
        super.onDestroyView()

        //防止窗口泄漏
        val requestDialog = getLoadingDialog() as Dialog?
        if (requestDialog!!.isShowing) {
            requestDialog.cancel()
        }

        removeAllDisposable()

        mUnBinder.unbind()
    }
    //</editor-fold >


    //<editor-fold desc="任务处理">
    override fun addDisposable(tag: Any, disposable: Disposable) {
        if (mContext is ITask) {
            (mContext as ITask).addDisposable(tag, disposable)
        }
    }

    override fun removeDisposable(tag: Any?) {
        if (mContext is ITask) {
            (mContext as ITask).removeDisposable(tag)
        }
    }

    override fun removeAllDisposable() {
        if (mContext is ITask) {
            (mContext as ITask).removeAllDisposable()
        }
    }
    //</editor-fold >

    //<editor-fold desc="IBaseFragment">
    override fun initLifecycleObserver(lifecycle: Lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
    }
    //</editor-fold >

    //<editor-fold desc="IBaseFragmentLazy">
    override fun hasInitAndVisible(): Boolean {
        return mIsInit && mIsVisible
    }

    override fun onLazyLoad() {
        LogUtil.e(mTagLog, "mIsInit:$mIsInit,mIsVisible:$mIsVisible")
    }
    //</editor-fold >

    //<editor-fold desc="ILoadingDialogHandler">
    override fun getLoadingDialog(): ITag? {
        return mRequestDialog
    }

    //自定义实现
    override fun initLoadingDialog(): ITag? {
        return null
    }

    private fun checkRequestDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog()

            if (mRequestDialog == null) {
                mRequestDialog = LoadingDialog(mContext)
            }
        }
        setRequestTag(hashCode())
    }


    override fun setRequestTag(tag: Any) {
        getLoadingDialog()?.setRequestTag(tag)
    }

    override fun getRequestTag(): Any {
        return getLoadingDialog()?.getRequestTag() ?: -1
    }
    //</editor-fold >
}