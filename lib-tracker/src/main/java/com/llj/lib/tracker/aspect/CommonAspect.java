package com.llj.lib.tracker.aspect;

import android.util.Log;

import com.llj.lib.utils.AToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-19
 */
@Aspect
public class CommonAspect {

    public static final long CLICK_INTERVAL = 2000;
    private static      long sLastClickTime;

    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))||execution(void *..lambda*(*..view.View))")
    public void onClick() {

    }

    @Pointcut("execution(* getPageName(..))")
    public void getPageName() {

    }

    @Pointcut("execution(* getModuleName(..))")
    public void getModuleName() {

    }


    @Around("onClick()")
    public void onClickAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.i("onClick", "onClickAround  aspect   ");
        if (System.currentTimeMillis() - sLastClickTime >= CLICK_INTERVAL) {
            sLastClickTime = System.currentTimeMillis();
            joinPoint.proceed();
        } else {
            AToastUtils.show("请不要重复点击");
            Log.e("onClick", "onClickAround  aspect   重复点击,已过滤");
        }
    }

}
