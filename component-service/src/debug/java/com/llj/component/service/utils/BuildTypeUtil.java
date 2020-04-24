/*
 * Copyright © 2020, Beijing Jinhaiqunying, Co,. Ltd. All Rights Reserved.
 * Copyright Notice
 * Jinhaiqunying copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 * without the prior written consent of Jinhaiqunying.
 * Disclaimer
 * This specification is preliminary and is subject to change at any time without notice.
 * Jinhaiqunying assumes no responsibility for any errors contained herein.
 *
 */

package com.llj.component.service.utils;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin;
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin;
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseDriver;
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseProvider;
import com.facebook.flipper.plugins.fresco.FrescoFlipperPlugin;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin;
import com.facebook.flipper.plugins.leakcanary.RecordLeakService;
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor;
import com.facebook.soloader.SoLoader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * describe 不同环境工具类
 *
 * @author liulinjie
 * @date 2020-01-17 15:47
 */
public class BuildTypeUtil {

  private static boolean isDebug(Application application) {
    return (application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
  }

  public static void initFlipper(Application application) {
    if (isDebug(application)) {

      SoLoader.init(application, false);
      if (FlipperUtils.shouldEnableFlipper(application)) {

        RefWatcher refWatcher = LeakCanary.refWatcher(application)
            .listenerServiceClass(RecordLeakService.class)
            .buildAndInstall();

        FlipperClient client = AndroidFlipperClient.getInstance(application);
        //布局查看
        client.addPlugin(new InspectorFlipperPlugin(application, DescriptorMapping.withDefaults()));
        //网络
        client.addPlugin(new NetworkFlipperPlugin());
        //图片加载
        client.addPlugin(new FrescoFlipperPlugin());
        //数据库文件
        client.addPlugin(new DatabasesFlipperPlugin(
            new SqliteDatabaseDriver(application, new SqliteDatabaseProvider() {
              @Override
              public List<File> getDatabaseFiles() {
                List<File> databaseFiles = new ArrayList<>();
                for (String databaseName : application.databaseList()) {
                  databaseFiles.add(application.getDatabasePath(databaseName));
                }
                return databaseFiles;
              }
            })));
        //SharedPreferences操作
        ArrayList<SharedPreferencesDescriptor> descriptors = new ArrayList<>();
        //List<File> files = FileUtils
        //    .listFilesInDir(
        //        application.getCacheDir().getParentFile().getAbsolutePath() + File.separator
        //            + "shared_prefs");
        //for (File file : files) {
        //  descriptors.add(new SharedPreferencesFlipperPlugin
        //      .SharedPreferencesDescriptor(file.getName().replace(".xml", ""),
        //      Context.MODE_PRIVATE));
        //}
        //client.addPlugin(new SharedPreferencesFlipperPlugin(application, descriptors));
        //内存溢出检测
        client.addPlugin(new LeakCanaryFlipperPlugin());
        //崩溃统计
        client.addPlugin(CrashReporterPlugin.getInstance());
        client.start();
      }
    }
  }

}
