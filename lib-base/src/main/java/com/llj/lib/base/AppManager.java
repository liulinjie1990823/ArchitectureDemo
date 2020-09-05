package com.llj.lib.base;

import com.llj.lib.base.config.JumpConfig;
import com.llj.lib.base.config.NetworkConfig;
import com.llj.lib.base.config.ToolbarConfig;
import com.llj.lib.base.config.UserInfoConfig;

/**
 * ArchitectureDemo.
 *
 * describe:配置信息类
 *
 * @author llj
 * @date 2019-10-22
 */
public class AppManager {

  private static AppManager sInstance;

  public static AppManager getInstance() {
    if (sInstance == null) {
      synchronized (AppManager.class) {
        if (sInstance == null) {
          sInstance = new AppManager();
        }
      }
    }
    return sInstance;
  }


  private ToolbarConfig  mToolbarConfig;
  private NetworkConfig  mNetworkConfig;
  private JumpConfig     mJumpConfig;
  private UserInfoConfig mUserInfoConfig;

  public NetworkConfig getNetworkConfig() {
    return mNetworkConfig;
  }

  public void setNetworkConfig(NetworkConfig networkConfig) {
    mNetworkConfig = networkConfig;
  }

  public JumpConfig getJumpConfig() {
    return mJumpConfig;
  }

  public void setJumpConfig(JumpConfig jumpConfig) {
    mJumpConfig = jumpConfig;
  }

  public ToolbarConfig getToolbarConfig() {
    return mToolbarConfig;
  }

  public void setToolbarConfig(ToolbarConfig toolbarConfig) {
    mToolbarConfig = toolbarConfig;
  }

  public UserInfoConfig getUserInfoConfig() {
    return mUserInfoConfig;
  }

  public void setUserInfoConfig(UserInfoConfig userInfoConfig) {
    mUserInfoConfig = userInfoConfig;
  }
}
