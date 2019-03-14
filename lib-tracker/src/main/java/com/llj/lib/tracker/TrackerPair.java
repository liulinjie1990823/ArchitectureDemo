package com.llj.lib.tracker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackerPair {

    String key() ;

    String value() ;
}
