package com.llj.lib.base

import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.util.ArraySet
import android.view.*
import butterknife.ButterKnife
import butterknife.Unbinder
import com.llj.lib.base.event.BaseEvent
import com.llj.lib.base.tracker.ITracker
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.net.observer.ITaskId
import com.llj.lib.tracker.PageName
import com.llj.lib.utils.AInputMethodManagerUtils
import com.llj.lib.utils.LogUtil
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * 08-11 11:33:36.156    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ setUserVisibleHint false
 * 08-11 11:33:36.156    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ setUserVisibleHint false
 * 08-11 11:33:36.157    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ setUserVisibleHint true
 * 08-11 11:33:36.158    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onAttach
 * 08-11 11:33:36.158    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onCreate
 * 08-11 11:33:36.159    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onCreateView
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onActivityCreated
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ onResume()
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onAttach
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onCreate
 * 08-11 11:33:36.160    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onCreateView
 * 08-11 11:33:36.161    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onActivityCreated
 * 08-11 11:33:36.161    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ onResume()
 *
 * 当切换到第二个fragment时打印日志：
 *
 * 08-11 11:33:54.084    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ setUserVisibleHint false
 * 08-11 11:33:54.084    7162-7162/com.example.yinsgo.myui V/Fragment1﹕ setUserVisibleHint false
 * 08-11 11:33:54.084    7162-7162/com.example.yinsgo.myui V/Fragment2﹕ setUserVisibleHint true
 * 08-11 11:33:54.084    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onAttach
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onCreate
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onCreateView
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onActivityCreated
 * 08-11 11:33:54.085    7162-7162/com.example.yinsgo.myui V/Fragment3﹕ onResume()
 *
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/15
 */
abstract class MvcBaseFragment : android.support.v4.app.DialogFragment()
        , IBaseFragment, ICommon, IUiHandler, IEvent, ITracker, ITask, ILoadingDialogHandler {
    val mTagLog: String = this.javaClass.simpleName

    lateinit var mContext: Context

    private var mInit: Boolean = false //是否已经初始化

    private lateinit var mUnBinder: Unbinder
    private var mRequestDialog: ITaskId? = null

    private val mDelayMessages: ArraySet<String> = ArraySet()

    var mUseSoftInput: Boolean = false //是否使用软键盘

    private var mPageName: String? = null
    private var mChildPageName: String? = null

    override fun getChildPageName(): String? {
        return mChildPageName
    }

    override fun setChildPageName(name: String) {
        mChildPageName = name
    }

    override fun getPageName(): String {
        if (mPageName == null) {
            val annotation = javaClass.getAnnotation(PageName::class.java)
            mPageName = annotation?.value ?: this.javaClass.simpleName
        }
        return mPageName!!
    }

    //<editor-fold desc="生命周期">
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        Timber.i("onAttach")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")

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
        Timber.i("onActivityCreated")
        if (dialog == null || dialog.window == null) {
            return
        }
        setWindowParams(dialog.window!!, -1, -1, Gravity.CENTER)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView")
        val layoutView = layoutView()
        val view = layoutView ?: inflater.inflate(layoutId(), null)

        mUnBinder = ButterKnife.bind(this, view)

        checkRequestDialog()

        register(this)

        initViews(savedInstanceState)

        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Timber.i("setUserVisibleHint:%s", isVisibleToUser)
        //当fragment在viewPager中的时候需要实现懒加载的模式
        //当使用viewPager进行预加载fragment的时候,先调用setUserVisibleHint,后调用onViewCreated
        //所以刚开始是mIsInit=true,mIsVisible为false

        // 加载数据
        if (useLazyLoad()) {
            if (hasInitAndVisible()) {
                onLazyInitData()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")

        // 已经完成初始化
        mInit = true
        // 加载数据
        if (useLazyLoad()) {
            if (hasInitAndVisible()) {
                onLazyInitData()
            }
        } else {
            val tracker = mContext as ITracker
            tracker.childPageName = pageName

            initData()
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("onDestroyView")

        //防止窗口泄漏
        val requestDialog = getLoadingDialog() as Dialog?
        if (requestDialog != null && requestDialog.isShowing) {
            requestDialog.cancel()
        }

        unregister(this)

        removeAllDisposable()

        mUnBinder.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.i("onDetach")
    }
    //</editor-fold >


    //<editor-fold desc="任务处理">
    override fun addDisposable(taskId: Int, disposable: Disposable) {
        mContext.let {
            if (it is ITask) {
                it.addDisposable(taskId, disposable)
            }
        }
    }

    override fun removeDisposable(taskId: Int?) {
        mContext.let {
            if (it is ITask) {
                it.removeDisposable(taskId)
            }
        }
    }

    override fun removeAllDisposable() {
        mContext.let {
            if (it is ITask) {
                it.removeAllDisposable()
            }
        }
    }
    //</editor-fold >

    //<editor-fold desc="事件总线">
    //限定界面
    protected fun <T> inCurrentPage(event: BaseEvent<T>): Boolean {
        return pageName == event.data
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: BaseEvent<T>) {
        if (!isEmpty(event.delayMessage)) {
            //延迟消息
            mDelayMessages.add(event.delayMessage)
        } else {
            if ("refresh" == event.message && inCurrentPage(event)) {
                //限定界面
                initData()
            } else {
                onReceiveEvent(event)
            }
        }
    }

    override fun <T : Any?> onReceiveEvent(event: BaseEvent<T>) {
    }
    //</editor-fold >

    //<editor-fold desc="IBaseFragment">
    override fun initLifecycleObserver(lifecycle: Lifecycle) {
        //将mPresenter作为生命周期观察者添加到lifecycle中
    }
    //</editor-fold >

    //<editor-fold desc="IBaseFragmentLazy">
    override fun hasInitAndVisible(): Boolean {
        return mInit && userVisibleHint
    }

    @CallSuper
    override fun onLazyInitData() {
        mContext.let {
            if (it is ITracker) {
                it.childPageName = pageName
            }
        }
        LogUtil.e(mTagLog, "mInit:$mInit,mVisible:$userVisibleHint")
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