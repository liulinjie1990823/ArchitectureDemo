package com.llj.socialization.share.interfaces;

import android.app.Activity;

import com.llj.socialization.share.model.ShareImageObject;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/19.
 */

public interface ShareQzoneInterface extends ShareInterface {

    void shareTalkAbout(Activity activity, int platform, ShareImageObject shareImageObject, String targetUrl);

}
