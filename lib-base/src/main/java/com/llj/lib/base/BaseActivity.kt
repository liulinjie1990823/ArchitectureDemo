package com.llj.lib.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/13
 */
abstract class BaseActivity :AppCompatActivity()
        , IBaseActivity, ICommon, IUiHandler, IEvent, ILoadingDialogHandler, ITask{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var annotations = this.javaClass.annotations
    }
}