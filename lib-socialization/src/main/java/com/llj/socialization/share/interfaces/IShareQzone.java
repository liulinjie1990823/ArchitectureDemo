package com.llj.socialization.share.interfaces;

import android.app.Activity;

import com.llj.socialization.share.ShareObject;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/19.
 */

public interface IShareQzone extends IShare {

    void shareTalkAbout(Activity activity, int platform, ShareObject shareObject);

}
