package com.llj.lib.socialization.qq.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.llj.socialization.init.SocialManager;
import com.llj.socialization.log.INFO;
import com.llj.socialization.log.Logger;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.login.callback.LoginListener;
import com.llj.socialization.login.interfaces.ILogin;
import com.llj.socialization.login.model.BaseToken;
import com.llj.socialization.login.model.LoginResult;
import com.llj.socialization.login.model.QQToken;
import com.llj.socialization.login.model.QQUser;
import com.llj.socialization.utils.InstallUtil;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * PROJECT:babyphoto_app DESCRIBE: Created by llj on 2017/2/16.
 */


public class LoginQQ implements ILogin {

  private static final String SCOPE = "all";

  private Tencent     mTencent;
  private IUiListener mIUiListener;

  private LoginListener mLoginListener;

  private boolean mFetchUserInfo;

  @Override
  public void init(Context context, LoginListener listener, boolean fetchUserInfo,
      boolean fetchWxToken) {
    mFetchUserInfo = fetchUserInfo;

    mTencent = Tencent
        .createInstance(SocialManager.getConfig(context.getApplicationContext()).getQqId(),
            context.getApplicationContext());
    mLoginListener = listener;
    mIUiListener = new IUiListener() {
      @Override
      public void onComplete(Object o) {
        Logger.e(INFO.QQ_AUTH_SUCCESS);
        try {
          //解析JSONObject
          QQToken token = QQToken.parse((JSONObject) o);
          //设置参数
          mTencent.setAccessToken(token.getAccessToken(), token.getExpiresIn() + "");
          mTencent.setOpenId(token.getOpenid());
          //
          if (mFetchUserInfo) {
            mLoginListener.beforeFetchUserInfo(token);
            fetchUserInfo(context, token);
          } else {
            mLoginListener.onLoginResponse(
                new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_SUCCESS, token));
          }
        } catch (JSONException e) {
          Logger.e(INFO.ILLEGAL_TOKEN);
          mLoginListener.onLoginResponse(
              new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_FAILURE,
                  e.getMessage()));
        }
      }

      @Override
      public void onError(UiError uiError) {
        Logger.e(INFO.QQ_LOGIN_ERROR);
        mLoginListener.onLoginResponse(
            new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_FAILURE,
                uiError.errorDetail));
      }

      @Override
      public void onCancel() {
        Logger.e(INFO.AUTH_CANCEL);
        mLoginListener.onLoginResponse(
            new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_HAS_CANCEL));
      }
    };
  }


  @Override
  public void doLogin(final Activity activity) {
    mTencent.login(activity, SCOPE, mIUiListener);
  }

  @Override
  public void fetchUserInfo(Context context, final BaseToken token) {
    UserInfo info = new UserInfo(context, mTencent.getQQToken());
    info.getUserInfo(new IUiListener() {
      @Override
      public void onComplete(Object object) {
        QQUser qqUser;
        try {
          qqUser = QQUser.parse(token.getOpenid(), (JSONObject) object);

          Logger.e(INFO.QQ_FETCH_USER_INFO_SUCCESS);

          mLoginListener.onLoginResponse(
              new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_SUCCESS, object,
                  token, qqUser));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onError(UiError uiError) {
        mLoginListener.onLoginResponse(
            new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_FAILURE,
                uiError.errorDetail));
      }

      @Override
      public void onCancel() {
        mLoginListener.onLoginResponse(
            new LoginResult(LoginPlatformType.QQ, LoginResult.RESPONSE_LOGIN_HAS_CANCEL));
      }
    });
  }

  @Override
  public void onNewIntent(Intent data) {

  }

  @Override
  public void handleResult(int requestCode, int resultCode, Intent data) {
    Tencent.handleResultData(data, mIUiListener);
  }


  @Override
  public boolean isInstalled(Context context) {
    return InstallUtil.isQQInstalled(context);
  }

  @Override
  public void recycle() {
    mTencent.releaseResource();
    mIUiListener = null;
    mLoginListener = null;
    mTencent = null;
  }
}
