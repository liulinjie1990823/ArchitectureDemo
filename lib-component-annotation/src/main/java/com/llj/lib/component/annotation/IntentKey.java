package com.llj.lib.component.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/6
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface IntentKey {
    String name();
}
