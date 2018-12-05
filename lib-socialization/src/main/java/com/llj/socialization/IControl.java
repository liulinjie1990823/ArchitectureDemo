package com.llj.socialization;

import android.content.Context;
import android.content.Intent;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2018/9/4
 */
public interface IControl {
    //处理结果
    void handleResult(int requestCode, int resultCode, Intent data);

    //是否安装相关应用
    boolean isInstalled(Context context);

    //垃圾回收
    void recycle();
}
