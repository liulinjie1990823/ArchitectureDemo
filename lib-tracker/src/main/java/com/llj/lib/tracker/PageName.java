package com.llj.lib.tracker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageName {


    /**
     * Name of route, used to generate javadoc.
     */
    String name() default "";

}