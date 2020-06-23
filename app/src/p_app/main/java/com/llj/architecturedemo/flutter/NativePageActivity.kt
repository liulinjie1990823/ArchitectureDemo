package com.llj.architecturedemo.flutter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.llj.architecturedemo.R
import com.llj.lib.component.api.finder.Utils.findViewById


class NativePageActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var mOpenNative: TextView
  private lateinit var mOpenFlutter: TextView
  private lateinit var mOpenFlutterFragment: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.native_page)
    mOpenNative = findViewById(R.id.open_native)
    mOpenFlutter = findViewById(R.id.open_flutter)
    mOpenFlutterFragment = findViewById(R.id.open_flutter_fragment)
    mOpenNative.setOnClickListener(this)
    mOpenFlutter.setOnClickListener(this)
    mOpenFlutterFragment.setOnClickListener(this)
  }

  override fun onClick(v: View) {
    val params: MutableMap<Any, Any> = HashMap()
    params["test1"] = "v_test1"
    params["test2"] = "v_test2"
    if (v === mOpenNative) {
      PageRouter.openPageByUrl(this, PageRouter.NATIVE_PAGE_URL, params)
    } else if (v === mOpenFlutter) {
      PageRouter.openPageByUrl(this, PageRouter.FLUTTER_MY_INV_PAGE_URL, params)
    } else if (v === mOpenFlutterFragment) {
      PageRouter.openPageByUrl(this, PageRouter.FLUTTER_FRAGMENT_PAGE_URL, params)
    }
  }
}