package com.llj.lib.base

import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import butterknife.ButterKnife
import butterknife.Unbinder
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.net.observer.ITaskId
import com.llj.lib.utils.AInputMethodManagerUtils
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

    private var mInit: Boolean = false //是否已经初始化
    private var mVisible: Boolean = false //是否可见

    private lateinit var mUnBinder: Unbinder
    private var mRequestDialog: ITaskId? = null

    var mUseSoftInput: Boolean = false //是否使用软键盘

    //<editor-fold desc="生命周期">
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showsDialog = false

        setStyle(STYLE_NO_TITLE, R.style.no_dim_dialog)

        mContext = context!!

        if (arguments !== null) {
            getArgumentsData(arguments!!)
        }

        try {
            AndroidSupportInjection.inject(this)
        } catch (e: Exception) {
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BaseDialogImpl(activity!!, theme)
        if (mUseSoftInput) {
            dialog.setOnShowListener(DialogInterface.OnShowListener {
                dialog.window!!.decorView.post(Runnable {
                    if (activity == null) {
                        return@Runnable
                    }
                    AInputMethodManagerUtils.showOrHideInput(dialog, true)
                })
            })
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (activity == null) {
            return
        }
        if (mUseSoftInput) {
            AInputMethodManagerUtils.hideSoftInputFromWindow(getDialog())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (dialog == null || dialog.window == null) {
            return
        }
        setWindowParams(dialog.window!!, -1, -1, Gravity.CENTER)
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

        mVisible = true

        if (hasInitAndVisible()) {
            onLazyLoad()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 已经完成初始化
        mInit = true
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
    override fun addDisposable(taskId: Int, disposable: Disposable) {
        if (mContext is ITask) {
            (mContext as ITask).addDisposable(taskId, disposable)
        }
    }

    override fun removeDisposable(taskId: Int?) {
        if (mContext is ITask) {
            (mContext as ITask).removeDisposable(taskId)
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
        return mInit && mVisible
    }

    override fun onLazyLoad() {
        LogUtil.e(mTagLog, "mInit:$mInit,mVisible:$mVisible")
    }
    //</editor-fold >

    //<editor-fold desc="ILoadingDialogHandler">
    override fun getLoadingDialog(): ITaskId? {
        return mRequestDialog
    }

    //自定义实现
    override fun initLoadingDialog(): ITaskId? {
        return null
    }

    private fun checkRequestDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog()

            if (mRequestDialog == null) {
                mRequestDialog = LoadingDialog(mContext)
            }
        }
        setRequestId(hashCode())
    }


    override fun setRequestId(taskId: Int) {
        getLoadingDialog()?.setRequestId(taskId)
    }

    override fun getRequestId(): Int {
        return getLoadingDialog()?.getRequestId() ?: -1
    }
    //</editor-fold >


    fun setWindowParams(window: Window, width: Int, height: Int, gravity: Int) {
        //        StatusBarCompat.translucentStatusBar(getWindow(), true);
        //         setCancelable(cancelable);
        //         setCanceledOnTouchOutside(cancel);
        val params = window.attributes
        // setContentView设置布局的透明度，0为透明，1为实际颜色,该透明度会使layout里的所有空间都有透明度，不仅仅是布局最底层的view
        // params.alpha = 1f;
        // 窗口的背景，0为透明，1为全黑
        // params.dimAmount = 0f;
        params.width = width
        params.height = height
        params.gravity = gravity
        window.attributes = params
    }
}