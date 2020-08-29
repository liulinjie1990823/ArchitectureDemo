/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.llj.component.service.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.llj.component.service.R;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import com.yanzhenjie.permission.runtime.Permission;
import java.util.List;

/**
 * Created by YanZhenjie on 2018/1/1.
 */
public final class RuntimeRationale implements Rationale<List<String>> {

  @Override
  public void showRationale(Context context, List<String> permissions,
      final RequestExecutor executor) {
    List<String> permissionNames = Permission.transformText(context, permissions);
    String message = context
        .getString(R.string.permission_rationale, TextUtils.join("\n", permissionNames));

    new AlertDialog.Builder(context)
        .setCancelable(false)
        .setTitle("提醒")
        .setMessage(message)
        .setPositiveButton("继续", (dialog, which) -> executor.execute())
        .setNegativeButton("取消", (dialog, which) -> executor.cancel())
        .show();
  }
}