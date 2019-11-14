package com.llj.lib.base.help;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.view.View;

import com.llj.lib.utils.ABitmapUtils;
import com.llj.lib.utils.callback.Delegate;


/**
 * Created by liulj on 16/2/22.
 */
public class RenderScriptHelper {
    private Activity mActivity;

    private Bitmap mBitmapOut;
    private Bitmap mBitmapIn;
    private float mScaleFactor = 8.0f;
    private float mBlurRadius  = 25.0f;

    private RenderScriptTask mRenderScriptTask;

    private Delegate<Bitmap> mBitmapDelegate;

    public RenderScriptHelper(Activity context) {
        mActivity = context;
        init();
    }

    private void init() {
        if (mActivity.getWindow() != null) {
            mActivity.getWindow().getDecorView().setDrawingCacheEnabled(true);
            mBitmapIn = Bitmap.createBitmap(mActivity.getWindow().getDecorView().getDrawingCache());
            mActivity.getWindow().getDecorView().setDrawingCacheEnabled(false);
        }
    }

    public void perform(Delegate<Bitmap> delegate) {
        mBitmapDelegate = delegate;
        if (mRenderScriptTask != null) {
            mRenderScriptTask.cancel(true);
        }
        mRenderScriptTask = new RenderScriptTask();
        mRenderScriptTask.execute();
    }

    /**
     * @param scaleFactor
     */
    public void setScaleFactor(float scaleFactor) {
        if (mScaleFactor >= 0 && mScaleFactor <= 8.0)
            mScaleFactor = scaleFactor;
        else {
            mScaleFactor = 4.0f;
        }
    }

    /**
     * @param blurRadius
     */
    public void setBlurRadius(float blurRadius) {
        if (blurRadius >= 0 && blurRadius <= 25.0)
            mBlurRadius = blurRadius;
        else {
            mBlurRadius = 20.0f;
        }
    }

    public void release() {
        mBitmapIn = null;
        mBitmapOut = null;
    }

    /**
     * activity的DecorView
     *
     * @param decorView
     */
    public Bitmap setBottomViewBlur(View decorView) {
        //原始的srcRect(根据DecorView减去上下偏移)
        final int topOffset = DisplayHelper.STATUS_BAR_HEIGHT;
        int bottomOffset = DisplayHelper.NAVIGATION_BAR_HEIGHT;
        Rect srcRect = new Rect(0, topOffset, mBitmapIn.getWidth(), mBitmapIn.getHeight() - bottomOffset);

        //根据mScaleFactor缩放的宽高
        double height = Math.ceil((decorView.getHeight() - topOffset - bottomOffset) / mScaleFactor);
        double width = Math.ceil((decorView.getWidth() * height / (decorView.getHeight() - topOffset - bottomOffset)));
        //创建一个根据mScaleFactor缩放后的bitmap
        mBitmapOut = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_4444);
        final RectF destRect = new RectF(0, 0, mBitmapOut.getWidth(), mBitmapOut.getHeight());

        //将原来的mBitmapIn剪裁出srcRect的大小,在缩放到destRect的位置上
        Canvas canvas = new Canvas(mBitmapOut);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmapIn, srcRect, destRect, paint);

        mBitmapOut = ABitmapUtils.getFastBlurBitmap(mActivity, mBitmapOut, mBlurRadius);

        return mBitmapOut;
//        createScript();
    }

    public Bitmap getFastBlurBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            return null;
        }
        return getFastBlurBitmap(bitmap);
    }

    public Bitmap getFastBlurBitmap(@NonNull Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Rect srcRect = new Rect(0, 0, width, height);

        width = (int) Math.ceil(width / mScaleFactor);
        height = (int) Math.ceil(height / mScaleFactor);
        mBitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final RectF destRect = new RectF(0, 0, mBitmapOut.getWidth(), mBitmapOut.getHeight());

        //将原来的mBitmapIn剪裁出srcRect的大小,在缩放到destRect的位置上
        Canvas canvas = new Canvas(mBitmapOut);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, srcRect, destRect, paint);

        return ABitmapUtils.getFastBlurBitmap(mActivity, mBitmapOut, mBlurRadius);
    }

    private class RenderScriptTask extends AsyncTask<Float, Boolean, Boolean> {

        protected Boolean doInBackground(Float... values) {
            if (!isCancelled()) {
                if (mActivity.getWindow() != null && mBitmapIn != null) {
                    setBottomViewBlur(mActivity.getWindow().getDecorView());
                }
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            if (result && mBitmapOut != null && mBitmapDelegate != null) {
                mBitmapDelegate.execute(mBitmapOut);
            }
        }

    }
}
