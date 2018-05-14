package com.llj.lib.utils.callback;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/26
 */
public interface ProgressListener {
    void onLoadProgress(long bytesRead, long contentLength, boolean done);
}
