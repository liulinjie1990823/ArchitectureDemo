package com.llj.component.service.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.llj.component.service.R;

import java.util.List;

/**
 * WeddingBazaar.
 * describe:
 * author llj
 * date 2018/12/14
 */
public class GoSettingDialog {

    public static void show(final Context context, List<String> permissions) {
        String title = PermissionManager.getPermissionCnStr(permissions)
                + context.getResources().getString(R.string.permission_dialog_show_title);
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(R.string.permission_dialog_show_content)
                .setPositiveButton("去设置", (dialog, which) -> {
                    Intent intent = new Intent();
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                    ((Activity) context).startActivityForResult(intent, 10086);
                })
                .setNegativeButton("稍后设置", (dialog, which) -> {

                }).show();
    }
}
