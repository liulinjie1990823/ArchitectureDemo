package com.llj.socialization.share.callback;

import android.graphics.Bitmap;

import com.llj.socialization.share.SharePlatformType;
import com.llj.socialization.share.model.ShareResult;


/**
 * Created by shaohui on 2016/11/18.
 */

public abstract class ShareListener {
    @SharePlatformType.Platform int platform;

    public ShareListener() {
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }


    //<editor-fold desc="自己的回调">
    public void onStart() {
    }

    public abstract Bitmap getExceptionImage();

    public abstract String imageLocalPathWrap(String imageLocalPath);

    public abstract void onShareResponse(ShareResult shareResult);
    //</editor-fold>

}
