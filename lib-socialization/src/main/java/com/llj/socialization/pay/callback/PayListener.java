package com.llj.socialization.pay.callback;

import com.llj.socialization.pay.PayPlatformType;
import com.llj.socialization.pay.model.PayResult;

/**
 * project:android
 * describe:
 * Created by llj on 2017/8/13.
 */

public abstract class PayListener {

    @PayPlatformType.Platform int platform;

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public void onStart() {
    }

    public abstract void onPayResponse(PayResult result);


}
