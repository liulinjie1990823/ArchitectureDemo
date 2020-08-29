/*
 * Copyright © 2020, Beijing Jinhaiqunying, Co,. Ltd. All Rights Reserved.
 * Copyright Notice
 * Jinhaiqunying copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 * without the prior written consent of Jinhaiqunying.
 * Disclaimer
 * This specification is preliminary and is subject to change at any time without notice.
 * Jinhaiqunying assumes no responsibility for any errors contained herein.
 *
 */

package com.llj.lib.base.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * WeddingBazaar.
 * describe:
 * @author llj
 * @date 2020/8/19
 */
class MvViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    var creator: Provider<out ViewModel>? = creators[modelClass]
    if (creator == null) {
      for ((key, value) in creators) {
        if (modelClass.isAssignableFrom(key)) {
          creator = value
          break
        }
      }
    }
    if (creator == null) {
      throw IllegalArgumentException("Unknown model class: $modelClass")
    }
    try {
      @Suppress("UNCHECKED_CAST")
      return creator.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }
}

@Module
 abstract class ViewModelBuilder {
  @Binds
  internal abstract fun bindViewModelFactory(factory: MvViewModelFactory): ViewModelProvider.Factory
}

@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)