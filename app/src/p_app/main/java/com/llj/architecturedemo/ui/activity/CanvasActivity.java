package com.llj.architecturedemo.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.adapter.ListBasedAdapter;
import com.llj.adapter.UniversalBind;
import com.llj.adapter.util.ViewHolderHelper;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.databinding.ActivityCanvasBinding;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.help.DisplayHelper;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/4/17 5:44 PM
 */
@SuppressWarnings("ConstantConditions")
@Route(path = CRouter.APP_CANVAS_ACTIVITY)
public class CanvasActivity extends AppMvcBaseActivity<ActivityCanvasBinding> {

  @Nullable
  @Override
  public ActivityCanvasBinding layoutViewBinding() {
    return ActivityCanvasBinding.inflate(getLayoutInflater());
  }


  @Override
  public int layoutId() {
    return R.layout.activity_canvas;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    ((ConstraintLayout) mViewBinder.getRoot()).addView(new CanvasView(mContext), new LayoutParams(
        DisplayHelper.SCREEN_WIDTH, DisplayHelper.SCREEN_HEIGHT));
  }

  @Override
  public void initData() {

    ArrayList<Data> data = new ArrayList<>();
    data.add(new Data());
    data.add(new Data());
    ItemAdapter dataAdapter = new UniversalBind.Builder<Data, ViewHolderHelper, ItemAdapter>(
        mViewBinder.recyclerView, new ItemAdapter(data))
        .setLinearLayoutManager()
        .build().getAdapter();

  }

  public class CanvasView extends View {

    public CanvasView(Context context) {
      super(context);
      mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.androids);
    }

    Paint mPaint = new Paint();
    final int mSpace = 100;   //长宽间隔

    ColorDrawable colorDrawable = new ColorDrawable(getCompatColor(mContext, R.color.gray));

    Bitmap mBitmap;

    private void drawGrid(Canvas canvas) {
      canvas.restoreToCount(1);
      //绘制网格
      mPaint.setColor(Color.BLACK);
      mPaint.setStrokeWidth(3);
      final int width = DisplayHelper.SCREEN_WIDTH;
      final int height = DisplayHelper.SCREEN_HEIGHT;
      int vertz = 0;
      int hortz = 0;
      for (int i = 0; i < 100; i++) {
        canvas.drawLine(0, vertz, width, vertz, mPaint);//画横线
        canvas.drawLine(hortz, 0, hortz, height, mPaint);//画竖线
        vertz += mSpace;
        hortz += mSpace;
      }
    }

    @Override
    protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      //设置背景色
      canvas.drawColor(getCompatColor(mContext, R.color.khaki));
      canvas.save();

      mPaint.setAntiAlias(true);

      //设置画布颜色
      //colorDrawable
      //    .setBounds(0, 0, this.getRight() - this.getLeft(),
      //        (int) ((this.getBottom() - this.getTop()) * 2 / 3f));
      //colorDrawable.draw(canvas);
      //

      //将坐标系原点移动到画布正中心
      //canvas.translate((DisplayHelper.SCREEN_WIDTH / mSpace) / 2 * mSpace,
      //    (DisplayHelper.SCREEN_HEIGHT / mSpace) / 2 * mSpace);
      //
      //RectF rect = new RectF(0, -400, 400, 0);   // 矩形区域
      //
      //mPaint.setColor(Color.BLACK); // 绘制黑色矩形
      //canvas.drawRect(rect, mPaint);
      //
      //canvas.scale(0.5f, 0.5f);// 画布缩放
      //
      //mPaint.setColor(Color.RED); // 绘制蓝色矩形
      //canvas.drawRect(rect, mPaint);
      //
      //mPaint.setColor(getCompatColor(mContext, R.color.grey));
      //canvas.drawCircle(0, 0, 100, mPaint);//绘制圆形

      //drawBitmap(@NonNull Bitmap bitmap, @Nullable Rect src, @NonNull Rect dst,@Nullable Paint paint)
      Rect mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
      Rect mDestRect = new Rect(100, 200, mBitmap.getWidth(), mBitmap.getHeight());
      canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint);

      // drawBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix, @Nullable Paint paint)
      Matrix matrix = new Matrix();
      matrix.setTranslate((DisplayHelper.SCREEN_WIDTH / mSpace) / 2 * mSpace,
          (DisplayHelper.SCREEN_HEIGHT / mSpace) / 2 * mSpace);
      matrix.postRotate(90, (DisplayHelper.SCREEN_WIDTH / mSpace) / 2 * mSpace,
          (DisplayHelper.SCREEN_HEIGHT / mSpace) / 2 * mSpace);
      canvas.drawBitmap(mBitmap, matrix, mPaint);

      RectF rect = new RectF(0, 0, 400, 400);   // 矩形区域
      mPaint.setColor(Color.BLACK); // 绘制黑色矩形
      canvas.drawRect(rect, mPaint);

      //drawRect
      //RectF rect = new RectF(0, 0, 300, 300);   // 矩形区域
      //mPaint.setColor(Color.BLACK); // 绘制黑色矩形
      //mPaint.setStyle(Style.FILL);
      //canvas.drawRect(rect,mPaint);
      //
      //
      //canvas.translate(300,300);
      //mPaint.setColor(Color.RED);
      //mPaint.setStyle(Style.STROKE);
      //mPaint.setStrokeWidth(50);
      //canvas.drawRect(rect,mPaint);
      //
      //
      //canvas.translate(300,300);
      //mPaint.setColor(Color.BLUE);
      //mPaint.setStyle(Style.FILL_AND_STROKE);
      //mPaint.setStrokeWidth(50);
      //canvas.drawRect(rect,mPaint);

      //// 在（200，200）位置绘制一个黑色圆形
      //mPaint.setColor(Color.BLACK);
      //canvas.translate(200, 200);
      //canvas.drawCircle(0, 0, 100, mPaint);
      //
      //// 在上一个坐标原点的基础上，xy轴又偏移了200px
      //mPaint.setColor(Color.BLUE);
      //canvas.translate(200, 200);
      //canvas.drawCircle(0, 0, 100, mPaint);

      drawGrid(canvas);
    }
  }

  private class ItemAdapter extends ListBasedAdapter<Data, ViewHolderHelper> {

    ItemAdapter(@Nullable List<Data> list) {
      super(list);
      addItemLayout(R.layout.item_canvas);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderHelper viewHolder, @Nullable Data item,
        int position) {
      if (item == null) {
        return;
      }
      TextView name = viewHolder.getView(R.id.tv_text);
      setText(name, item.name);

    }
  }

  class Data {

    public String name;
    public String type;

    public Data() {
    }

    public Data(String name, String type) {
      this.name = name;
      this.type = type;
    }
  }


  interface Source<T> {

    T nextT();
  }

  // Java
  void demo(Source<String> strs) {
    Source<? extends Object> objects = strs; // ！！！在 Java 中不允许
    // ……
  }
}
