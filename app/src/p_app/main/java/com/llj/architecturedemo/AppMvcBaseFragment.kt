package com.llj.architecturedemo

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvcBaseFragment

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
abstract class AppMvcBaseFragment<V : ViewBinding> : MiddleMvcBaseFragment<V>()
