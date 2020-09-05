package com.llj.loading.flutter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.idlefish.flutterboost.containers.CustomBoostFlutterActivity


class PageRouter {

  companion object {

    val NATIVE_PAGE_URL = "sample://nativePage"
    val FLUTTER_PAGE_URL = "sample://flutterPage"
    val FLUTTER_MY_INV_PAGE_URL = "sample://flutter/myInvPage"
    val FLUTTER_MY_LOGIN_PAGE_URL = "sample://flutter/loginPage"
    val FLUTTER_MY_MAIN_PAGE_URL = "sample://flutter/mainPage"
    val FLUTTER_FRAGMENT_PAGE_URL = "sample://flutterFragmentPage"

    val pageName: Map<String, String> = object : HashMap<String, String>() {
      init {
        put("first", "first")
        put("second", "second")
        put("tab", "tab")
        put("sample://flutterPage", "flutterPage")
        put(FLUTTER_MY_INV_PAGE_URL, "myInvPage")
        put(FLUTTER_MY_LOGIN_PAGE_URL, "loginPage")
        put(FLUTTER_MY_MAIN_PAGE_URL, "mainPage")
      }
    }


    fun openPageByUrl(context: Context, url: String, params: Map<*, *>?): Boolean {
      return openPageByUrl(context, url, params, 0)
    }

    fun openPageByUrl(context: Context, url: String, params: Map<*, *>?, requestCode: Int): Boolean {
      val path = url.split("\\?".toRegex()).toTypedArray()[0]
      Log.i("openPageByUrl", path)

      return try {
        if (pageName.containsKey(path)) {
          //flutter page
          val intent: Intent = CustomBoostFlutterActivity.withNewEngine().url(pageName[path]!!).params(params!!)
              .backgroundMode(CustomBoostFlutterActivity.BackgroundMode.opaque).build(context)
          if (context is Activity) {
            context.startActivityForResult(intent, requestCode)
          } else {
            context.startActivity(intent)
          }
          return true
        } else if (url.startsWith(FLUTTER_FRAGMENT_PAGE_URL)) {
          //flutter fragment
//          context.startActivity(Intent(context, FlutterFragmentPageActivity::class.java))
          return true
        } else if (url.startsWith(NATIVE_PAGE_URL)) {
          //native page
          context.startActivity(Intent(context, NativePageActivity::class.java))
          return true
        }
        false
      } catch (t: Throwable) {
        false
      }
    }

  }

}