package com.llj.lib.record.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.llj.lib.record.CameraHelper;
import com.llj.lib.record.CameraOptCallback;
import com.llj.lib.record.CameraUtil;
import com.llj.lib.record.ICameraHandler;
import com.llj.lib.record.RecordAdapter;
import com.llj.lib.record.RecordSetting;

import java.io.IOException;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
public class LSurfaceView extends SurfaceView implements ICameraHandler {
    public static final String TAG = LSurfaceView.class.getSimpleName();

    private SurfaceHolder mSurfaceHolder;
    private Camera        mCamera;

    private RecordSetting mRecordSetting;


    private int     mFaceType;
    private String  mFlashMode;//默认取消闪光灯
    private boolean mIsAutoFocus;
    private long    mAutoFocusDuration;

    private int                   mSaveWidth;
    private int                   mSaveHeight;
    private String                directoryPath;
    private Bitmap.CompressFormat mCompressFormat;

    private Camera.PreviewCallback mPreviewCallback;
    private CameraOptCallback      mCameraOptCallback;

    private RecordAdapter mMediaRecorder;

    private boolean mLogEnable;


    private int mSensorRotation;//手机旋转的角度，会变化
    private int mDisplayRotation;//preview的角度，一般设置为90度


    public LSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public LSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mOrientationEventListener = new OrientationEventListener(context) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation > 315 || orientation <= 45) {
                    mSensorRotation = 0;
                } else if (orientation <= 135) {
                    mSensorRotation = 90;
                } else if (orientation <= 225) {
                    mSensorRotation = 180;
                } else {
                    mSensorRotation = 270;
                }

                log("mSensorRotation:" + mSensorRotation);
            }
        };
    }

    public void init(RecordSetting recordSetting) {
        this.mRecordSetting = recordSetting;
        this.mLogEnable = recordSetting.isLogEnable();
        this.mSaveWidth = recordSetting.getSaveWidth();
        this.mSaveHeight = recordSetting.getSaveHeight();
        this.mFaceType = recordSetting.getFaceType();
        this.mFlashMode = recordSetting.getFlashMode();
        this.mIsAutoFocus = recordSetting.isAutoFocus();
        this.mCompressFormat = recordSetting.getCompressFormat();
        this.directoryPath = recordSetting.getDirectoryPath();
        this.mAutoFocusDuration = recordSetting.getAutoFocusDuration();
        this.mPreviewCallback = recordSetting.getPreviewCallback();
        this.mCameraOptCallback = recordSetting.getCameraOptCallback();
        this.mMediaRecorder = recordSetting.getRecordAdapter();

        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceHolder = holder;
                onStart();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceHolder = null;
                onDestroy();
            }
        });
    }

    private OrientationEventListener mOrientationEventListener;

    @Override
    public void onStart() {
        if (mSurfaceHolder == null) {
            return;
        }
        if (mCamera == null) {
            if (openCamera(mFaceType)) {
                startPreview();
            }
        } else {
            startPreview();
        }
    }

    @Override
    public void onStop() {
        releaseRecorder();
        releaseOrientationEventListener();
        releaseCamera();
    }

    @Override
    public void onDestroy() {
        releaseRecorder();
        releaseOrientationEventListener();
        releaseCamera();

        mPreviewCallback = null;
        mSurfaceHolder = null;
        mCameraOptCallback = null;
        mMediaRecorder=null;
        mCamera=null;
        mOrientationEventListener=null;
    }

    private void releaseOrientationEventListener(){
        if (mOrientationEventListener != null) {
            mOrientationEventListener.disable();
        }
    }

    /**
     * 开启相机
     *
     * @param faceType 前置/后置
     *
     * @return 是否开启成功
     */
    private boolean openCamera(int faceType) {
        int cameraId = CameraHelper.getCameraId(faceType);
        if (cameraId >= 0) {
            try {
                mCamera = Camera.open(cameraId);
                mDisplayRotation = CameraHelper.setCameraDisplayOrientation(getContext(), mCamera, faceType);
                initParameters(mCamera);
                mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                    @Override
                    public void onPreviewFrame(byte[] data, Camera camera) {
                        if (!mStartContinuousShooting || data == null) {
                            return;
                        }

                    }
                });
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }


    /**
     * @param camera 配置相机基本参数
     */
    private void initParameters(Camera camera) {
        try {
            Camera.Parameters parameters = camera.getParameters();

            //获取与指定狂傲相等或最接近的尺寸
            //设置预览尺寸
            int targetWidth = getHeight();
            int targetHeight = getWidth();
            Camera.Size bestSize = CameraHelper.getBestSize(targetWidth, targetHeight, parameters.getSupportedPreviewSizes());
            if (bestSize != null) {
                parameters.setPreviewSize(bestSize.width, bestSize.height);
            }

            //设置保存图片
            Camera.Size bestPictureSize = CameraHelper.getBestSize(mSaveWidth, mSaveHeight, parameters.getSupportedPictureSizes());
            if (bestPictureSize != null) {
                parameters.setPictureSize(bestPictureSize.width, bestPictureSize.height);
            }

            //对焦模式
            if (CameraHelper.isSupportFocus(parameters, Camera.Parameters.FOCUS_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }

            //闪光灯模式
            if (CameraHelper.isSupportFlashMode(parameters, mFlashMode)) {
                parameters.setFlashMode(mFlashMode);
            }

            camera.setParameters(parameters);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 开启界面预览
     */
    private void startPreview() {
        if (mCamera != null) {
            try {
                mOrientationEventListener.enable();

                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
                //对焦一次
                CameraHelper.autoFocus(mCamera);
                //自动聚焦
                startAutoFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 开始自动聚焦
     */
    private void startAutoFocus() {
        if (mIsAutoFocus) {
            removeCallbacks(mAutoFocusRunnable);
            postDelayed(mAutoFocusRunnable, mAutoFocusDuration);
        }
    }

    private Runnable mAutoFocusRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsAutoFocus) {
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success && mCamera != null) {
                            mCamera.cancelAutoFocus();
                            startAutoFocus();
                        }
                    }
                });
            }
        }
    };


    private void log(String msg) {
        if (mLogEnable) {
            Log.i(TAG, msg);
        }
    }

    private void releaseRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.releaseRecorder(mCamera);
        }
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
        }
    }

    @Override
    public Camera camera() {
        return mCamera;
    }

    @Override
    public boolean isFlashOn() {
        return CameraHelper.isFlashOn(mCamera);
    }

    @Override
    public boolean flashOn() {
        return CameraHelper.flashOn(mCamera);
    }

    @Override
    public boolean flashOff() {
        return CameraHelper.flashOff(mCamera);
    }

    @Override
    public void switchCamera() {
        mFaceType = mFaceType == Camera.CameraInfo.CAMERA_FACING_BACK ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        onStop();
        onStart();
    }

    @Override
    public void takePicture() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                if (mCamera != null) {
                    mCamera.startPreview();
                }
                CameraUtil.savePic(mFaceType, mDisplayRotation, mSensorRotation, directoryPath, data, mCompressFormat, mCameraOptCallback);
            }
        });
    }

    private boolean mStartContinuousShooting;//是否进行连拍

    @Override
    public void takeContinuousShooting() {
        mStartContinuousShooting = true;
    }

    @Override
    public boolean isRecording() {
        if (mMediaRecorder != null) {
            return mMediaRecorder.isRecording();
        }
        return false;

    }

    @Override
    public boolean startRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.startRecorder(mCamera, mSurfaceHolder, mRecordSetting);
        }
        return true;
    }

    @Override
    public boolean finishRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.releaseRecorder(mCamera);
        }
        String path = null;
        if (mMediaRecorder != null) {
            path = mMediaRecorder.getRecordFilePath();
        }
        if (mCameraOptCallback != null) {
            mCameraOptCallback.onVideoRecordComplete(path);
        }
        return true;
    }


}
