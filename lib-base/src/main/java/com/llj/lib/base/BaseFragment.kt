package com.llj.lib.base

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/13
 */
abstract class BaseFragment :android.support.v4.app.Fragment()
        ,IBaseFragment, ICommon, IUiHandler, IEvent, ILoadingDialogHandler, ITask  {
}