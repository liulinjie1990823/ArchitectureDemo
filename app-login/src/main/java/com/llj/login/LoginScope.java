package com.llj.login;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * describe 不写是傻逼
 *
 * @author liulinjie
 * @date 2020/4/19 4:11 PM
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginScope {

}