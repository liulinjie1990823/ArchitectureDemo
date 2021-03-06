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

package com.llj.application.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.blankj.utilcode.util.FileUtils;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin;
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin;
import com.facebook.flipper.plugins.fresco.FrescoFlipperPlugin;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin;
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin;
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import com.facebook.flipper.plugins.sandbox.SandboxFlipperPlugin;
import com.facebook.flipper.plugins.sandbox.SandboxFlipperPluginStrategy;
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin;
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor;
import com.facebook.soloader.SoLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        FlipperClient client = AndroidFlipperClient.getInstance(application);
        //Layout Inspector
        client.addPlugin(new InspectorFlipperPlugin(application, DescriptorMapping.withDefaults()));
        //Navigation
        client.addPlugin(NavigationFlipperPlugin.getInstance());
        //Network
        client.addPlugin(new NetworkFlipperPlugin());
        //Databases
        client.addPlugin(new DatabasesFlipperPlugin(application));
        //Images Fresco
        client.addPlugin(new FrescoFlipperPlugin());
        //Sandbox
        client.addPlugin(new SandboxFlipperPlugin(new SandboxFlipperPluginStrategy() {
          @Override
          public Map<String, String> getKnownSandboxes() {
            return null;
          }

          @Override
          public void setSandbox(String sandbox) {

          }
        }));
        //Shared Preferences
        ArrayList<SharedPreferencesDescriptor> descriptors = new ArrayList<>();
        List<File> files = FileUtils
            .listFilesInDir(
                application.getCacheDir().getParentFile().getAbsolutePath() + File.separator
                    + "shared_prefs");
        for (File file : files) {
          descriptors.add(new SharedPreferencesFlipperPlugin
              .SharedPreferencesDescriptor(file.getName().replace(".xml", ""),
              Context.MODE_PRIVATE));
        }
        client.addPlugin(new SharedPreferencesFlipperPlugin(application, descriptors));
        //LeakCanary
        //RefWatcher refWatcher = LeakCanary.refWatcher(application)
        //    .listenerServiceClass(RecordLeakService.class)
        //    .buildAndInstall();
        //client.addPlugin(new LeakCanaryFlipperPlugin());
        client.addPlugin(new LeakCanary2FlipperPlugin());
        //Crash Reporter
        client.addPlugin(CrashReporterPlugin.getInstance());
        client.start();
      }
    }
  }
}
