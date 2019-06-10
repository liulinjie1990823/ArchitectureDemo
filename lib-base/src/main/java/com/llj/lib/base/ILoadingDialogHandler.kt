package com.llj.lib.base

import com.llj.lib.net.observer.ITaskId

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * ArchitectureDemo
 * describe: 供activity控制dialog
 * @author llj
 * @date 2018/5/24
 */
interface ILoadingDialogHandler : Consumer<Disposable>, Action, ITaskId {

    fun getLoadingDialog(): ITaskId?

    fun initLoadingDialog(): ITaskId?

    fun showLoadingDialog() {}

    fun dismissLoadingDialog() {}

    //显示
    override fun accept(disposable: Disposable?) {
        showLoadingDialog()
    }

    //隐藏
    override fun run() {
        dismissLoadingDialog()
    }
}
