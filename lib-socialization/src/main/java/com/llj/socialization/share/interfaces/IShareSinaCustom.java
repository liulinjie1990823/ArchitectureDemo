package com.llj.socialization.share.interfaces;

import android.app.Activity;

import com.llj.socialization.share.ShareObject;
import com.llj.socialization.share.callback.ShareListener;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/19.
 */

public interface IShareSinaCustom extends IShare {
    void shareCustom(Activity activity, int platform, ShareObject shareObject, ShareListener listener);
}
