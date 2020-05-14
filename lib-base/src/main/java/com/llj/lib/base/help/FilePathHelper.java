package com.llj.lib.base.help;

import android.content.Context;
import android.os.Environment;
import com.llj.lib.utils.AAppUtil;
import java.io.File;

/**
 * ArchitectureDemo describe: author liulj date 2018/4/25
 */
public class FilePathHelper {

  public static String TEMP_PATH;
  public static String PIC_PATH;
  public static String APK_PATH;
  public static String CACHE_PATH;

  private static boolean sInitialed;

  public static void init(Context context) {
    if (sInitialed || context == null) {
      return;
    }
    sInitialed = true;

    String parentPath;

    if (AAppUtil.isSDCardAvailable()) {
      // 使用自己设置的sdcard缓存路径，需要应用里设置清除缓存按钮
      //  /storage/sdcard0/包名
      parentPath = Environment.getExternalStorageDirectory().getPath() + File.separator + context
          .getPackageName();

      //context.getExternalCacheDir()
      // /storage/sdcard0/Android/data/com.example.qymh/cache
    } else {
      // data/data/包名/files（这个文件夹在apk安装的时候就会创建）
      parentPath = context.getFilesDir().getAbsolutePath();
    }

    // 临时文件路径设置
    TEMP_PATH = parentPath + "/tmp";
    // 图片缓存路径设置
    PIC_PATH = parentPath + "/.pic";
    // 更新APK路径设置
    APK_PATH = parentPath + "/apk";
    // /storage/sdcard0/包名/cache
    CACHE_PATH = parentPath + "/cache";
    // 创建各目录
    new File(TEMP_PATH).mkdirs();
    new File(PIC_PATH).mkdirs();
    new File(APK_PATH).mkdirs();
    new File(CACHE_PATH).mkdirs();
  }
}
