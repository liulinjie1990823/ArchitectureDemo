package com.llj.lib.net;

import android.app.Dialog;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/10
 */
public interface IRequestDialog extends ITag{

    Dialog getDialog();

    void customShow();

    void customDismiss();

}
