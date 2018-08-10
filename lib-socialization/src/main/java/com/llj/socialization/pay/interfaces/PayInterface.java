package com.llj.socialization.pay.interfaces;

import android.content.Context;
import android.content.Intent;

import com.llj.socialization.pay.model.PayParam;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public interface PayInterface {


    void doPay(PayParam payParam);

    void handleResult(int requestCode, int resultCode, Intent data);

    boolean isInstalled(Context context);

    void recycle();

}
