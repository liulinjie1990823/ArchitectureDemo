package com.llj.login.ui.view;

import com.llj.application.vo.UserInfoVo;
import com.llj.lib.base.mvp.IBaseActivityView;
import com.llj.lib.base.mvp.IBaseTaskView1;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-11
 */
public interface ILoginView extends IBaseActivityView {

    interface PhoneLogin extends IBaseTaskView1<UserInfoVo> {
    }
}
