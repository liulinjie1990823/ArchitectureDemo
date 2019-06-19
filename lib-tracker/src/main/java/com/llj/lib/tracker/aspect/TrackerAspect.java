package com.llj.lib.tracker.aspect;

import android.util.Log;
import android.view.View;

import com.llj.lib.tracker.R;
import com.llj.lib.tracker.Tracker;
import com.llj.lib.tracker.model.TrackerEvent;
import com.llj.lib.utils.AToastUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-19
 */
public class TrackerAspect {

    public static final long CLICK_INTERVAL = 2000;
    private static      long sLastClickTime;

    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))||execution(void *..lambda*(*..view.View))")
    public void onClick() {

    }


    @Before("onClick()")
    public void onClickBefore(JoinPoint joinPoint) throws Throwable {
        Log.i("onClick", "onClickBefore  aspect   ");
        Log.i("onClick", "onClickBefore  aspect   " + joinPoint);
        Log.i("onClick", "onClickBefore  aspect   " + joinPoint.getSignature());
        Log.i("onClick", "onClickBefore  aspect   " + joinPoint.getSignature().getDeclaringTypeName());
        Log.i("onClick", "onClickBefore  aspect   " + joinPoint.getSignature().getName());
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

    @After("onClick()")
    public void onClickAfter(JoinPoint joinPoint) throws Throwable {
        Log.i("onClick", "onClickAfter  aspect   ");
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            if (args[0] instanceof View) {
                View view = (View) args[0];

                TrackerEvent event = new TrackerEvent();
                event.eventName = view.getTag(R.id.tracker_event_name) == null ? null : view.getTag(R.id.tracker_event_name).toString();
                event.extraData = view.getTag(R.id.tracker_extra_data) == null ? null : view.getTag(R.id.tracker_extra_data).toString();

                Tracker.report(event);
            }
        }
    }


    @AfterReturning("onClick()")
    public void onClickAfterReturning(JoinPoint joinPoint) throws Throwable {
        Log.i("onClick", "onClickAfterReturning  aspect   ");
    }

    @AfterThrowing("onClick()")
    public void onClickAfterThrowing(JoinPoint joinPoint) throws Throwable {
        Log.i("onClick", "AfterThrowing  aspect   ");
    }


}
