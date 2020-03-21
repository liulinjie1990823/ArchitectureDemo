package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import butterknife.ButterKnife
import butterknife.Unbinder
import com.llj.lib.base.event.BaseEvent
import com.llj.lib.base.widget.LoadingDialog
import com.llj.lib.utils.AFragmentUtils
import com.llj.lib.utils.AInputMethodManagerUtils
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * Fragment在 ViewPager中的表现
 *
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
 *
 * describe:Fragment基类，兼容DialogFragment
 * @author llj
 * @date 2018/8/15
 */
abstract class MvcBaseFragment : androidx.fragment.app.DialogFragment()
        , IBaseFragment, ICommon, IUiHandler, IEvent, ITask, ILoadingDialogHandler<BaseDialog> {
    val mTagLog: String = this.javaClass.simpleName

    lateinit var mContext: Context

    private var mInit: Boolean = false //是否已经初始化

    private lateinit var mUnBinder: Unbinder
    private var mRequestDialog: BaseDialog? = null

    private val mDelayMessages: androidx.collection.ArraySet<String> = androidx.collection.ArraySet()

    var mUseSoftInput: Boolean = false //是否使用软键盘

    //<editor-fold desc="对话框相关">
    private var mOnDismissListener: DialogInterface.OnDismissListener? = null
    private var mOnShowListener: DialogInterface.OnShowListener? = null

    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener) {
        mOnDismissListener = onDismissListener
    }

    fun setOnShowListener(onShowListener: DialogInterface.OnShowListener) {
        mOnShowListener = onShowListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BaseDialogImpl(activity!!, theme)
    }

    //<editor-fold desc="onShow">
    //如果是DialogFragment，会在该方法中显示dialog
    override fun onStart() {
        super.onStart()
        Timber.tag(mTagLog).i("onStart：%s", mTagLog)

        dispatchShowListener()

        showSoftInput()
    }

    private fun dispatchShowListener() {
        //设置回调方法
        if (dialog == null) {
            mOnShowListener?.onShow(null)
        } else {
            if (!(dialog?.isShowing!!)) {
                mOnShowListener?.onShow(dialog)
            }
        }
    }

    private fun showSoftInput() {
        //是否显示输入法
        if (!mUseSoftInput) {
            return
        }
        if (dialog == null) {
            activity?.window?.decorView?.post(Runnable {
                AInputMethodManagerUtils.showOrHideInput(activity, true)
            })
        } else {
            dialog?.window?.decorView?.post(Runnable {
                AInputMethodManagerUtils.showOrHideInput(dialog, true)
            })
        }
    }
    //</editor-fold>

    //<editor-fold desc="onDismiss">
    //如果有dialog，dialog的dismiss会回调该方法
    //不要设置dialog的setOnDismissListener，因为DialogFragment中会覆盖
    //在fragment中提供了mOnDismissListener的回调
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        dispatchDismissListener()
    }

    private fun dispatchDismissListener() {
        //设置回调方法
        mOnDismissListener?.onDismiss(dialog)
    }

    override fun dismissAllowingStateLoss() {
        hideSoftInput()
        super.dismissAllowingStateLoss()
    }

    private fun hideSoftInput() {
        if (!mUseSoftInput) {
            return
        }
        if (dialog == null) {
            AInputMethodManagerUtils.hideSoftInputFromWindow(activity)
        } else {
            AInputMethodManagerUtils.hideSoftInputFromWindow(dialog)
        }

    }
    //</editor-fold>

    //<editor-fold desc="smartDismiss">
    fun smartDismiss() {
        if (showsDialog) {
            dismissAllowingStateLoss()
        } else {
            hideSoftInput()
            AFragmentUtils.removeFragment(fragmentManager, this)
            dispatchDismissListener()
        }
    }
    //</editor-fold>


    //<editor-fold desc="smartShow">
    fun smartShow(manager: FragmentManager, tag: String) {
        smartShow(manager, tag, Window.ID_ANDROID_CONTENT)
    }

    fun smartShow(manager: FragmentManager, tag: String, @IdRes containerViewId: Int) {
        if (showsDialog) {
            try {
                super.show(manager, tag)
            } catch (ignore: IllegalStateException) {
                //  容错处理,不做操作
            }

        } else {
            //添加到ID_ANDROID_CONTENT里
            AFragmentUtils.addFragment(manager, containerViewId, this, tag)
        }
    }
    //</editor-fold>

    //<editor-fold desc="smartShowNow">
    fun smartShowNow(manager: FragmentManager, tag: String) {
        smartShowNow(manager, tag, Window.ID_ANDROID_CONTENT)
    }

    //1.先添加fragment
    //2.在fragment的生命周期的onStart中显示dialog
    fun smartShowNow(manager: FragmentManager, tag: String, @IdRes containerViewId: Int) {
        if (showsDialog) {
            try {
                super.showNow(manager, tag)
            } catch (ignore: IllegalStateException) {
                //  容错处理,不做操作
            }

        } else {
            //添加到ID_ANDROID_CONTENT里
            AFragmentUtils.addFragment(manager, containerViewId, this, tag)
        }
    }
    //</editor-fold>
    //</editor-fold >

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Timber.tag(mTagLog).i("setUserVisibleHint:%s,%s", isVisibleToUser, mTagLog)
        //当fragment在viewPager中的时候需要实现懒加载的模式
        //当使用viewPager进行预加载fragment的时候,先调用setUserVisibleHint,后调用onViewCreated
        //所以刚开始是mIsInit=true,mIsVisible为false

        // 加载数据
        if (useLazyLoad() && hasInitAndVisible()) {
            initData()
        }
    }

    //<editor-fold desc="生命周期">

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag(mTagLog).i("onAttach：%s", mTagLog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(mTagLog).i("onCreate：%s", mTagLog)

        //如果要修改可以在Fragment的构造函数中修改
        showsDialog = false

        //设置dialog的style
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.tag(mTagLog).i("onActivityCreated：%s", mTagLog)
        if (dialog == null || dialog?.window == null) {
            return
        }
        setWindowParams(dialog?.window!!, -1, -1, Gravity.CENTER)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.tag(mTagLog).i("onCreateView：%s", mTagLog)
        val layoutView = layoutView()
        val view = layoutView ?: inflater.inflate(layoutId(), null)

        mUnBinder = ButterKnife.bind(this, view)

        checkLoadingDialog()

        registerEventBus(this)

        initViews(savedInstanceState)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(mTagLog).i("onViewCreated：%s", mTagLog)

        // 已经完成初始化
        mInit = true
        // 加载数据
        if (useLazyLoad()) {
            if (hasInitAndVisible()) {
                Timber.tag(mTagLog).i("mInit：%s ,mVisible：%s", mInit, userVisibleHint)
                initData()
            }
        } else {
            initData()
        }
    }


    override fun onResume() {
        super.onResume()
        Timber.tag(mTagLog).i("onResume：%s", mTagLog)
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(mTagLog).i("onPause：%s", mTagLog)
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(mTagLog).i("onStop：%s", mTagLog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag(mTagLog).i("onDestroyView：%s", mTagLog)

        //防止窗口泄漏
        val requestDialog = getLoadingDialog() as Dialog?
        if (requestDialog != null && requestDialog.isShowing) {
            requestDialog.cancel()
            requestDialog.setOnCancelListener(null)
            dismissLoadingDialog()

            mRequestDialog = null
        }

        unregisterEventBus(this)

        removeAllDisposable()

        mUnBinder.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(mTagLog).i("onDestroy：%s", mTagLog)
    }

    override fun onDetach() {
        super.onDetach()
        Timber.tag(mTagLog).i("onDetach：%s", mTagLog)
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

    //一般用event.pageName和子Fragment中标记的pageName比对
    override fun <T : Any?> inCurrentPage(event: BaseEvent<T>): Boolean {
        return mTagLog == event.pageName
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

    //</editor-fold >

    //<editor-fold desc="ILoadingDialogHandler加载框">
    override fun getLoadingDialog(): BaseDialog? {
        return mRequestDialog
    }

    private fun checkLoadingDialog() {
        if (mRequestDialog == null) {
            mRequestDialog = initLoadingDialog()

            if (mRequestDialog == null) {
                mRequestDialog = LoadingDialog(mContext)
            }
        }
        setRequestId(hashCode())
    }

    //自定义实现
    override fun initLoadingDialog(): BaseDialog? {
        return null
    }

    override fun showLoadingDialog() {
        getLoadingDialog()?.show()
    }

    override fun dismissLoadingDialog() {
        getLoadingDialog()?.dismiss()
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