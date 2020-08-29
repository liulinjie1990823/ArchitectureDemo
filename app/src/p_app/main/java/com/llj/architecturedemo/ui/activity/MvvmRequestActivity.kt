package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.AppMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.databinding.ActivityRequestBinding
import com.llj.architecturedemo.vm.MainContractViewModel
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.LayoutId
import javax.inject.Inject


/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * @date 2018/9/20
 */
@Route(path = CRouter.APP_MVVM_REQUEST_ACTIVITY)
@LayoutId(id = R.layout.activity_request)
class MvvmRequestActivity : AppMvcBaseActivity<ActivityRequestBinding>() {

  @Inject
  lateinit var mViewModelFactory: ViewModelProvider.Factory
  private val mViewModel by viewModels<MainContractViewModel> { mViewModelFactory }

  override fun initViews(savedInstanceState: Bundle?) {
    mViewBinder.tvRequest.setOnClickListener {
      val hashMap = HashMap<String, Any>()
      hashMap["phone"] = "13188888888"
      hashMap["requestId"] = "1"
      mViewModel.setQuery(hashMap)

    }

    mViewModel.mobile.observe(this, Observer {
      it?.data?.run {
        showLongToast(this.phone)
      }
    })
  }

  override fun initData() {
    val hashMap = HashMap<String, Any>()
    hashMap["phone"] = "13188888888"
    hashMap["requestId"] = "1"
    mViewModel.setQuery(hashMap)
  }


}