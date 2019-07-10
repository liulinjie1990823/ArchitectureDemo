package com.example.lib.jump.annotation;

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
public @interface JumpKey {

    String ciw();

    String name();

    boolean required() default false;
}