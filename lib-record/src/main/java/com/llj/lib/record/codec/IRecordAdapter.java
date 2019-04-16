package com.llj.lib.record.codec;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.llj.lib.record.RecordSetting;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
public interface IRecordAdapter {


    void initRecorder(RecordSetting recordSetting);

    boolean startRecorder(Camera camera, SurfaceHolder surfaceHolder, RecordSetting recordSetting);

    void stopRecorder();

    void releaseRecorder(Camera camera);

    boolean isRecording();

    String getRecordFilePath();

}
