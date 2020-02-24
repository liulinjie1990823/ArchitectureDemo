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

  }

}

