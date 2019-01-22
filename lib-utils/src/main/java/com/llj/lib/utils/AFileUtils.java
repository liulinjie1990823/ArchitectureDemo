package com.llj.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.llj.lib.utils.callback.ProgressListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * 文件处理工具
 *
 * @author liulj
 */
public class AFileUtils {


    /**
     * 1.从指定文件夹下查找含有关键字的文件
     *
     * @param keyword  文件中的关键字
     * @param filepath 指定文件夹
     *
     * @return 文件的全路径，带有文件名
     */
    public static final String searchFile(String keyword, File filepath) {
        File[] files = filepath.listFiles();

        if (files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果目录可读就执行（一定要加，不然会挂掉）
                    if (file.canRead()) {
                        searchFile(keyword, file); // 如果是目录，递归查找
                    }
                } else {
                    // 判断是文件，则进行文件名判断
                    try {
                        // if (file.getName().indexOf(keyword) > -1 ||
                        // file.getName().indexOf(keyword.toUpperCase()) > -1) {
                        // return file.getPath();
                        // }
                        if (file.getName().split("[.]")[0].equals(keyword)) {
                            return file.getPath();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return null;
    }

    /**
     * 针对系统文夹只需要扫描,不用插入内容提供者,不然会重复
     *
     * @param context  上下文
     * @param filePath 文件路径,文件夹则无效
     */
    private static void scanFileByBroadcast(Context context, String filePath) {
        if (!fileIsExist(filePath))
            return;
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(intent);
    }


    /**
     * 现在使用这个方法来通知扫描刷新
     *
     * @param context
     * @param filePath
     * @param onScanCompletedListener
     */
    private static void scanFile(Context context, String filePath, MediaScannerConnection.OnScanCompletedListener onScanCompletedListener) {
        if ((!fileIsExist(filePath))
                || onScanCompletedListener == null)
            return;
        MediaScannerConnection.scanFile(context,
                new String[]{filePath}, null,
                onScanCompletedListener);
    }

    /**
     * 文件是否存在
     *
     * @param filePath
     *
     * @return
     */
    public static boolean fileIsExist(String filePath) {
        return !TextUtils.isEmpty(filePath) && (new File(filePath).exists());
    }

    /**
     * 2.取得某个文件夹或者文件的bytes大小
     *
     * @param file 文件或者文件夹
     *
     * @return 文件或者文件夹大小
     */
    public static final long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            for (File subFile : file.listFiles()) {
                if (subFile.isDirectory()) {
                    size += getFileSize(subFile);
                } else {
                    // 文件的bytes大小累加
                    size += subFile.length();
                }
            }
        }
        return size;
    }

    /**
     * 3.将个文件夹或者文件的bytes大小转换成对应格式
     *
     * @param fileLength 文件或者文件夹大小
     *
     * @return 对应格式的字符串
     */
    public static final String formatFileSize(long fileLength) {
        DecimalFormat df = new DecimalFormat("##.00");
        String fileSizeString = "";
        if (fileLength < 1024) {
            fileSizeString = df.format((double) fileLength) + "B";
        } else if (fileLength < 1048576) {
            fileSizeString = df.format((double) fileLength / 1024) + "K";
        } else if (fileLength < 1073741824) {
            fileSizeString = df.format((double) fileLength / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLength / 1073741824) + "G";
        }
        if (fileSizeString.equals(".00B")) {
            fileSizeString = "0" + fileSizeString;
        }
        return fileSizeString;
    }

    /**
     * @param context
     * @param fileLength
     *
     * @return
     */
    public static final String formatFileSize(Context context, long fileLength) {
        return Formatter.formatFileSize(context, fileLength);
    }

    /**
     * 4.删除文件
     *
     * @param file 将要删除的文件或者文件
     */
    public static final void cleanCache(File file) {
        if (file != null && file.exists()) {
            for (File subFile : file.listFiles()) {
                if (!subFile.isDirectory()) {
                    // 如果不是目录是文件则删除
                    subFile.delete();
                }
            }
        }
    }


    /**
     * @param inputStream
     * @param file        存放本地文件路径
     *
     * @return
     */
    public static final boolean saveInputStreamToFile(InputStream inputStream, File file, ProgressListener progressListener) {
        if (inputStream == null) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            //需要创建父级以上的目录
            file.getParentFile().mkdirs();
            //创建空文件
            file.createNewFile();

            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));

            int size;
            long total = 0;
            byte[] temp = new byte[1024];
            while ((size = inputStream.read(temp, 0, temp.length)) != -1) {
                total += size;
                progressListener.onLoadProgress(total, 0, false);
                bufferedOutputStream.write(temp, 0, size);
            }

            bufferedOutputStream.flush();

            inputStream.close();
            bufferedOutputStream.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 5. 保存字节流至文件
     *
     * @param bytes 字节流
     * @param file  目标文件
     */
    public static final boolean saveBytesToFile(byte[] bytes, File file) {
        if (bytes == null) {
            return false;
        }
        ByteArrayInputStream byteArrayInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();

            byteArrayInputStream = new ByteArrayInputStream(bytes);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));

            int size;
            byte[] temp = new byte[1024];
            while ((size = byteArrayInputStream.read(temp, 0, temp.length)) != -1) {
                bufferedOutputStream.write(temp, 0, size);
            }

            bufferedOutputStream.flush();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 6.保存位图至图片文件
     *
     * @param bitmap
     * @param file
     *
     * @return
     */
    public static final boolean saveBitmapToFile(Bitmap bitmap, File file) {
        BufferedOutputStream bos = null;
        try {
            if (file != null) {
                file.getParentFile().mkdirs();
                bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(CompressFormat.JPEG, 80, bos);

                bos.flush();
                bos.close();
                bos = null;
                if (file.exists() && file.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos = null;
            }
        }
        return false;
    }

    public static final boolean copyFile(String srcFilePath, String destFilePath) {
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);
        return copyFile(srcFile, destFile);
    }

    /**
     * 7.把源文件复制到新的文件中，只需要一个file对象即可
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     */
    public static final boolean copyFile(File srcFile, File destFile) {
        if (!srcFile.exists()) {
            return false;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();

            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(destFile));

            int size;
            byte[] temp = new byte[1024];
            while ((size = bis.read(temp, 0, temp.length)) != -1) {
                bos.write(temp, 0, size);
            }
            bos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos = null;
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bis = null;
            }
        }
        return false;
    }

    /**
     * @param f
     *
     * @return
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        FileInputStream stream = null;
        ByteArrayOutputStream out;
        try {
            stream = new FileInputStream(f);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[2097152];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
        } finally {
        }
        return null;
    }

    /**
     * @param file
     *
     * @return
     */
    public static ByteArrayOutputStream getOutputStreamFromFile(File file) {
        if (file == null) {
            return null;
        }
        FileInputStream stream = null;
        ByteArrayOutputStream out = null;
        try {
            stream = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[2097152];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out;
        } catch (Exception e) {
        } finally {
        }
        return out;
    }

    /**
     * 根据文件路径获得全文件名
     *
     * @param path 文件路径
     */
    public static final String getUrlByPath(String path) {
        String name = null;
        if (path != null) {
            int start = path.lastIndexOf("/");
            // 截取最后一个/后面的字符串
            name = path.substring(start == -1 ? 0 : start + 1);
        }
        return name;
    }

    /**
     * 根据文件路径获得文件名
     * 如:http://ibbpp.oss-cn-hangzhou.aliyuncs.com/36580174/bbpp_36580174_1452342480003.png
     *
     * @param path 文件路径 如36580174/bbpp_36580174_1452342480003.png
     */
    public static final String getFileNameByPathWithSuffix(String path) {
        String name = "";
        if (path != null) {
            int start = path.lastIndexOf("/");
            // 截取最后一个/到最后一个.之间的字符串
            name = path.substring(start == -1 ? 0 : start + 1, path.length());
        }
        return name;
    }

    /**
     * 返回后缀的路径
     *
     * @param path          路径
     * @param defaultSuffix 默认的后缀
     *
     * @return 后缀
     */
    public static String getSuffix(String path, String defaultSuffix) {
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
        return suffix;
    }

    public static String getSuffix(String path) {
        String defaultSuffix = APhotoUtils.JPG;
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
        return suffix;
    }

    /**
     * 根据文件路径获得后缀名
     *
     * @param path 文件路径
     */
    public static final String getFileTypeByPath(String path) {
        String type = null;
        if (path != null) {
            int start = path.lastIndexOf(".");
            if (start != -1) {
                // 截取.后面的字符串
                type = path.substring(start + 1);
            }
        }
        return type;
    }

    /**
     * 根据文件路径获得文件名
     *
     * @param path 文件路径
     */
    public static final String getFileNameByPathNoSuffix(String path) {
        String name = null;
        if (path != null) {
            int start = path.lastIndexOf("/");
            int end = path.lastIndexOf(".");
            // 截取最后一个/到最后一个.之间的字符串
            name = path.substring(start == -1 ? 0 : start + 1, end == -1 ? path.length() : end);
        }
        return name;
    }

    /**
     * 根据URL获得全文件名
     *
     * @param url URL
     */
    public static final String getFileFullNameByUrl(String url) {
        String name = null;
        if (url != null) {
            int start = url.lastIndexOf("/");
            int end = url.lastIndexOf("?");
            // 截取url中最后一个/到最后一个？之间的字符串
            name = url.substring(start == -1 ? 0 : start + 1, end == -1 ? url.length() : end);
        }
        return name;
    }

    /**
     * 根据URL获得后缀名
     *
     * @param url URL
     */
    public static final String getFileTypeByUrl(String url) {
        String type = null;
        if (url != null) {
            int start = url.lastIndexOf(".");
            int end = url.lastIndexOf("?");
            if (start != -1) {
                // 截取最后一个.到？的
                type = url.substring(start + 1, end == -1 ? url.length() : end);
            }
        }
        return type;
    }

    /**
     * 根据URL获得文件名
     *
     * @param url URL
     */
    public static final String getFileNameByUrl(String url) {
        String name = null;
        if (url != null) {
            int start = url.lastIndexOf("/");
            int end = url.lastIndexOf(".");
            int end2 = url.lastIndexOf("?");
            // 没有.就截取/到？的 有就截取/到.的
            name = url.substring(start == -1 ? 0 : start + 1, end == -1 ? (end2 == -1 ? url.length() : end2) : end);
        }
        return name;
    }

    /**
     * 返回文件后缀
     *
     * @param fileName
     *
     * @return
     */
    public static String getFileFormat(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        } else {
            int point = fileName.lastIndexOf('.');
            return fileName.substring(point + 1);
        }
    }
}
