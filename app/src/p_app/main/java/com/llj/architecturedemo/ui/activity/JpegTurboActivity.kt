package com.llj.architecturedemo.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.databinding.ActivityJpegTurboBinding
import com.llj.application.router.CRouterClassName
import com.llj.lib.base.help.FilePathHelper
import com.llj.lib.jpeg.turbo.utils.JpegUtils
import java.io.File

@Route(path = CRouterClassName.APP_JPEG_TURBO_ACTIVITY)
class JpegTurboActivity : MainMvcBaseActivity<ActivityJpegTurboBinding>() {

  companion object {
    init {
      System.loadLibrary("turbojpeg-utils")
    }
  }

  private lateinit var mBitmap: Bitmap

  private var mOutput: File? = null
  private var mCurrent: Long = 0

  override fun initViews(savedInstanceState: Bundle?) {
    val options = BitmapFactory.Options()
    options.inSampleSize = 4
    mBitmap = BitmapFactory.decodeResource(resources, R.drawable.test_10087, options)
    mViewBinder.image.setImageBitmap(mBitmap)
    Log.i(mTagLog, "mBitmap:" + mBitmap.width + "*" + mBitmap.height)

    mOutput = File(FilePathHelper.CACHE_PATH + "/" + "jpeg_turbo" + ".jpg")

    mViewBinder.tvProcess.setOnClickListener {
      mCurrent = System.currentTimeMillis()
      val jpegUtils = JpegUtils()
      jpegUtils.nativeCompressBitmap(mBitmap, 95, mOutput!!.absolutePath)

      val temp = System.currentTimeMillis() - mCurrent

      showLongToast("cost" + temp + "ms")
      Log.i(mTagLog, "dstBitmap resize" + ":" + temp + "ms")
    }
  }

  override fun initData() {
  }
}