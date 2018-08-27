package com.llj.lib.base

import com.llj.lib.net.observer.ITag

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * ArchitectureDemo
 * describe: 供activity和dialog使用，
 * author llj
 * date 2018/5/24
 */
interface ILoadingDialogHandler : Consumer<Disposable>, Action, ITag {

    fun getLoadingDialog(): ITag?

    fun initLoadingDialog(): ITag?

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
