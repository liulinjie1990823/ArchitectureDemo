package com.llj.architecturedemo

import androidx.viewbinding.ViewBinding
import com.llj.component.service.MiddleMvcBaseActivity

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
abstract class AppMvcBaseActivity<V : ViewBinding> : MiddleMvcBaseActivity<V>()
