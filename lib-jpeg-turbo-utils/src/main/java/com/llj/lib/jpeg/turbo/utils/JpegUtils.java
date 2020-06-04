package com.llj.lib.jpeg.turbo.utils;

import android.graphics.Bitmap;

public class JpegUtils {
  public native int nativeCompressBitmap(Bitmap bitmap, int quality, String destFile);
}
