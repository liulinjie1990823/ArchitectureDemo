package com.llj.setting.ui.view;

import com.llj.application.vo.UserInfoVo;
import com.llj.lib.base.mvp.IBaseTaskView1;
import com.llj.lib.base.mvp.IBaseTaskView2;
import com.llj.setting.ui.model.MobileInfoVo;

/**
 * ArchitectureDemo.
 * describe:
 * @author llj
 * date 2019/3/25
 */
public interface SettingView  {


    interface UserInfo extends IBaseTaskView1<UserInfoVo> {

    }

    interface MobileInfo extends IBaseTaskView2<MobileInfoVo> {

    }
}
