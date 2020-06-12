package com.llj.lib.base

import android.content.DialogInterface
import com.llj.lib.net.observer.ITaskId

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * ArchitectureDemo
 *
 * describe: 供activity控制dialog
 *
 * @author llj
 * @date 2018/5/24
 */
interface ILoadingDialogHandler<T> : Consumer<Disposable>, Action, ITaskId {

  fun getLoadingDialog(): T?

  fun initLoadingDialog(): T?

  fun showLoadingDialog()

  fun dismissLoadingDialog()

  //取消回调监听
  fun onLoadingDialogCancel(dialog: DialogInterface?)
  fun onLoadingDialogDismiss(dialog: DialogInterface?)

  //显示
  override fun accept(disposable: Disposable?) {
    showLoadingDialog()
  }

  //隐藏
  override fun run() {
    dismissLoadingDialog()
  }
}
