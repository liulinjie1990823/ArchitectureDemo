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


    void initCamera(Camera camera, int displayRotation, RecordSetting recordSetting);

    void initRecorder(Camera camera, SurfaceHolder surfaceHolder, RecordSetting recordSetting);

    boolean startRecorder();

    boolean resumeRecording();

    boolean pauseRecording();

    boolean stopRecorder();

    boolean releaseRecorder(Camera camera, boolean deleteFile);

    boolean isRecording();

    String getRecordFilePath();

}
