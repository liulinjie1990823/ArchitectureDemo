package com.llj.lib.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.llj.lib.utils.AInputMethodManagerUtils;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-06-09
 */
public abstract class BaseDialogFragment extends MvcBaseFragment {

    private boolean mShowInput;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.no_dim_dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme());
        if (mShowInput) {
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (getActivity() == null) {
                                return;
                            }
                            AInputMethodManagerUtils.showOrHideInput(getActivity(), true);
                        }
                    });
                }
            });
        }
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog().getWindow() == null) {
            return;
        }
        setWindowParams(getDialog().getWindow(), -1, -1, Gravity.BOTTOM);
    }

    public void setWindowParams(Window window, int width, int height, int gravity) {
        WindowManager.LayoutParams params = window.getAttributes();
        // setContentView设置布局的透明度，0为透明，1为实际颜色,该透明度会使layout里的所有空间都有透明度，不仅仅是布局最底层的view
        // params.alpha = 1f;
        // 窗口的背景，0为透明，1为全黑
        // params.dimAmount = 0f;
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);
    }

    public void setShowInput(boolean showInput) {
        mShowInput = showInput;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mShowInput) {
            if (getActivity() == null) {
                return;
            }
            AInputMethodManagerUtils.hideSoftInputFromWindow(getDialog());
        }
    }
}
