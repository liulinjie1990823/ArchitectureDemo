package com.llj.lib.socialization.sina.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.llj.lib.socialization.sina.api.AccessTokenKeeper;
import com.llj.lib.socialization.sina.login.model.WeiboToken;
import com.llj.socialization.init.SocialManager;
import com.llj.socialization.login.LoginPlatformType;
import com.llj.socialization.login.callback.LoginListener;
import com.llj.socialization.login.interfaces.ILogin;
import com.llj.socialization.login.model.BaseToken;
import com.llj.socialization.login.model.LoginResult;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.SdkListener;
import com.sina.weibo.sdk.openapi.WBAPIFactory;

/**
 * PROJECT:babyphoto_app DESCRIBE: Created by llj on 2017/2/16.
 */

public class LoginSina implements ILogin {


  private IWBAPI        mWbApi;
  private LoginListener mLoginListener;

  private boolean mFetchUserInfo;

  @Override
  public void init(Context context, LoginListener listener, boolean fetchUserInfo,
      boolean fetchWxToken) {
    mFetchUserInfo = fetchUserInfo;

    AuthInfo authInfo = new AuthInfo(context.getApplicationContext(),
        SocialManager.getConfig(context.getApplicationContext()).getSignId(),
        SocialManager.getConfig(context.getApplicationContext()).getSignRedirectUrl(),
        SocialManager.getConfig(context.getApplicationContext()).getSignScope());
    mWbApi = WBAPIFactory.createWBAPI(context.getApplicationContext());
    mWbApi.registerApp(context.getApplicationContext(), authInfo, new SdkListener() {
      @Override
      public void onInitSuccess() {

      }

      @Override
      public void onInitFailure(Exception e) {

      }
    });

    mLoginListener = listener;
  }


  @Override
  public void doLogin(final Activity activity) {
    mWbApi.authorize(activity, new WbAuthListener() {
      @Override
      public void onComplete(Oauth2AccessToken oauth2AccessToken) {
        AccessTokenKeeper.writeAccessToken(activity, oauth2AccessToken);
        //
        WeiboToken weiboToken = WeiboToken.parse(oauth2AccessToken);
        weiboToken.setExpiresIn(oauth2AccessToken.getExpiresTime());
        //
        if (mFetchUserInfo) {
          mLoginListener.beforeFetchUserInfo(weiboToken);
          fetchUserInfo(activity, weiboToken, oauth2AccessToken);
        } else {
          mLoginListener.onLoginResponse(
              new LoginResult(LoginPlatformType.SINA, LoginResult.RESPONSE_LOGIN_SUCCESS,
                  weiboToken));
        }
      }

      @Override
      public void onCancel() {
        mLoginListener.onLoginResponse(
            new LoginResult(LoginPlatformType.SINA, LoginResult.RESPONSE_LOGIN_HAS_CANCEL));
      }

      @Override
      public void onError(UiError uiError) {
        mLoginListener.onLoginResponse(
            new LoginResult(LoginPlatformType.SINA, LoginResult.RESPONSE_LOGIN_FAILURE,
                uiError.errorMessage));
      }

    });
  }

  public void fetchUserInfo(Context context, final BaseToken token, Oauth2AccessToken accessToken) {

  }

  @Override
  public void fetchUserInfo(Context context, BaseToken token) {

  }

  @Override
  public void onNewIntent(Intent data) {
  }

  @Override
  public void handleResult(Activity activity, int requestCode, int resultCode, Intent data) {
    mWbApi.authorizeCallback(activity, requestCode, resultCode, data);
  }

  @Override
  public boolean isInstalled(Context context) {
    return mWbApi.isWBAppInstalled();
  }

  @Override
  public void recycle() {
    mWbApi = null;
    mLoginListener = null;
  }
}
