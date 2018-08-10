package com.llj.socialization.share.interfaces;

import android.app.Activity;

import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.model.ShareImageObject;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/19.
 */

public interface ShareSinaCustomInterface extends ShareInterface {
    void shareCustom(Activity activity, int platform, String description, ShareImageObject shareImageObject, ShareListener listener);
}
