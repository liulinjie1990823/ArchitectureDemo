package com.llj.lib.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

public class ANotificationUtils {

  private static final String CHECK_OP_NO_THROW    = "checkOpNoThrow";
  private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";


  private static NotificationManagerCompat mNotificationManager;

  public static NotificationManagerCompat getNotificationManager(Context context) {
    if (mNotificationManager == null) {
      synchronized (ANotificationUtils.class) {
        if (mNotificationManager == null) {
          mNotificationManager = NotificationManagerCompat.from(context.getApplicationContext());
        }
      }
    }
    return mNotificationManager;
  }

  //表示versionCode=19 也就是4.4的系统以及以上的系统生效。4.4以下系统默认全部打开状态。
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public static boolean isNotificationEnabled(Context context) {
    return getNotificationManager(context).areNotificationsEnabled();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public static boolean isNotificationsChannelEnabled(Context context, String channelID) {
    NotificationChannel channel = getNotificationManager(context).getNotificationChannel(channelID);
    if (channel == null) {
      return true;
    }
    return !(channel.getImportance() == NotificationManager.IMPORTANCE_NONE);
  }


  public static void jumpSetting(Context context, String channelId) {
    Intent intent = new Intent();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      //[8.0,+)后引导到具体渠道页面
      if (TextUtils.isEmpty(channelId)) {
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
      } else {
        intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
      }
      //[5.0,8.0)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
      intent.putExtra("app_package", context.getPackageName());
      intent.putExtra("app_uid", context.getApplicationInfo().uid);
    } else {
      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
      Uri uri = Uri.fromParts("package", context.getPackageName(), null);
      intent.setData(uri);
    }

    context.startActivity(intent);
  }


}
