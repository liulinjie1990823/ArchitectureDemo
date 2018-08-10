package com.llj.socialization.share.callback;

import android.graphics.Bitmap;

import com.llj.socialization.share.SharePlatformType;
import com.llj.socialization.share.model.ShareResult;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;


/**
 * Created by shaohui on 2016/11/18.
 */

public abstract class ShareListener implements IUiListener, WbShareCallback, IWXAPIEventHandler {
    @SharePlatformType.Platform int platform;

    public ShareListener() {
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    //<editor-fold desc="qq和qq空间回调">
    @Override
    public final void onComplete(Object o) {
        onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_SUCCESS));
    }

    @Override
    public final void onError(UiError uiError) {
        onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_FAILURE, uiError.errorMessage));
    }

    @Override
    public final void onCancel() {
        onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_HAS_CANCEL));
    }
    //</editor-fold>

    //<editor-fold desc="微信的回调">
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.e("llj", "onResp:" + "resp.getType():" + baseResp.getType() + "resp.errCode:" + baseResp.errCode + "resp.errStr:" + baseResp.errStr);

        if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == baseResp.getType()) {
            //分享
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_SUCCESS));
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_HAS_CANCEL));
                    break;
                case BaseResp.ErrCode.ERR_SENT_FAILED:
                    onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_FAILURE, baseResp.errStr));
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_AUTH_DENIED));
                    break;
                default:
                    onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_FAILURE, baseResp.errStr));
                    break;
            }
        }
    }
    //</editor-fold>


    //<editor-fold desc="自己的回调">
    public void onStart() {
    }

    public abstract Bitmap getExceptionImage();

    public abstract String imageLocalPathWrap(String imageLocalPath);

    public abstract void onShareResponse(ShareResult shareResult);
    //</editor-fold>

    //<editor-fold desc="微博回调">
    @Override
    public void onWbShareSuccess() {
        onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_SUCCESS));
    }

    @Override
    public void onWbShareCancel() {
        onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_HAS_CANCEL));
    }

    @Override
    public void onWbShareFail() {
        onShareResponse(new ShareResult(platform, ShareResult.RESPONSE_SHARE_FAILURE));
    }
    //</editor-fold>
}
