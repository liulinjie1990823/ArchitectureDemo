package com.llj.lib.record;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;

import java.util.List;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
public class CameraHelper {

    private static final String TAG = "CameraHelper";


    //获取指定位置的摄像头id
    public static int getCameraId(int cameraFacing) {
        int numberOfCameras = Camera.getNumberOfCameras();

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == cameraFacing) {
                return i;
            }
        }
        return -1;
    }


    /**
     * @param flashMode 闪光灯模式
     *
     * @return 是否支持
     */
    public static boolean isSupportFlashMode(Camera.Parameters parameters, String flashMode) {
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes == null) {
            return false;
        }
        for (String supportedFlashMode : supportedFlashModes) {
            if (TextUtils.equals(flashMode, supportedFlashMode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 开启闪光灯模式
     */
    public static boolean flashOn(@NonNull Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        if (isSupportFlashMode(camera.getParameters(), Camera.Parameters.FLASH_MODE_ON)) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            try {
                camera.setParameters(parameters);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 关闭闪光灯模式
     */
    public static boolean flashOff(@NonNull Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        try {
            camera.setParameters(parameters);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * @return 闪光灯是否已开启
     */
    public static boolean isFlashOn(@NonNull Camera camera) {
        String flashMode = camera.getParameters().getFlashMode();
        return TextUtils.equals(flashMode, Camera.Parameters.FLASH_MODE_ON);
    }

    /**
     * @param focusMode 聚焦模式
     *
     * @return 是否支持
     */
    public static boolean isSupportFocus(Camera.Parameters parameters, String focusMode) {
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        for (String supportedFocusMode : supportedFocusModes) {
            if (TextUtils.equals(focusMode, supportedFocusMode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置相机对焦模式
     *
     * @param camera
     * @param focusMode
     */
    public static void setCameraFocusMode(@NonNull Camera camera, String focusMode) {
        Camera.Parameters parameters = camera.getParameters();
        List<String> sfm = parameters.getSupportedFocusModes();
        if (sfm.contains(focusMode)) {
            parameters.setFocusMode(focusMode);
        }
        camera.setParameters(parameters);
    }


    /**
     * 自动对焦
     *
     * @param camera
     */
    public static void autoFocus(@NonNull Camera camera) {
        final String currentFocusMode = camera.getParameters().getFocusMode();
        CameraHelper.setCameraFocusMode(camera, Camera.Parameters.FOCUS_MODE_MACRO);

        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                CameraHelper.setCameraFocusMode(camera, currentFocusMode);     //恢复之前的对焦模式
            }
        });
    }


    /**
     * 获取最佳尺寸
     *
     * @param targetWidth  目标宽
     * @param targetHeight 目标高
     * @param sizeList     相机自身提供的尺寸集合
     *
     * @return 最佳展示尺寸
     */
    public static Camera.Size getBestSize(int targetWidth, int targetHeight, List<Camera.Size> sizeList) {
        Camera.Size bestSize = null;
        //目标大小的宽高比
        float targetRatio = targetWidth * 1.0f / targetHeight;
        float minDiff = targetRatio;

        for (Camera.Size size : sizeList) {
            float supportRatio = size.width * 1.0f / size.height;
            log("系统支持的尺寸  = " + supportRatio + " 宽高" + size.width + "*" + size.height);
        }

        for (Camera.Size size : sizeList) {
            if (size.width == targetWidth && size.height == targetHeight) {
                bestSize = size;
                break;
            }
            float supportRatio = size.width * 1.0f / size.height;
            float tempDiff = Math.abs(supportRatio - targetRatio);
            if (tempDiff < minDiff) {
                minDiff = tempDiff;
                bestSize = size;
            }
        }

        if (bestSize != null) {
            log("目标尺寸   targetWidth = " + targetWidth
                    + "  targetHeight = " + targetHeight
                    + " ---> targetRatio = " + targetRatio);
            log("最优尺寸   bestSize.width =  " + bestSize.width
                    + " bestSize.height = " + bestSize.height
                    + " ---> supportRatio = " + bestSize.width * 1.0f / bestSize.height);
        }
        return bestSize;
    }


    /**
     * 设置相机旋转角度
     */
    public static int setCameraDisplayOrientation(Context context, @NonNull Camera camera, int faceType) {
        Activity activity = (Activity) context;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(faceType, cameraInfo);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        int screenDegree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                screenDegree = 0;
                break;
            case Surface.ROTATION_90:
                screenDegree = 90;
                break;
            case Surface.ROTATION_180:
                screenDegree = 180;
                break;
            case Surface.ROTATION_270:
                screenDegree = 270;
                break;
        }
        int displayOrientation;
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            displayOrientation = (cameraInfo.orientation + screenDegree) % 360;
            displayOrientation = (360 - displayOrientation) % 360;
        } else {
            displayOrientation = (cameraInfo.orientation - screenDegree + 360) % 360;
        }
        camera.setDisplayOrientation(displayOrientation);
        log("屏幕的旋转角度 = " + rotation);
        log("setDisplayOrientation ---> " + displayOrientation);
        return displayOrientation;
    }


    public static int calculateSensorRotation(float x, float y) {
        //x是values[0]的值，X轴方向加速度，从左侧向右侧移动，values[0]为负值；从右向左移动，values[0]为正值
        //y是values[1]的值，Y轴方向加速度，从上到下移动，values[1]为负值；从下往上移动，values[1]为正值
        //不考虑Z轴上的数据，
        if (Math.abs(x) > 6 && Math.abs(y) < 4) {
            if (x > 6) {
                return 270;
            } else {
                return 90;
            }
        } else if (Math.abs(y) > 6 && Math.abs(x) < 4) {
            if (y > 6) {
                return 0;
            } else {
                return 180;
            }
        }
        return -1;
    }

    private static void log(String msg) {
        Log.i(TAG, msg);
    }
}
