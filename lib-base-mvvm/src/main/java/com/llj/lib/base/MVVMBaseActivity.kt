package com.llj.lib.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.llj.lib.base.mvvm.BaseViewModel
import com.llj.lib.base.widget.LoadingDialog
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 *
 * @author llj
 * @date 2018/6/30
 */
abstract class MVVMBaseActivity<V : ViewDataBinding, VM : BaseViewModel> : MvcBaseActivity<V>() {



}
