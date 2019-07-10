package com.llj.lib.base.compiler.utils;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Simplify the message print.
 *
 * @author Alex <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 16/8/22 上午11:48
 */

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-11
 */
public class Logger {
    private Messager msg;
    private String prefix;

    public Logger(Messager msg, String prefix) {
        this.msg = msg;
        this.prefix = prefix;
    }

    public boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Print info log.
     */
    public void info(CharSequence info) {
        if (isEmpty(info)) {
            msg.printMessage(Diagnostic.Kind.NOTE, prefix + info);
        }
    }

    public void warning(CharSequence warning) {
        if (isEmpty(warning)) {
            msg.printMessage(Diagnostic.Kind.WARNING, prefix+ warning);
        }
    }


    public void error(CharSequence error) {
        if (!isEmpty(error)) {
            msg.printMessage(Diagnostic.Kind.ERROR, prefix + "An exception is encountered, [" + error + "]");
        }
    }

    public void error(Throwable error) {
        if (null != error) {
            msg.printMessage(Diagnostic.Kind.ERROR, prefix + "An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
