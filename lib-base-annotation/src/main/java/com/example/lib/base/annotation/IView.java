package com.example.lib.base.annotation;

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
@Target(ElementType.METHOD)
public @interface IView {

    Class<?> view();
}