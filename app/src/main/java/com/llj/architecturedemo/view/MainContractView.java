package com.llj.architecturedemo.view;

import com.llj.architecturedemo.db.entity.MobileEntity;
import com.llj.lib.base.mvp.IView;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/4
 */
public interface MainContractView extends IView {

    void toast(MobileEntity mobile);
}
