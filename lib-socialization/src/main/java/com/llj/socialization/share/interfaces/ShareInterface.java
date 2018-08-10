package com.llj.socialization.share.interfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.model.ShareImageObject;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public interface ShareInterface {

    void shareTitle(Activity activity, int platform, String title,String targetUrl);

    void shareDescription(Activity activity, int platform, String description,String targetUrl);

    void shareText(Activity activity, int platform, String title, String description, String targetUrl);

    void shareImage(Activity activity, int platform, ShareImageObject shareImageObject, String targetUrl);

    void shareMedia(Activity activity, int platform, String title, String description, ShareImageObject shareImageObject, String targetUrl);

    void handleResult(Intent data);

    boolean isInstalled(Context context);

    void recycle();

     void sendFailure(Activity activity,ShareListener shareListener, String message);

}
