package com.llj.architecturedemo.ui.activity;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.record.CameraOptCallback;
import com.llj.lib.record.CameraOptCallbackAdapter;
import com.llj.lib.record.RecordSetting;
import com.llj.lib.record.widget.CameraPreviewView;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
@Route(path = CRouter.APP_RECORD_VIDEO_ACTIVITY)
public class RecordVideoActivity extends AppMvcBaseActivity {
    private static final String mSavePath = Environment.getExternalStorageDirectory() + "/" + "camera_test";//相机拍照/录像缓存路径

    @BindView(R.id.avatar_img)   ImageView         mAvatarImg;
    @BindView(R.id.surface_view) CameraPreviewView mSurfaceView;
    @BindView(R.id.flash_btn)    Button            mFlashBtn;
    @BindView(R.id.switch_btn)   Button            mSwitchBtn;
    @BindView(R.id.shot_btn)     Button            mShotBtn;
    @BindView(R.id.record_btn)   Button            mRecordBtn;

    @Override
    public int layoutId() {
        return R.layout.activity_record_video;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        setTranslucentStatusBar(getWindow(), true);

        mFlashBtn.setOnClickListener(mOnClickListener);
        mSwitchBtn.setOnClickListener(mOnClickListener);
        mShotBtn.setOnClickListener(mOnClickListener);
        mRecordBtn.setOnClickListener(mOnClickListener);

    }

    private View.OnClickListener mOnClickListener   = new DebouncingOnClickListener() {
        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.flash_btn:
                    break;
                case R.id.switch_btn:
                    mSurfaceView.switchCamera();
                    resetRecordStatus();
                    resetFlashStatus();
                    break;
                case R.id.shot_btn:
                    mSurfaceView.takePicture();
                    break;
                case R.id.record_btn:
                    if (mSurfaceView.isRecording()) {
                        mSurfaceView.finishRecorder();
                        mRecordBtn.setText("开始录像");
                    } else {
                        if (mSurfaceView.startRecorder()) {
                            mRecordBtn.setText("结束录像");
                        }
                    }
                    break;
            }
        }
    };
    //相机操作数据回调
    private CameraOptCallback    mCameraOptCallback = new CameraOptCallbackAdapter() {
        @Override
        public void onPictureComplete(String path, Bitmap bitmap) {
            if (mAvatarImg != null) {
                mAvatarImg.setImageBitmap(bitmap);
            }
            showLongToast("图片保存路径 = " + path);
        }

        @Override
        public void onVideoRecordComplete(String path) {
            showLongToast("录像保存路径 = " + path);
        }
    };

    /**
     * 初始化相机操作类
     * build()方法一旦调用，就会启用相机
     */
    private void initCamera() {
        RecordSetting recordSetting = new RecordSetting.Builder()
                .setLogEnable(true)
                .previewWidthAndHeight(1080, 1920)
                .isAutoFocus(false)
                .directoryPath(mSavePath)
                .CameraOptCallback(mCameraOptCallback)
                .faceType(Camera.CameraInfo.CAMERA_FACING_BACK)
                .flashMode(Camera.Parameters.FLASH_MODE_AUTO)
                .isScaleEnable(true)
                .build();
        mSurfaceView.init(recordSetting);
//        if (mCameraHelper == null) {
//            mCameraHelper = new CameraHelper.Builder()
//                    .setActivity(this)
//                    .setSurfaceView(mSurfaceView)
//                    .setAutoFocus(true)//默认开启，3秒一次对焦
//                    .setCameraOptCallback(mCameraOptCallback)//相机操作回调
//                    .setDirectoryPath(mSavePath)//缓存路径
//                    .setFlashEnable(false)//是否开启闪光灯拍照
//                    .setScaleEnable(true)//是否支持手势缩放
//                    .build();
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSurfaceView.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSurfaceView.onStop();
    }

    @Override
    protected void onDestroy() {
        mSurfaceView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void initData() {
        initCamera();
    }


    /**
     * 重置录像按钮的状态
     */
    private void resetRecordStatus() {
        mRecordBtn.setText("开始录像");
    }

    /**
     * 重置闪光灯按钮的状态
     */
    private void resetFlashStatus() {
        mFlashBtn.setText(mSurfaceView.isFlashOn() ? "关闭闪关灯" : "打开闪关灯");
    }
}
