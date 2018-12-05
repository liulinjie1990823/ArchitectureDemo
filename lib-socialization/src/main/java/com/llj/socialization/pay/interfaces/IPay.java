package com.llj.socialization.pay.interfaces;

import android.content.Context;

import com.llj.socialization.IControl;
import com.llj.socialization.pay.callback.PayListener;
import com.llj.socialization.pay.model.PayParam;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public interface IPay extends IControl {

    void init(final Context context, final PayListener listener);

    void doPay(PayParam payParam);

}
