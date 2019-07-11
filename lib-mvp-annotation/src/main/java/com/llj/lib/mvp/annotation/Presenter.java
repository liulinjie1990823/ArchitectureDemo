package com.llj.lib.mvp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArchitectureDemo.
 * describe: Presenter中的方法注解，自动生成模板代码
 * author llj
 * date 2018/9/6
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Presenter {

    Class<?> object();//接口返回实体对象

    boolean showLoading();//true使用加载框，false使用下载

    String method() default "";//接口调用方法

    String requestIdKey() default "requestId";//requestId的入参变量

    int exceptionId() default 0;//exceptionId

}