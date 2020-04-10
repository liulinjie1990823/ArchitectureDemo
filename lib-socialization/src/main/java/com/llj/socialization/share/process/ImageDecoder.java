package com.llj.socialization.share.process;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.llj.socialization.log.INFO;
import com.llj.socialization.log.Logger;
import com.llj.socialization.share.ShareObject;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;


public class ImageDecoder {

  public static final  String TAG                 = "ImageDecoder";
  /**
   * 分享图片的文件名
   */
  private static final String FILE_NAME_NO_SUFFIX = "share_image";
  /**
   * 图片的默认后缀
   */
  private static final String DEFAULT_SUFFIX      = ".png";

  /**
   * 默认的质量
   */
  public static final int                   DEFAULT_QUALITY         = 100;
  public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
  public static final String                BASE_64                 = "base64";


  /**
   * 将url或者bitmap存储到文件中
   *
   * @param context 上下文
   * @param shareObject 分享对象
   * @return 文件路径
   * @throws Exception
   */
  public static String decode(Context context, ShareObject shareObject) {
    String resultPath = null;
    try {
      if (!TextUtils.isEmpty(shareObject.getImageUrlOrPath())) {
        //路径，则需要解析(网络地址则下载，本地地址则复制一个副本)
        resultPath = pathToFile(context, shareObject.getImageUrlOrPath());

      } else if (shareObject.getImageBitmap() != null) {
        //bitmap,保存到本地
        File resultFile = getCacheFile(context, shareObject.getImageUrlOrPath());

        Bitmap.CompressFormat format =
            isJpgPath(resultFile.getAbsolutePath()) ? Bitmap.CompressFormat.JPEG
                : Bitmap.CompressFormat.PNG;

        resultPath = bitmapToFile(shareObject.getImageBitmap(), resultFile, format, DEFAULT_QUALITY)
            .getAbsolutePath();
      }
    } catch (Exception e) {
      Log.e(TAG, "[decode]: failed");
      e.printStackTrace();
    }
    return resultPath;
  }


  /**
   * 将本地文件，url，base64值转换成file
   *
   * @param context 上下文
   * @param pathOrUrl 本地路径，url，base64
   * @return
   * @throws Exception
   */
  private static String pathToFile(Context context, @NonNull String pathOrUrl) throws Exception {
    if (pathOrUrl == null || pathOrUrl.length() == 0) {
      Log.e(TAG, "[pathToFile]: pathOrUrl is null");
      return null;
    }
    File resultFile = getCacheFile(context, pathOrUrl);

    File localFile = new File(pathOrUrl);
    if (localFile.exists() && localFile.length() > 0) {
      //pathOrUrl如果是本地文件且存在复制本地文件
      copyFile(new File(pathOrUrl), resultFile);
    } else if (HttpUrl.parse(pathOrUrl) != null) {
      //如果是网络文件则下载文件
      downloadImageToUri(pathOrUrl, resultFile);
    } else if (pathOrUrl.contains(BASE_64)) {
      //解析base64图片路径
      Bitmap bitmap = stringToBitmap(pathOrUrl);
      bitmapToFile(bitmap, resultFile, DEFAULT_COMPRESS_FORMAT, DEFAULT_QUALITY);
    }
    if (!resultFile.exists() || resultFile.length() <= 0) {
      Log.e(TAG, "[pathToFile]: pathToFile file is empty");
    }
    return resultFile.getAbsolutePath();
  }

  private static Bitmap stringToBitmap(String string) {
    Bitmap bitmap = null;
    try {
      byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
      bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bitmap;
  }


  /**
   * 下载图片到本地
   *
   * @param url 图片地址
   * @param resultFile 本地图片路径
   * @return 本地图片路径地址字符串
   * @throws IOException
   */
  private static String downloadImageToUri(String url, File resultFile) throws IOException {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    Response response = client.newCall(request).execute();
    BufferedSink sink = Okio.buffer(Okio.sink(resultFile));
    sink.writeAll(response.body().source());

    sink.close();
    response.close();

    return resultFile.getAbsolutePath();
  }

  /**
   * 获取源文件的副本路径
   *
   * @param context 上下文
   * @param pathOrUrl 照片路径(本地或者网络，用来获取后缀用)
   * @return 本地的一个新的存放地址
   * @throws Exception
   */
  private static File getCacheFile(Context context, String pathOrUrl) throws Exception {
    String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(pathOrUrl);
    String fileName;
    if (TextUtils.isEmpty(pathOrUrl) || TextUtils.isEmpty(fileExtensionFromUrl)) {
      fileName = FILE_NAME_NO_SUFFIX;
    } else {
      fileName = md5(pathOrUrl.getBytes()) + FILE_NAME_NO_SUFFIX + "." + fileExtensionFromUrl;
    }

    String state = Environment.getExternalStorageState();
    if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {
      File file = new File(context.getExternalFilesDir(""), fileName);

      Logger.i("CacheFile:" + file.getAbsolutePath());

      return file;
    } else {
      throw new Exception(INFO.SD_CARD_NOT_AVAILABLE);
    }
  }

  //    private static File getCacheFile(Context context, String pathOrUrl) throws Exception {
  //        String fileName;
  //        if (TextUtils.isEmpty(pathOrUrl)) {
  //            fileName = FILE_NAME_NO_SUFFIX + getSuffix(pathOrUrl, DEFAULT_SUFFIX);
  //        } else {
  //            fileName = md5(pathOrUrl.getBytes()) + FILE_NAME_NO_SUFFIX + getSuffix(pathOrUrl, DEFAULT_SUFFIX);
  //        }
  //        String state = Environment.getExternalStorageState();
  //        if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {
  //            File file = new File(context.getExternalFilesDir(""), fileName);
  //
  //            Logger.i("CacheFile:" + file.getAbsolutePath());
  //
  //            return file;
  //        } else {
  //            throw new Exception(INFO.SD_CARD_NOT_AVAILABLE);
  //        }
  //    }

  /**
   * 返回后缀的路径
   *
   * @param path 路径
   * @param defaultSuffix 默认的后缀
   * @return 后缀
   */
  private static String getSuffix(String path, String defaultSuffix) {
    if (TextUtils.isEmpty(defaultSuffix)) {
      throw new NullPointerException("defaultSuffix can not be null");
    }
    if (TextUtils.isEmpty(path)) {
      return defaultSuffix;
    }
    String suffix = path.substring(path.lastIndexOf(".") + 1);
    if (TextUtils.isEmpty(suffix)) {
      return defaultSuffix;
    }
    if (suffix.contains("?")) {
      suffix = suffix.substring(0, suffix.indexOf("?"));
    }
    return suffix;
  }

  /**
   * 复制文件副本
   *
   * @param inputStream 输入流
   * @param outputStream 输出流
   * @throws IOException
   */
  private static void copyFile(InputStream inputStream, OutputStream outputStream)
      throws IOException {
    byte[] buffer = new byte[4096];
    while (-1 != inputStream.read(buffer)) {
      outputStream.write(buffer);
    }

    outputStream.flush();
    inputStream.close();
    outputStream.close();
  }

  /**
   * 复制文件副本
   *
   * @param origin 源文件
   * @param result 目标文件
   * @return 目标文件的地址字符串
   * @throws IOException
   */
  private static String copyFile(File origin, File result) throws IOException {
    copyFile(new FileInputStream(origin), new FileOutputStream(result, false));
    return result.getAbsolutePath();
  }

  /**
   * 保存bitmap到本地文件
   *
   * @param bitmap 照片对象
   * @param file 本地地址
   * @param format 照片规格
   * @param quality 照片质量
   * @return 保存后的文件
   */
  private static File bitmapToFile(Bitmap bitmap, File file, Bitmap.CompressFormat format,
      int quality) {
    if (bitmap != null && !bitmap.isRecycled()) {
      try {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(format, quality, bos);
        bos.flush();
        bos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (!file.exists() || file.length() <= 0) {
      Log.e(TAG, "[bitmapToFile]: bitmap saved file is null");
    }
    return file;
  }

  public static BitmapFactory.Options tryCompress2Byte(String imagePath) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, options);
    return options;
  }

  /**
   * 获取微信分享缩略图的字节数组
   *
   * @param imagePath 图片地址
   * @param size 压缩bitmap的最长边
   * @param length 字节数组的大小
   * @return 缩略图的字节数组
   */
  public static byte[] compress2Byte(String imagePath, int size, int length) {
    //先根据采样率压缩
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, options);

    int outH = options.outHeight;
    int outW = options.outWidth;
    int inSampleSize = 1;

    while (outH / inSampleSize > size || outW / inSampleSize > size) {
      inSampleSize *= 2;
    }

    options.inSampleSize = inSampleSize;
    options.inJustDecodeBounds = false;

    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

    ByteArrayOutputStream result = new ByteArrayOutputStream();
    int quality = 100;

    if (isJpgPath(imagePath)) {
      //再根据质量压缩
      Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
      bitmap.compress(format, quality, result);
      while (result.size() > length) {
        result.reset();
        quality -= 10;
        bitmap.compress(format, quality, result);
      }
    } else {
      //宽高像素按比例压缩
      Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
      bitmap.compress(format, quality, result);

      while (result.size() > length) {
        result.reset();
        Matrix matrix = new Matrix();
        matrix.setScale(0.9f, 0.9f);
        bitmap = Bitmap
            .createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.compress(format, quality, result);
      }
    }
    bitmap.recycle();
    return result.toByteArray();
  }

  private static boolean isJpgPath(String path) {
    return path != null && (path.endsWith(".jpg") || path.endsWith(".JPG") || path.endsWith(".JPEG")
        || path.endsWith(".jpeg"));
  }

  private static byte[] digest(final byte[] data, final String algorithm) {
    if (data == null || data.length <= 0) {
      return null;
    }
    try {
      MessageDigest md = MessageDigest.getInstance(algorithm);
      md.update(data);
      return md.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static byte[] digestMD5(final byte[] data) {
    return digest(data, "MD5");
  }

  private static final char HEX_DIGITS[] =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

  private static String hex(final byte[] bytes) {
    if (bytes == null) {
      return "";
    }
    int len = bytes.length;
    if (len <= 0) {
      return "";
    }
    char[] ret = new char[len << 1];
    for (int i = 0, j = 0; i < len; i++) {
      ret[j++] = HEX_DIGITS[bytes[i] >>> 4 & 0x0f];
      ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
    }
    return new String(ret);
  }

  public static String md5(final byte[] data) {
    return hex(digestMD5(data));
  }
}
