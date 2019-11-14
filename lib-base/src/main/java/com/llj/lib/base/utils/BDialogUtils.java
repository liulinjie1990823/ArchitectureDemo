package com.llj.lib.base.utils;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import com.llj.lib.base.R;


/**
 * Created by liulj on 16/5/25.
 */
public class BDialogUtils {


    public static AlertDialog showCommonDialog(Context context, String message, final AlertDialog.OnClickListener positiveClickListener) {
        return showCommonDialog(context, null, message, positiveClickListener, null);
    }

    public static AlertDialog showCommonDialog(Context context, String message, final AlertDialog.OnClickListener positiveClickListener, final AlertDialog.OnClickListener negativeClickListener) {
        return showCommonDialog(context, null, message, positiveClickListener, negativeClickListener);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param positiveClickListener
     * @param negativeClickListener
     * @return
     */
    public static AlertDialog showCommonDialog(Context context, String title, String message, final AlertDialog.OnClickListener positiveClickListener, final AlertDialog.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title == null)
            builder.setTitle(context.getResources().getString(R.string.common_alert));
        else
            builder.setTitle(title);
        if (message == null) {
            throw new RuntimeException("message can not be null");
        }
        builder.setMessage(message);
        builder.setPositiveButton(context.getResources().getString(R.string.common_confirm), new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (positiveClickListener != null)
                    positiveClickListener.onClick(dialog, which);

            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.common_cancel), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (negativeClickListener != null)
                    negativeClickListener.onClick(dialog, which);
            }
        });
        return builder.create();
    }


    public static AlertDialog showCommonDialog(Context context, int messageResId, final AlertDialog.OnClickListener positiveClickListener) {
        return showCommonDialog(context, 0, messageResId, positiveClickListener, null);
    }

    public static AlertDialog showCommonDialog(Context context, int messageResId, final AlertDialog.OnClickListener positiveClickListener, final AlertDialog.OnClickListener negativeClickListener) {
        return showCommonDialog(context, 0, messageResId, positiveClickListener, negativeClickListener);
    }

    /**
     * @param context
     * @param titleResId
     * @param messageResId
     * @param positiveClickListener
     * @param negativeClickListener
     * @return
     */
    public static AlertDialog showCommonDialog(Context context, int titleResId, int messageResId, final AlertDialog.OnClickListener positiveClickListener, final AlertDialog.OnClickListener negativeClickListener) {
        return showCommonDialog(context, titleResId, messageResId, 0, 0, positiveClickListener, negativeClickListener);
    }

    public static AlertDialog showCommonDialog(Context context, int titleResId, int messageResId, int positiveResId, int negativeResId, final AlertDialog.OnClickListener positiveClickListener, final AlertDialog.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (titleResId == 0) {
            builder.setTitle(context.getResources().getString(R.string.common_alert));
        } else {
            builder.setTitle(titleResId);
        }
        if (messageResId == 0) {
            throw new RuntimeException("messageResId can not be 0");
        }
        builder.setMessage(messageResId);

        builder.setPositiveButton(positiveResId == 0 ? context.getResources().getString(R.string.common_confirm) : context.getResources().getString(positiveResId),
                new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (positiveClickListener != null)
                            positiveClickListener.onClick(dialog, which);

                    }
                });
        builder.setNegativeButton(negativeResId == 0 ? context.getResources().getString(R.string.common_cancel) : context.getResources().getString(negativeResId),
                new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (negativeClickListener != null)
                            negativeClickListener.onClick(dialog, which);
                    }
                });
        return builder.create();
    }
}
