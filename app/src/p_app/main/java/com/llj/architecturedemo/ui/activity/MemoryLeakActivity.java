package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.vo.Animal;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.MvcBaseActivity;
import com.llj.lib.utils.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ArchitectureDemo.
 * describe:
 * author liulj
 * date 2018/7/2
 */
@Route(path = CRouter.APP_MEMORY_LEAK_ACTIVITY)
public class MemoryLeakActivity extends MvcBaseActivity {
    public static final int SHOW_TYPE_ALBUM = 0;

    private Animal mAnimal = new Animal();

    @Override
    public int layoutId() {
        return R.layout.activity_memory_leak;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {


//        DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler(new RealSubject1());
//        Proxy.newProxyInstance(dynamicProxyHandler.getClass().getClassLoader(), dynamicProxyHandler.getClass().getInterfaces(), dynamicProxyHandler);

    }

    class MyHandler extends Handler {
        public MyHandler(Callback callback) {
            super(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            mContext.databaseList();
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //    private final Handler mHandler = new MyHandler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            return false;
//        }
//    });

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            showLongToast("测试内存泄漏");
            mHandler.sendEmptyMessageDelayed(1, 10000);
        }
    };

    @Override
    public void initData() {

    }


    public interface Subject1 {
        void method1();

        void method2();
    }

    public static class RealSubject1 implements Subject1 {

        @Override
        public void method1() {
            LogUtil.i(this.getClass().getSimpleName(), "我是RealSubject1的方法1");
        }

        @Override
        public void method2() {
            LogUtil.i(this.getClass().getSimpleName(), "我是RealSubject1的方法2");
        }
    }

    public static class DynamicProxyHandler implements InvocationHandler {
        private Object mObject;

        public DynamicProxyHandler(Object object) {
            mObject = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            LogUtil.i("DynamicProxyHandler", "我正在动态代理[" + proxy.getClass().getSimpleName() + "]的[" + method.getName() + "]方法");
            return method.invoke(mObject, args);
        }


    }

}
