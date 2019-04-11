package com.llj.lib.record;

import android.hardware.Camera;
import android.view.SurfaceHolder;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
public interface RecordAdapter {

    boolean startRecorder(Camera camera, SurfaceHolder surfaceHolder, RecordSetting recordSetting);

    void releaseRecorder(Camera camera);

    boolean isRecording();

    String getRecordFilePath();

}
