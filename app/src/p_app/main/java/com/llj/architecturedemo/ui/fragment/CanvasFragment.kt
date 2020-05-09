package com.llj.architecturedemo.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.llj.architecturedemo.AppMvcBaseFragment
import com.llj.architecturedemo.R
import com.llj.architecturedemo.databinding.FragmentCanvasBinding
import com.llj.architecturedemo.ui.activity.CanvasActivity
import com.llj.component.service.arouter.CRouter
import com.llj.lib.base.help.DisplayHelper
import timber.log.Timber


/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/4/18 8:55 PM
 */
@Route(path = CRouter.APP_CANVAS_FRAGMENT)
class CanvasFragment : AppMvcBaseFragment<FragmentCanvasBinding>() {

  @Autowired(name = CRouter.KEY_TYPE) @JvmField var mType: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NO_TITLE, R.style.no_dim_dialog)
  }

  override fun initViews(savedInstanceState: Bundle?) {
    super.initViews(savedInstanceState)
    ARouter.getInstance().inject(this);
    val layoutParams = ConstraintLayout.LayoutParams(DisplayHelper.SCREEN_WIDTH, DisplayHelper.SCREEN_HEIGHT)
    mViewBinder!!.root.addView(CanvasView(mContext), layoutParams)
  }

  override fun initData() {
    Timber.tag(mTagLog).i("DisplayHelper.SCREEN_HEIGHT：%d", DisplayHelper.SCREEN_HEIGHT)
  }

  inner class CanvasView : View {

    constructor(context: Context?) : super(context) {
      this.mPaint = Paint()
      this.colorDrawable = ColorDrawable(getCompatColor(mContext, R.color.gray))
      mBitmap = BitmapFactory.decodeResource(resources, R.drawable.androids)
    }

    var mPaint: Paint
    val mSpace = 100 //长宽间隔
    var colorDrawable: ColorDrawable
    var mBitmap: Bitmap

    private fun drawGrid(canvas: Canvas) {
      canvas.restoreToCount(1)
      //绘制网格
      mPaint.color = Color.BLACK
      mPaint.strokeWidth = 3f
      val width = DisplayHelper.SCREEN_WIDTH
      val height = DisplayHelper.SCREEN_HEIGHT
      var vertz = 0
      var hortz = 0
      for (i in 0..99) {
        canvas.drawLine(0f, vertz.toFloat(), width.toFloat(), vertz.toFloat(), mPaint) //画横线
        canvas.drawLine(hortz.toFloat(), 0f, hortz.toFloat(), height.toFloat(), mPaint) //画竖线
        vertz += mSpace
        hortz += mSpace
      }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
      super.onDraw(canvas)
      //设置背景色
      canvas.drawColor(getCompatColor(mContext, R.color.khaki))
      canvas.save()
      mPaint.isAntiAlias = true

//      CanvasActivity.callStatic()
//      CanvasActivity.callNonStatic()
//      var drawRect:Int = CanvasActivity.drawRect
//      CanvasActivity.drawRoundRect
//      CanvasActivity.drawCircle
//      CanvasActivity.drawOval
//      CanvasActivity.drawArc
//      CanvasActivity.drawLine
//      CanvasActivity.drawLines

      when (mType) {
        CanvasActivity.drawRect -> {
          //drawRect
          val rect = Rect(0, 0, 300, 300)
          mPaint.color = Color.BLACK; // 绘制黑色矩形
          mPaint.style = Paint.Style.FILL;
          canvas.drawRect(rect, mPaint);


          canvas.translate(300f, 300f);
          mPaint.color = Color.RED;
          mPaint.style = Paint.Style.STROKE;
          mPaint.strokeWidth = 50f;
          canvas.drawRect(rect, mPaint);


          canvas.translate(300f, 300f);
          mPaint.color = Color.BLUE;
          mPaint.style = Paint.Style.FILL_AND_STROKE;
          mPaint.strokeWidth = 50f;
          canvas.drawRect(rect, mPaint);
        }
        CanvasActivity.drawRoundRect -> {
          //drawRoundRect
          val rect = RectF(0F, 0F, 300F, 300F)
          mPaint.color = Color.BLACK; // 绘制黑色矩形
          mPaint.style = Paint.Style.FILL;
          canvas.drawRoundRect(rect, 20f, 40f, mPaint);


          canvas.translate(300f, 300f);
          mPaint.color = Color.RED;
          mPaint.style = Paint.Style.STROKE;
          mPaint.strokeWidth = 50f;
          canvas.drawRoundRect(rect, 20f, 40f, mPaint);


          canvas.translate(300f, 300f);
          mPaint.color = Color.BLUE;
          mPaint.style = Paint.Style.FILL_AND_STROKE;
          mPaint.strokeWidth = 50f;
          canvas.drawRoundRect(rect, 20f, 40f, mPaint);
        }
        CanvasActivity.drawCircle -> {
        }
        CanvasActivity.drawOval -> {
        }
        CanvasActivity.drawArc -> {
        }
        CanvasActivity.drawLine -> {
        }
        CanvasActivity.drawLines -> {
        }
        CanvasActivity.drawPath -> {

        }
        CanvasActivity.drawBitmap1 -> {
          canvas.drawBitmap(mBitmap, 100f, 200f, mPaint)
        }
        CanvasActivity.drawBitmap2 -> {
          //drawBitmap(@NonNull Bitmap bitmap, @Nullable Rect src, @NonNull Rect dst,@Nullable Paint paint)
          val mSrcRect = Rect(0, 0, mBitmap.width, mBitmap.height)
          val mDestRect = Rect(100, 200, mBitmap.width, mBitmap.height)
          canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint)
        }
        CanvasActivity.drawBitmap3 -> {
          // drawBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix, @Nullable Paint paint)
          val matrix = Matrix()
          matrix.setTranslate(DisplayHelper.SCREEN_WIDTH / mSpace / 2 * mSpace.toFloat(),
              DisplayHelper.SCREEN_HEIGHT / mSpace / 2 * mSpace.toFloat())
          matrix.postRotate(90f, DisplayHelper.SCREEN_WIDTH / mSpace / 2 * mSpace.toFloat(),
              DisplayHelper.SCREEN_HEIGHT / mSpace / 2 * mSpace.toFloat())
          canvas.drawBitmap(mBitmap, matrix, mPaint)

          val rect = Rect(0, 0, 400, 400) // 矩形区域
          mPaint.color = Color.BLACK // 绘制黑色矩形
          canvas.drawRect(rect, mPaint)
        }
        CanvasActivity.translate -> {
          canvas.translate(((DisplayHelper.SCREEN_WIDTH / mSpace) / 2 * mSpace).toFloat(),
              ((DisplayHelper.SCREEN_HEIGHT / mSpace) / 2 * mSpace).toFloat());
          val rect = Rect(0, -400, 400, 0);   // 矩形区域
          mPaint.color = Color.BLACK; // 绘制黑色矩形
          canvas.drawRect(rect, mPaint);
        }
        CanvasActivity.scale -> {
          //将坐标系原点移动到画布正中心
          canvas.translate((DisplayHelper.SCREEN_WIDTH / 2).toFloat(), (DisplayHelper.SCREEN_HEIGHT / 2).toFloat())

          val rect = RectF(0f, -400f, 400f, 0f) // 矩形区域

          mPaint.color = Color.BLACK // 绘制黑色矩形
          canvas.drawRect(rect, mPaint)

          canvas.scale(0.5f, 0.5f) // 画布缩放

          mPaint.color = Color.RED // 绘制蓝色矩形
          canvas.drawRect(rect, mPaint)

          mPaint.color = Color.GRAY //  //绘制圆形
          canvas.drawCircle(0f, 0f, 100f, mPaint)
        }
        CanvasActivity.rotate -> {
          canvas.translate((DisplayHelper.SCREEN_WIDTH / 2).toFloat(), (DisplayHelper.SCREEN_HEIGHT / 2).toFloat())
          canvas.drawBitmap(mBitmap, 0f, 0f, mPaint)

          canvas.rotate(90f, 0f, 0f)
          canvas.drawBitmap(mBitmap, 0f, 0f, mPaint)

        }
      }
      //设置画布颜色
      //colorDrawable
      //    .setBounds(0, 0, this.getRight() - this.getLeft(),
      //        (int) ((this.getBottom() - this.getTop()) * 2 / 3f));
      //colorDrawable.draw(canvas);
      //


      //// 在（200，200）位置绘制一个黑色圆形
      //mPaint.setColor(Color.BLACK);
      //canvas.translate(200, 200);
      //canvas.drawCircle(0, 0, 100, mPaint);
      //
      //// 在上一个坐标原点的基础上，xy轴又偏移了200px
      //mPaint.setColor(Color.BLUE);
      //canvas.translate(200, 200);
      //canvas.drawCircle(0, 0, 100, mPaint);
      drawGrid(canvas)
    }

  }

}