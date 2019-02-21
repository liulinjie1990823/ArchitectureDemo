package com.llj.component.service.permission;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * WeddingBazaar.
 * describe: 权限工具类
 * author llj
 * date 2019/2/21
 */
public class PermissionManager {
    //日历
    public static void checkCalendar(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.CALENDAR)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (listener != null) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //相机
    public static void checkCamera(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.CAMERA)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (PermissionUtils.hasCameraPermissions(context)) {
                        if (listener != null) {
                            listener.onGranted(permissions);
                        }
                    } else {
                        Toast.makeText(context, "请打开摄像头权限", Toast.LENGTH_LONG).show();
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //存储空间
    public static void checkStorage(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (listener != null) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //相机和存储空间
    public static void checkCameraAndStorage(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.CAMERA, Permission.Group.STORAGE)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (PermissionUtils.hasCameraPermissions(context)) {
                        if (listener != null) {
                            listener.onGranted(permissions);
                        }
                    } else {
                        Toast.makeText(context, "请打开摄像头权限", Toast.LENGTH_LONG).show();
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //通讯录
    public static void checkContacts(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.CONTACTS)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (listener != null) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //位置信息
    public static void checkLocation(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (listener != null) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //麦克风
    public static void checkRecordAudio(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.MICROPHONE)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (listener != null) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //电话
    public static void checkPhoneState(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.PHONE)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (listener != null) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //身体传感器
    public static void checkSensors(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.SENSORS)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (listener != null) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }

    //短信
    public static void checkSms(Context context, PermissionListener listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.SEND_SMS)
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> {
                    if (listener != null) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        //不再询问
                        GoSettingDialog.show(context, permissions);
                    }
                })
                .start();
    }


    public static String getPermissionCnStr(List<String> permissions) {
        List<String> pStrs = new ArrayList<>();
        String result = "";
        if (permissions == null || permissions.isEmpty()) {
            return "";
        }

        for (String permission : permissions) {
            if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                pStrs.add("读写");
            }
            if (permission.equals(Manifest.permission.CAMERA)) {
                pStrs.add("打开摄像头");
            }
            if (permission.equals(Manifest.permission.RECORD_AUDIO)) {
                pStrs.add("使用话筒录音/通话录音");
            }
            if (permission.equals(Manifest.permission.CALL_PHONE)) {
                pStrs.add("拨打电话");
            }
            if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                pStrs.add("获取位置信息");
            }
            if (permission.equals(Manifest.permission.WRITE_CONTACTS)) {
                pStrs.add("写入/删除联系人信息");
            }
            if (permission.equals(Manifest.permission.SEND_SMS)) {
                pStrs.add("发送短信");
            }
            if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
                pStrs.add("读取设备信息");
            }
            if (permission.equals(Manifest.permission.ACCESS_NOTIFICATION_POLICY)) {
                pStrs.add("通知");
            }
        }

        for (int i = 0; i < pStrs.size(); i++) {
            if (i != pStrs.size() - 1) {
                result += pStrs.get(i) + ",";
            } else {
                result += pStrs.get(i);
            }
        }
        return result;
    }

    public interface PermissionListener {
        void onGranted(List<String> permissions);
    }
}
