package com.llj.architecturedemo.ui.presenter

import android.arch.lifecycle.LifecycleOwner
import com.llj.architecturedemo.repository.HomeRepository
import com.llj.architecturedemo.ui.view.SecondView
import com.llj.lib.base.mvp.BaseActivityPresenter
import com.llj.lib.base.mvp.BaseRepository
import javax.inject.Inject

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/7/25
 */
class SecondPresenter @Inject constructor(repository: HomeRepository, view: SecondView)
    : BaseActivityPresenter<BaseRepository, SecondView>(repository, view) {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }
}