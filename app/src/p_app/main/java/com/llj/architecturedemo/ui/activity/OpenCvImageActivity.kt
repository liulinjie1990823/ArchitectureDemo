package com.llj.architecturedemo.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.databinding.ActivityOpencvImageBinding
import com.llj.application.router.CRouterClassName
import com.llj.lib.base.help.FilePathHelper
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.opencv.osgi.OpenCVNativeLoader
import java.io.File


/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/5/20 9:25 AM
 */
@Route(path = CRouterClassName.APP_OPENCV_IMAGE_ACTIVITY)
class OpenCvImageActivity : MainMvcBaseActivity<ActivityOpencvImageBinding>() {

  private lateinit var mBitmap: Bitmap

  override fun initViews(savedInstanceState: Bundle?) {
    val options = BitmapFactory.Options()
    options.inSampleSize = 4
    mBitmap = BitmapFactory.decodeResource(resources, R.drawable.test_10087, options)

    Log.i(mTagLog, "mBitmap:" + mBitmap.width + "*" + mBitmap.height)

    mViewBinder.image.setImageBitmap(mBitmap)

  }

  override fun onResume() {
    super.onResume()

    val loaderCallback = object : BaseLoaderCallback(this) {}

//    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, loaderCallback)

    OpenCVNativeLoader().init()
  }


  override fun initData() {

    mViewBinder.tvProcess.setOnClickListener {


      val srcMat = Mat()
      val dstMat = Mat()
      Utils.bitmapToMat(mBitmap, srcMat)

      processImg(R.id.image1, Imgproc.INTER_NEAREST, srcMat, dstMat)
      processImg(R.id.image2, Imgproc.INTER_LINEAR, srcMat, dstMat)
      processImg(R.id.image3, Imgproc.INTER_CUBIC, srcMat, dstMat)
      processImg(R.id.image4, Imgproc.INTER_AREA, srcMat, dstMat)
      processImg(R.id.image5, Imgproc.INTER_LANCZOS4, srcMat, dstMat)
      processImg(R.id.image6, Imgproc.INTER_LINEAR_EXACT, srcMat, dstMat)
//      processImg(R.id.image7, Imgproc.INTER_MAX, srcMat, dstMat)
//      processImg(R.id.image8, Imgproc.WARP_FILL_OUTLIERS, srcMat, dstMat)
//      processImg(R.id.image9, Imgproc.WARP_INVERSE_MAP, srcMat, dstMat)
    }
  }

  private var mOutput: File? = null
  private var mCurrent: Long = 0
  private fun processImg(imageId: Int, interpolation: Int, srcMat: Mat, dstMat: Mat) {
    mCurrent = System.currentTimeMillis()
    Imgproc.resize(srcMat, dstMat, org.opencv.core.Size((srcMat.width() / 4.0), (srcMat.height() / 4.0)), 0.0, 0.0, interpolation)
    val dstBitmap: Bitmap = Bitmap.createBitmap(dstMat.width(), dstMat.height(), Bitmap.Config.ARGB_8888)
    Utils.matToBitmap(dstMat, dstBitmap)

    mOutput = File(FilePathHelper.CACHE_PATH + "/" + getInterpolation(interpolation) + ".jpg")

    val temp = System.currentTimeMillis() - mCurrent

    Log.i(mTagLog, "dstBitmap resize" + getInterpolation(interpolation) + ":" + temp + "ms")
    Log.i(mTagLog, "dstBitmap:" + dstBitmap.width + "*" + dstBitmap.height)

    findViewById<ImageView>(imageId).setImageBitmap(dstBitmap)
  }


  private fun getInterpolation(interpolation: Int): String {
    var name: String = ""
    when (interpolation) {
      0 -> {
        name = "Imgproc.INTER_NEAREST"
      }
      1 -> {
        name = "Imgproc.INTER_LINEAR"
      }
      2 -> {
        name = "Imgproc.INTER_CUBIC"
      }
      3 -> {
        name = "Imgproc.INTER_AREA"
      }
      4 -> {
        name = "Imgproc.INTER_LANCZOS4"
      }
      5 -> {
        name = "Imgproc.INTER_LINEAR_EXACT"
      }
      7 -> {
        name = "Imgproc.INTER_MAX"
      }
      8 -> {
        name = "Imgproc.WARP_FILL_OUTLIERS"
      }
      16 -> {
        name = "Imgproc.INTER_LINEAR_EXACT"
      }
    }
    return name
  }


}