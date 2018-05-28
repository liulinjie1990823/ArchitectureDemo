package com.llj.architecturedemo;

import com.llj.lib.base.mvp.IModel;
import com.llj.lib.base.mvp.IView;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/17
 */
public class MainContract {

    interface View extends IView {

        void toast();

    }

    interface Model extends IModel {

    }
}
