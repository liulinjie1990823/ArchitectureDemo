package com.llj.widget.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * WeddingBazaar
 * describe:
 * author liulj
 * date 2018/6/28
 */
public class CircleView extends View {
    //画笔
    private Paint mPaint;

    //圆的半径
    private float mRadius = 360f;

    //圆的圆心的x坐标
    private float pointX = mRadius;

    //圆的圆心的Y坐标
    private float pointY = mRadius;

    //控制是否可以移动的变量 true的时候可以移动
    private boolean moveable;

    private RectF mRectF = new RectF();


    public CircleView(Context context) {
        super(context, null);
    }

    //自定义veiw在布局中使用，必须实现的一个构造器
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //构造一个paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //根据圆心的坐标来绘制圆的位置的，而圆心的坐标，我们触摸屏幕的时候被我们修改了
//        canvas.drawCircle(pointX,pointY,mRadius,mPaint);

//        float x = 100f;
//        float y = 100f;
//        float left = 0f - x;
//        float top = 0f;
//        float right = 1080f + x;
//        float bottom = 800f;
//        RectF rectF = new RectF(left, top, right, bottom);
        // 绘制背景矩形
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(rectF,mPaint);
//        canvas.drawOval(mRectF, mPaint);
        canvas.drawArc(mRectF, 0, 180, true, mPaint);
    }

    public void setRectF(float left, float top, float right, float bottom, float xOffset, float yOffset) {
        mRectF.left = left - xOffset;
        mRectF.top = top - yOffset;
        mRectF.right = right + xOffset;
        mRectF.bottom = bottom + yOffset;

    }

    public void setColor(String beginColor, String endColor) {
//        mPaint.setShader(new LinearGradient(mRectF.left, mRectF.top, mRectF.right, mRectF.top,
//                Color.parseColor("#004A98"),
//                Color.parseColor("#458BD5"),
//                Shader.TileMode.CLAMP));

        mPaint.setShader(new LinearGradient(mRectF.left+100, mRectF.top, mRectF.right-100, mRectF.top,
                Color.parseColor(beginColor),
                Color.parseColor(endColor),
                Shader.TileMode.CLAMP));

        invalidate();
    }

    public RectF getRectF() {
        return mRectF;
    }


    //要单点拖动，保证手指在圆上的时候才移动，我们需要判断触摸的位置
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //手指触摸的x坐标
        float touchX;

        //手指触摸的y坐标
        float touchY;

        //event.getAction()判断事件的类型
        switch (event.getAction()) {

            //按下的事件
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                if (touchX > pointX - mRadius && touchX < pointX + mRadius && touchY > pointY - mRadius && touchY < pointY + mRadius) {
                    moveable = true;
                    Toast.makeText(getContext(), "我按下了", Toast.LENGTH_LONG).show();
                } else {
                    moveable = false;
                }
                break;

            //移动的事件
            case MotionEvent.ACTION_MOVE:
                if (moveable) {

                    //重新设置一下圆心的位置， 把我们圆心的位置（pointX,pointY)设置成
                    // 当前触摸的位置（event.getX()，event.getY()）
                    pointX = event.getX();
                    pointY = event.getY();

                    //去重新绘制， 会重新走onDraw()方法
                    invalidate();
                }
                break;

            //抬起的事件
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
