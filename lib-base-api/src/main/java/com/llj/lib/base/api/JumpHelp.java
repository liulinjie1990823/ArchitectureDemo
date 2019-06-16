package com.llj.lib.base.api;

import android.app.Application;

import com.llj.lib.base.api.core.LogisticsCenter;
import com.llj.lib.base.api.template.ILogger;
import com.llj.lib.base.api.utils.Consts;
import com.llj.lib.base.api.utils.DefaultLogger;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-16
 */
public class JumpHelp {

    private volatile static boolean hasInit    = false;
    private volatile static boolean debuggable = false;

    public static ILogger logger = new DefaultLogger(Consts.TAG);


    public static void init(Application application) {
        if (!hasInit) {
            logger.info(Consts.TAG, "JumpHelp init start.");

            LogisticsCenter.init(application);


            logger.info(Consts.TAG, "JumpHelp init success!");
            hasInit=true;
        }
    }

    public static synchronized void openDebug() {
        debuggable = true;
    }

    public static boolean debuggable() {
        return true;
    }
}
