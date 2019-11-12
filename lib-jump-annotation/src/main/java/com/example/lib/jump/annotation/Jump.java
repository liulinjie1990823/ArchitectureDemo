package com.example.lib.jump.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArchitectureDemo. describe: author llj date 2018/9/6
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Jump {

  /**
   * 外部路径path
   */
  String outPath();

  /**
   * 内部路径
   */
  String inPath();

  /**
   * 注释描述
   */
  String desc() default "";

  /**
   * 是否需要登录
   *
   * @return true 是
   */
  boolean needLogin() default false;
}