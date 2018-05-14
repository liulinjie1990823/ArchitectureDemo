package com.llj.lib.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liulj on 16/8/5.
 */

public class ScreenShotUtil {
    public static final String TAG = ScreenShotUtil.class.getSimpleName();

    /**
     * 获取屏幕中view的bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap getNormalViewScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getScrollViewScreenshot(ScrollView scrollView) {
        int h = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        Log.d(TAG, "实际高度:" + h);
        Log.d(TAG, " 高度:" + scrollView.getHeight());
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }


    /**
     * 获取ListView的截图,包含了所有的item
     * <p>
     * 需要注意的是如果item中有网络请求的图片,
     * 需要在所有的图片加载下来并绘制到view上
     * 的时候再去获取该itemView的bitmap
     * 即:childView.getDrawingCache()
     *
     * @param listView
     * @param path
     * @return
     */
    public static String getWholeListViewItemsToBitmap(ListView listView, String path) {
        Bitmap bigBitmap = getWholeListViewItemsToBitmap(listView);
        return ABitmapUtils.bitmapToFile(bigBitmap, new File(path), Bitmap.CompressFormat.PNG, 60).getAbsolutePath();
    }

    /**
     * @param listview
     * @return
     */
    public static Bitmap getWholeListViewItemsToBitmap(ListView listview) {
        ListAdapter adapter = listview.getAdapter();
        int itemsCount = adapter.getCount();
        int allItemsHeight = 0;
        List<Bitmap> bitmapList = new ArrayList<>();
        for (int i = 0; i < itemsCount; i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());

            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();

            bitmapList.add(childView.getDrawingCache());
            allItemsHeight += childView.getMeasuredHeight();
        }

        Bitmap bigBitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allItemsHeight, Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);
        Paint paint = new Paint();
        int iHeight = 0;
        for (int i = 0; i < bitmapList.size(); i++) {
            Bitmap bitmap = bitmapList.get(i);
            bigCanvas.drawBitmap(bitmap, 0, iHeight, paint);
            iHeight += bitmap.getHeight();
            bitmap.recycle();
            bitmap = null;
        }
        return bigBitmap;
    }

    /**
     * @param view
     * @return
     */
    public static Bitmap getRecyclerViewScreenshot(RecyclerView view) {
        int size = view.getAdapter().getItemCount();
        RecyclerView.ViewHolder holder = view.getAdapter().createViewHolder(view, 0);
        view.getAdapter().onBindViewHolder(holder, 0);
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
        Bitmap bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), holder.itemView.getMeasuredHeight() * size,
                Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);
        bigCanvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        int iHeight = 0;
        holder.itemView.setDrawingCacheEnabled(true);
        holder.itemView.buildDrawingCache();
        bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
        holder.itemView.setDrawingCacheEnabled(false);
        holder.itemView.destroyDrawingCache();
        iHeight += holder.itemView.getMeasuredHeight();
        for (int i = 1; i < size; i++) {
            view.getAdapter().onBindViewHolder(holder, i);
            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
            iHeight += holder.itemView.getMeasuredHeight();
            holder.itemView.setDrawingCacheEnabled(false);
            holder.itemView.destroyDrawingCache();
        }
        return bigBitmap;
    }
}
