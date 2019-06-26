package com.llj.lib.base

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/13
 */
abstract class BaseFragment :android.support.v4.app.DialogFragment()
        ,IBaseFragment, ICommon, IUiHandler, IEventK, ILoadingDialogHandler, ITask  {
}