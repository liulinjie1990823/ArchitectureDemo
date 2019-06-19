package com.llj.lib.tracker.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-19
 */
@Aspect
public class TrackerAspect {

    public static final long CLICK_INTERVAL = 400;
    private static      long sLastClickTime;

    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void onClick() {

    }
//
//    @Around("onClick()")
//    public void onClickAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        if (System.currentTimeMillis() - sLastClickTime >= CLICK_INTERVAL) {
//            sLastClickTime = System.currentTimeMillis();
//            joinPoint.proceed();
//        } else {
//            AToastUtils.show("请不要重复点击");
//            Log.e("onClickAround", "重复点击,已过滤");
//        }
//    }
//
//    @After("onClick()")
//    public void onClickAfter(JoinPoint joinPoint) throws Throwable {
//
//
//    }

    @Before("onClick()")
    public void onClickBefore(JoinPoint joinPoint) throws Throwable {
        Log.i("onClick", "onClickBefore  aspect   " + joinPoint.getSignature());
        Log.i("onClick", "onClickBefore  aspect   " + joinPoint.getSignature().getDeclaringTypeName());
        Log.i("onClick", "onClickBefore  aspect   " + joinPoint.getSignature().getName());
    }

}
