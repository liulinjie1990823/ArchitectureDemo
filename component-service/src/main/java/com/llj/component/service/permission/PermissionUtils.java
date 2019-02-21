package com.llj.component.service.permission;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/12/17
 */
public class PermissionUtils {


    private static Boolean mCameraCanUse = true; //缓存上次的查询结果
    private static Camera  mCamera       = null;

    /**
     * 检测相机权限
     *
     * @param context
     *
     * @return
     */
    public static boolean hasCameraPermissions(Context context) {
        try {
            if (RomUtils.isOppo() || RomUtils.isVivo()) {
                if (!isCameraCanUse()) {
                    return false;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!isHasCameraPermission()) {
                        return false;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 相机是否可用
     * ps:有些手机即使禁掉拍照权限获取到的camera也不为null（比如魅族，oppoR9s）
     *
     * @return
     */
    private static boolean isCameraCanUse() {
        boolean canUse = true;
        try {
            mCamera = getCamera();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        mCameraCanUse = canUse;
        return canUse;
    }

    /**
     * 是否拿到相机权限
     * ps: vivo ，opo手机不管授权与否都会
     * 返回PackageManager.PERMISSION_GRANTED（已授权）故作特殊处理
     *
     * @return
     */

    private static boolean isHasCameraPermission() {
        Field fieldPassword;
        try {
            mCamera = getCamera();
            //通过反射去拿相机是否获得了权限
            fieldPassword = mCamera.getClass().getDeclaredField("mHasPermission");
            fieldPassword.setAccessible(true);
            Boolean result = (Boolean) fieldPassword.get(mCamera);
            if (mCamera != null) {
                mCamera.release();
            }
            mCamera = null;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            mCamera = null;
            return true;
        }
    }

    /**
     * 相机是否可使用
     *
     * @return
     */
    public static Boolean getCameraCanUse() {
        return mCameraCanUse;
    }

    /**
     * 获取相机实例
     *
     * @return
     */
    public static Camera getCamera() {
        if (mCamera == null) {
            return Camera.open();
        }
        return mCamera;
    }
}
