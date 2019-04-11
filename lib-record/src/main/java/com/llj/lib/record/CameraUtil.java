package com.llj.lib.record;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Describe as : 图片处理工具
 * Created by LHL on 2018/4/7.
 */

public final class CameraUtil {
    private static final String TAG = "CameraUtil";

    private CameraUtil() throws IllegalAccessException {
        throw new IllegalAccessException("no instance!");
    }

    private static final ExecutorService sExecutorService = Executors.newSingleThreadExecutor();
    private static final Handler         sHandler         = new Handler(Looper.getMainLooper());

    public static void savePic(final int faceType, int displayOrientation, int sensorRotation, String directoryPath, final byte[] data,
                               final Bitmap.CompressFormat format,
                               final CameraOptCallback cameraOptCallback) {

        File saveFile = createDirectoryIfNotExist(directoryPath);
        if (saveFile == null) {
            return;
        }
        //获取保存文件名
        final String path = getFilePath(format, directoryPath);

        sExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                //处理bitmap角度
                Bitmap result = rotateBitmap(faceType, displayOrientation, sensorRotation, data);

                //保存图片
                saveBitmap(path, result, format, cameraOptCallback);
            }
        });
    }

    //https://blog.csdn.net/u010126792/article/details/86706199
    private static Bitmap rotateBitmap(final int faceType, int displayOrientation, int sensorRotation, final byte[] data) {
        Bitmap rawBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        Matrix matrix = new Matrix();
        int rotation = (displayOrientation + sensorRotation) % 360;

        if (faceType == Camera.CameraInfo.CAMERA_FACING_BACK) {
            //如果是后置摄像头因为没有镜面效果直接旋转特定角度
            matrix.setRotate(rotation);
        } else {
            //如果是前置摄像头需要做镜面操作，然后对图片做镜面postScale(-1, 1)
            //因为镜面效果需要360-rotation，才是前置摄像头真正的旋转角度
            rotation = (360 - rotation) % 360;
            matrix.setRotate(rotation);
            matrix.postScale(-1, 1);
        }
        return Bitmap.createBitmap(rawBitmap, 0, 0, rawBitmap.getWidth(), rawBitmap.getHeight(), matrix, true);
    }

    private static String getFilePath(final Bitmap.CompressFormat format, String directoryPath) {
        String suffix = "";
        switch (format) {
            case PNG:
                suffix = ".png";
                break;
            case JPEG:
                suffix = ".jpeg";
                break;
            case WEBP:
                suffix = ".webp";
                break;
        }
        return directoryPath + "/" + getDateFormatStr() + suffix;
    }

    private static void saveBitmap(String filePath, Bitmap result, final Bitmap.CompressFormat format, final CameraOptCallback cameraOptCallback) {
        File f = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean compress = result.compress(format, 100, fileOutputStream);
        if (compress) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (cameraOptCallback != null) {
                        cameraOptCallback.onPictureComplete(filePath, result);
                    }
                }
            });

        }
        try {
            if (fileOutputStream != null)
                fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (fileOutputStream != null)
                fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createDirectoryIfNotExist(String savePath) {
        File file = new File(savePath);
        if (!file.exists()) {
            createDirectory(file);
        }
        if (file.isFile()) {
            boolean delete = file.delete();
            if (delete) {
                createDirectory(file);
            }
        }
        return file;
    }

    private static void createDirectory(File file) {
        boolean mkdirs = file.mkdirs();
        Log.i(TAG, "createDirectoryIfNotExist ---> mkdirs = " + mkdirs
                + "  savePath = " + file.getAbsolutePath());
    }


    private static void closeStream(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static String getDateFormatStr() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    private static boolean checkPermissionResultIfGranted(@NonNull int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 如果需要请求权限，则弹框提示，并作出需要权限的解释（以toast展示）
     *
     * @param activity    当前界面
     * @param permissions 权限
     * @param explanation 解释
     * @param requestCode 触发requestPermissions后，在onRequestPermissionsResult里用到（区分是在请求哪组权限）
     *
     * @return 如果为true，表示已经获取了这些权限；false表示还未获取，并正在执行请求
     */
    private static boolean requestPermissionIfNeed(Activity activity,
                                                   String[] permissions, CharSequence explanation, int requestCode) {
        ArrayList<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (permissionList.isEmpty()) {
            return true;
        }

        String[] needRequestPermissions = permissionList.toArray(new String[permissionList.size()]);

        for (String permission : needRequestPermissions) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                if (!TextUtils.isEmpty(explanation)) {
                    Toast.makeText(activity, explanation, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

        ActivityCompat.requestPermissions(activity,
                needRequestPermissions,
                requestCode);
        return false;
    }
}
