package com.llj.lib.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 判断应用运行在前台或者后台
 *
 * @author liulj
 */
public class AActivityManagerUtils {
    public static final String TAG = AActivityManagerUtils.class.getSimpleName();

    /**
     * 1.判断当前任务是否在任务栈的前台
     *
     * @param context 上下文对象
     * @return true 前台 false 后台
     */
    @SuppressWarnings("deprecation")
    public static boolean isApplicationInForeground(@NonNull Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final List<RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
            if (processInfos == null || processInfos.size() == 0) {
                return false;
            }

            for (RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    if (processInfo.processName.equals(context.getPackageName())) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            // 获取当前活动的task栈
            //<uses-permission android:name="android.permission.GET_TASKS" />
            List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
            if (tasksInfo != null && !tasksInfo.isEmpty()) {
                if (context.getApplicationInfo().packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
//                    LogUtil.LLJi("context.getPackageName():" + context.getPackageName());
//                    LogUtil.LLJi("context.getApplicationInfo().packageName:" + context.getApplicationInfo().packageName);
//                    LogUtil.LLJi("tasksInfo.get(0).topActivity.getPackageName():" + tasksInfo.get(0).topActivity.getPackageName());
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 方法1：通过getRunningTasks判断App是否位于前台，此方法在5.0以上失效
     *
     * @param context     上下文参数
     * @param packageName 需要检查是否位于栈顶的App的包名
     * @return
     */
    public static boolean isRunningTask(@NonNull Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks == null || runningTasks.size() == 0) {
            return false;
        }
        ComponentName cn = runningTasks.get(0).topActivity;
        return !TextUtils.isEmpty(packageName) && packageName.equals(cn.getPackageName());
    }


    public static boolean isRunningProcess(@NonNull Context context) {
        return isRunningProcess(context, getRunningProcess(context));
    }

    public static String getRunningProcess(@NonNull Context context) {
        return context.getApplicationContext().getPackageName();
    }

    public static void logRunningProcess(@NonNull Context context) {
        LogUtil.e(TAG, "当前的processName:" + getRunningProcess(context));
    }


    /**
     * 方法2：通过getRunningAppProcesses的IMPORTANCE_FOREGROUND属性判断是否位于前台，当service需要常驻后台时候，此方法失效,
     * 在小米 Note上此方法无效，在Nexus上正常
     *
     * @param context     上下文参数
     * @param packageName 需要检查是否位于栈顶的App的包名
     * @return
     */

    public static boolean isRunningProcess(@NonNull Context context, String packageName) {
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        // 获得进程pid
        int pid = android.os.Process.myPid();
        // 获得运行的所有进程
        List<RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
        if (processInfoList == null || processInfoList.isEmpty()) {
            return false;
        }
        for (RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo != null && processInfo.pid == pid && TextUtils.equals(packageName, processInfo.processName)) {
                return true;
            }
        }
        LogUtil.e(TAG, "isRunningProcess:false");
        return false;
    }

    /**
     * 方法4：通过使用UsageStatsManager获取，此方法是ndroid5.0A之后提供的API
     * 必须：
     * 1. 此方法只在android5.0以上有效
     * 2. AndroidManifest中加入此权限<uses-permission xmlns:tools="http://schemas.android.com/tools" android:name="android.permission.PACKAGE_USAGE_STATS"
     * tools:ignore="ProtectedPermissions" />
     * 3. 打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾
     *
     * @param context     上下文参数
     * @param packageName 需要检查是否位于栈顶的App的包名
     * @return
     */

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("WrongConstant")
    public static boolean queryUsageStats(@NonNull Context context, String packageName) {
        class RecentUseComparator implements Comparator<UsageStats> {
            @Override
            public int compare(UsageStats lhs, UsageStats rhs) {
                return Long.compare(rhs.getLastTimeUsed(), lhs.getLastTimeUsed());
            }
        }
        RecentUseComparator mRecentComp = new RecentUseComparator();
        long ts = System.currentTimeMillis();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        if (mUsageStatsManager == null) {
            return false;
        }
        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts - 1000 * 10, ts);
        if (usageStats == null || usageStats.size() == 0) {
            if (!havePermissionForTest(context)) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context, "权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        Collections.sort(usageStats, mRecentComp);
        String currentTopPackage = usageStats.get(0).getPackageName();

        return currentTopPackage.equals(packageName);
    }

    /**
     * 判断是否有用权限
     *
     * @param context 上下文参数
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean havePermissionForTest(@NonNull Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (appOpsManager == null) {
                return false;
            }
            int mode = appOpsManager.checkOpNoThrow("android:get_usage_stats", applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    /**
     * 应用可申请的内存
     *
     * @param context
     * @return
     */
    public static int getMemoryClass(@NonNull Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return 0;
        }
        return manager.getMemoryClass();
    }

    /**
     * 应用可申请的最大内存
     *
     * @param context
     * @return
     */
    public static int getLargeMemoryClass(@NonNull Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return 0;
        }
        return manager.getLargeMemoryClass();
    }
}
