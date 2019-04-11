package com.llj.lib.record;

import android.hardware.Camera;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
public interface ICameraHandler {

    Camera camera();

    boolean flashOn();

    boolean isFlashOn();

    boolean flashOff();

    void switchCamera();

    void takePicture();

    void takeContinuousShooting();

    boolean isRecording();

    boolean startRecorder();

    boolean finishRecorder();


    void onStart();

    void onStop();

    void onDestroy();

}
