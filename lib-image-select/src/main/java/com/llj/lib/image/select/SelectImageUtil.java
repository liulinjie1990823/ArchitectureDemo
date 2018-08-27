package com.llj.lib.image.select;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by liulj on 15/12/3.
 */
public class SelectImageUtil {
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }
        return degree;
    }
    /**
     * 6.创建旋转后的图片
     *
     * @param bitmap
     * @param degrees 旋转的角度
     * @return
     */
    public static final Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
        int sourceWidth = bitmap.getWidth();
        int sourceHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        // 当进行的不只是平移操作的时候，最后的参数为true，可以进行滤波处理，有助于改善新图像质量
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, sourceWidth, sourceHeight, matrix, true);
        return newbmp;
    }
}
