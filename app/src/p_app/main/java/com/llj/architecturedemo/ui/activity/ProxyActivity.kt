package com.llj.architecturedemo.ui.activity

import android.os.Bundle
import com.llj.architecturedemo.R
import com.llj.lib.base.MvcBaseActivity
import com.llj.lib.utils.LogUtil
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/8/28
 */
class ProxyActivity : MvcBaseActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_proxy
    }

    override fun initViews(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }


    public class DynamicProxyHandler(private var test: Any) : InvocationHandler {


        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
            LogUtil.i("DynamicProxyHandler", "我正在动态代理[" + test.javaClass.simpleName + "]的[" + method?.name + "]方法")
            return method?.invoke(test, args)!!
        }

        companion object {
            fun newProxyInstance(test: Any): Any {
                // 传入被代理对象的classloader实现的接口, 还有DynamicProxyHandler的对象即可。
                return Proxy.newProxyInstance(test.javaClass.classLoader,
                        test.javaClass.interfaces,
                        DynamicProxyHandler(test))
            }
        }
    }


    interface Subject1 {
        fun method1()
        fun method2()
    }


    inner class RealSubject1 : Subject1 {
        override fun method1() {
            LogUtil.i(this.javaClass.simpleName, "我是RealSubject1的方法1")
        }

        override fun method2() {
            LogUtil.i(this.javaClass.simpleName, "我是RealSubject1的方法2")
        }
    }
}