package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.MvcBaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/11/9
 */
@Route(path = CRouter.APP_JAVA_TYPE_ACTIVITY)
public class JavaTypeActivity extends MvcBaseActivity {
    @Override
    public int layoutId() {
        return R.layout.activity_java_type;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

        Kotlin kotlin = new Kotlin("Kotlin");
        ArrayList<Kotlin> kotlinList = new ArrayList<>();
        kotlinList.add(kotlin);

        Java java = new Java("Java");
        ArrayList<Java> javaList = new ArrayList<>();
        javaList.add(java);


        ArrayList< C> cList2 = new ArrayList<>();


        C c = new C("C");
        ArrayList<? super C> cList = new ArrayList<>();
        cList.add(kotlin);

        cList=new ArrayList<Object>();

    }

    public static class C {
        public String name;

        public C() {
        }

        public C(String name) {
            this.name = name;
        }
    }

    public static class Java extends C {
        public String name;

        public Java() {
        }

        public Java(String name) {
            this.name = name;
        }
    }

    public static class Kotlin extends Java {
        public String name;

        public Kotlin(String name) {
            this.name = name;
        }
    }
}
