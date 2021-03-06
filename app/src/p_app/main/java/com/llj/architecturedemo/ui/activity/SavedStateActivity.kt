package com.llj.architecturedemo.ui.activity

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.application.router.CRouter
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.databinding.ActivitySavedStateBinding
import timber.log.Timber

/**
 * describe
 *
 * @author
 * @date
 */
@Route(path = CRouter.APP_SAVED_STATE_ACTIVITY)
class SavedStateActivity : MainMvcBaseActivity<ActivitySavedStateBinding>() {

  private val mViewModel: SavedStateViewModel by viewModels()

  override fun getIntentData(intent: Intent) {
    super.getIntentData(intent)
    //ViewModelProvider(this).get(SavedStateViewModel::class.java)
    //发起请求
  }

  fun transform(color: String?): Int {
    return when (color) {
      null -> 0
      "Red" -> 0
      "Green" -> 1
      "Blue" -> 2
      else -> throw IllegalArgumentException("Invalid color param value")
    }
  }

  object Resource {
    val name = "Name"
  }

  override fun initViews(savedInstanceState: Bundle?) {

    val items = mutableListOf("apple", "banana", "kiwifruit", null)

    items.filter { !it.isNullOrEmpty() }.map { }

    items[0]?.let { print(it) } ?: { print("") }

    if ("john@example.com" in items) {
    }

    val map = hashMapOf("a" to 1, "b" to 2, "c" to 3)
    for ((k, v) in map) {
      println("$k -> $v")
    }

    val name = mViewModel.mHandle.get<String>(SavedStateViewModel.USER_NAME)
    Timber.tag(mTagLog).e("name:%s", nullToEmpty(name))

    mViewBinder.tvClick.setOnClickListener {
      mViewModel.mHandle.set(SavedStateViewModel.USER_NAME, "liulinjie")
    }

    //使用getLiveData来监听数据，更加方便
    mViewBinder.tvClick2.setOnClickListener {
      mViewModel.userId1.value = "123456"

    }
    mViewModel.userId1.observe(this, Observer {
      Timber.tag(mTagLog).e("userId:%s", it)
    })
  }

  override fun initData() {

  }

  class SavedStateViewModel(private val mApplication: Application,
                            val mHandle: SavedStateHandle) : AndroidViewModel(mApplication) {
    companion object {
      const val USER_ID = "userId"
      const val USER_NAME = "userName"
    }

    private val _userInfo = MutableLiveData<String>()
    val userInfo = _userInfo


    val userName: MutableLiveData<String> by lazy { mHandle.getLiveData(USER_ID) }


    val userId1: MutableLiveData<String>

    init {
      userId1 = mHandle.getLiveData(USER_ID)
    }


    fun getUserId2(): MutableLiveData<String> {
      return mHandle.getLiveData(USER_ID)
    }

    fun getUserId3() = mHandle.getLiveData<String>(USER_ID)

    val userId4
      get() = mHandle.getLiveData<String>(USER_ID)


    var userId5: MutableLiveData<String>? = null


    var userId6: MutableLiveData<String>? = null
      set(value) {
        Timber.tag("mTagLog").e("userId:")
      }

    var userId7: MutableLiveData<String>?
      get() = mHandle.getLiveData<String>(USER_ID)
      set(value) {
        Timber.tag("mTagLog").e("userId:")
      }

    var userId8: MutableLiveData<String>? = null
      get() = mHandle.getLiveData<String>(USER_ID)
      set(value) {
        field = value
      }


  }
}