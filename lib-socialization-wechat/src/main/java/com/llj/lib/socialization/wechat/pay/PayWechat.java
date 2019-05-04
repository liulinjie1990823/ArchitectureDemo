package com.llj.lib.socialization.wechat.pay;

import android.content.Context;
import android.content.Intent;

import com.llj.socialization.init.SocialManager;
import com.llj.socialization.log.INFO;
import com.llj.socialization.pay.PayPlatformType;
import com.llj.socialization.pay.callback.PayListener;
import com.llj.socialization.pay.interfaces.IPay;
import com.llj.socialization.pay.model.PayParam;
import com.llj.socialization.pay.model.PayResult;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * project:android
 * describe:
 * Created by llj on 2017/8/14.
 */

public class PayWechat implements IPay {

    private IWXAPI mIWXAPI;

    private PayListener        mPayListener;
    private IWXAPIEventHandler mIWXAPIEventHandler;


    @Override
    public void init(Context context, PayListener listener) {
        mIWXAPI = WXAPIFactory.createWXAPI(context, SocialManager.getConfig(context).getWxId());
        mPayListener = listener;

        mIWXAPIEventHandler = new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {
            }

            @Override
            public void onResp(BaseResp baseResp) {
                if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    switch (baseResp.errCode) {
                        case BaseResp.ErrCode.ERR_OK:
                            mPayListener.onPayResponse(new PayResult(PayPlatformType.WECHAT, PayResult.RESPONSE_PAY_SUCCESS));
                            break;
                        case BaseResp.ErrCode.ERR_USER_CANCEL:
                            mPayListener.onPayResponse(new PayResult(PayPlatformType.WECHAT, PayResult.RESPONSE_PAY_HAS_CANCEL));
                            break;
                        case BaseResp.ErrCode.ERR_SENT_FAILED:
                            mPayListener.onPayResponse(new PayResult(PayPlatformType.WECHAT, PayResult.RESPONSE_PAY_FAILURE, INFO.WX_ERR_SENT_FAILED));
                            break;
                        case BaseResp.ErrCode.ERR_UNSUPPORT:
                            mPayListener.onPayResponse(new PayResult(PayPlatformType.WECHAT, PayResult.RESPONSE_PAY_FAILURE, INFO.WX_ERR_UNSUPPORT));
                            break;
                        case BaseResp.ErrCode.ERR_AUTH_DENIED:
                            mPayListener.onPayResponse(new PayResult(PayPlatformType.WECHAT, PayResult.RESPONSE_PAY_FAILURE, INFO.WX_ERR_AUTH_DENIED));
                            break;
                        default:
                            mPayListener.onPayResponse(new PayResult(PayPlatformType.WECHAT, PayResult.RESPONSE_PAY_FAILURE, INFO.WX_ERR_AUTH_ERROR));
                    }
                }
            }
        };
    }

    @Override
    public void doPay(PayParam payParam) {
        PayReq request = new PayReq();
        request.appId = payParam.getAppId();
        request.nonceStr = payParam.getNonceStr();
        request.packageValue = "Sign=WXPay";
        request.partnerId = payParam.getMch_id();
        request.prepayId = payParam.getPrepay_id();
        request.timeStamp = payParam.getTimeStamp() + "";
        request.sign = genAppSign(request, payParam.getMch_key());
        mIWXAPI.sendReq(request);
    }

    private static String genAppSign(PayReq reques, String key) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("appid=");
        stringBuilder.append(reques.appId);
        stringBuilder.append("&noncestr=");
        stringBuilder.append(reques.nonceStr);//
        stringBuilder.append("&package=");
        stringBuilder.append(reques.packageValue);
        stringBuilder.append("&partnerid=");
        stringBuilder.append(reques.partnerId);
        stringBuilder.append("&prepayid=");//
        stringBuilder.append(reques.prepayId);
        stringBuilder.append("&timestamp=");//
        stringBuilder.append(reques.timeStamp);
        stringBuilder.append("&key=");
        stringBuilder.append(key);

        return MD5(stringBuilder.toString());
    }

    /**
     * MD5加密，大写
     *
     * @return 加密后String
     */
    public static final String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str).toUpperCase(Locale.getDefault());
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        mIWXAPI.handleIntent(data, mIWXAPIEventHandler);
    }

    @Override
    public boolean isInstalled(Context context) {
        return mIWXAPI.isWXAppInstalled();
    }

    @Override
    public void recycle() {
        if (mIWXAPI != null) {
            mIWXAPI.detach();
        }
    }
}
