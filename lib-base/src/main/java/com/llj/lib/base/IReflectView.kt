package com.llj.lib.base

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import timber.log.Timber
import java.lang.reflect.ParameterizedType

/**
 * describe ViewBinding解析类
 *
 * @author liulinjie
 * @date 3/6/21 1:13 PM
 */
interface IReflectView<V : ViewBinding> {

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

  fun layoutViewBinding(): V? {
    return null
  }
}