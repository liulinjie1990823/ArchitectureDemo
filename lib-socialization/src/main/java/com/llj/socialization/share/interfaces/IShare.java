package com.llj.socialization.share.interfaces;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.llj.socialization.IControl;
import com.llj.socialization.share.ShareObject;
import com.llj.socialization.share.callback.ShareListener;
import com.llj.socialization.share.model.ShareResult;
import com.llj.socialization.utils.Utils;

/**
 * PROJECT:babyphoto_app
 * DESCRIBE:
 * Created by llj on 2017/1/18.
 */

public interface IShare extends IControl {

    void init(final Context context, final ShareListener listener);

    //标题+链接
    void shareTitle(Activity activity, int platform, @NonNull ShareObject shareObject);

    //描述+链接
    void shareDescription(Activity activity, int platform, @NonNull ShareObject shareObject);

    //标题+描述+链接
    void shareText(Activity activity, int platform, @NonNull ShareObject shareObject);

    //图片+链接
    void shareImage(Activity activity, int platform, @NonNull ShareObject shareObject);

    //web+链接
    void shareWeb(Activity activity, int platform, @NonNull ShareObject shareObject);

    default void shareMiniProgram(Activity activity, int platform, @NonNull ShareObject shareObject) {
    }

    default void sendFailure(Context context, ShareListener shareListener, String message) {
        Utils.finishActivity(context);
        shareListener.onShareResponse(
            new ShareResult(shareListener.getPlatform(), ShareResult.RESPONSE_SHARE_FAILURE,
                message));
    }


}
