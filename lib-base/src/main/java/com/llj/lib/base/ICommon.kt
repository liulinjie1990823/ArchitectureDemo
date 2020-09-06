package com.llj.lib.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import timber.log.Timber
import java.lang.reflect.ParameterizedType

/**
 * ArchitectureDemo
 * describe:通用的方法
 * @author llj
 * @date 2018/5/24
 */
interface ICommon<V : ViewBinding> {

  fun reflectViewBinder(layoutInflater: LayoutInflater, mTagLog: String): V? {
    Timber.tag(mTagLog).i("------------------------start-------------------------")
    val currentTimeMillis: Long = System.currentTimeMillis()
    var classType: Class<*>? = javaClass
    for (i in 0..10) {
      if (classType == null) {
        break
      }
      if (classType.genericSuperclass is ParameterizedType) {
        //父类是泛型类型就反射一次
        Timber.tag(mTagLog).i("Lifecycle %s（%d）layoutViewBinding reflect class %s",
            mTagLog, hashCode(), classType.genericSuperclass!!.toString())
        val reflectOnce = reflectOnce(classType.genericSuperclass as ParameterizedType, layoutInflater, mTagLog)
        if (reflectOnce != null) {
          val diffTimeMillis = System.currentTimeMillis() - currentTimeMillis
          Timber.tag(mTagLog).i("Lifecycle %s（%d）layoutViewBinding reflect success cost：%d ms",
              mTagLog, hashCode(), diffTimeMillis)
          return reflectOnce
        }
      }
      classType = classType.superclass
    }
    val diffTimeMillis = System.currentTimeMillis() - currentTimeMillis
    Timber.tag(mTagLog).i("Lifecycle %s（%d）layoutViewBinding reflect nothing cost：%d ms", mTagLog,
        hashCode(), diffTimeMillis)
    return null
  }

  private fun reflectOnce(type: ParameterizedType, layoutInflater: LayoutInflater, mTagLog: String): V? {
    var viewBinder: V? = null
    try {
      val clazz = type.actualTypeArguments[0] as Class<V>
      Timber.tag(mTagLog).i("Lifecycle %s（%d）layoutViewBinding reflect generic type %s", mTagLog, hashCode(), clazz)
      val method = clazz.getMethod("inflate", LayoutInflater::class.java)
      viewBinder = method.invoke(null, layoutInflater) as V
    } catch (e: Exception) {
      e.printStackTrace()
      Timber.tag(mTagLog).i("Lifecycle %s（%d）layoutViewBinding reflect failed：%s", mTagLog, hashCode(), e.message)
      Timber.tag(mTagLog).i("------------------------failed-------------------------")
    }
    return viewBinder
  }

  fun getIntentData(intent: Intent) {}

  fun getArgumentsData(bundle: Bundle) {}

  fun layoutView(): View? {
    return null
  }

  fun layoutViewBinding(): V? {
    return null
  }

  @LayoutRes
  fun layoutId(): Int

  fun initViews(savedInstanceState: Bundle?)

  fun initData()
}
