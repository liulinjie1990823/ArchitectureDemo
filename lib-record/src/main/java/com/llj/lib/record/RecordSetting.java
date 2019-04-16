package com.llj.lib.record;

import android.graphics.Bitmap;
import android.hardware.Camera;

import com.llj.lib.record.codec.IRecordAdapter;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
public class RecordSetting {

    private boolean logEnable;

    private int saveWidth;//保存宽度
    private int saveHeight;//保存高度
    private int audioSamplingRate;//设置录制的音频采样率
    private int videoFrameRate;//设置要捕获的视频的帧速率

    private int previewWidth;
    private int previewHeight;

    private int    faceType;
    private String flashMode;//闪光灯模式

    private boolean isAutoFocus;//是否自动聚焦
    private long    autoFocusDuration;

    private boolean isScaleEnable;//是否支持缩放

    private String                directoryPath;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;

    private Camera.PreviewCallback mPreviewCallback;
    private CameraOptCallback      mCameraOptCallback;

    private IRecordAdapter mRecordAdapter;

    public boolean isLogEnable() {
        return logEnable;
    }

    public int getSaveWidth() {
        return saveWidth;
    }

    public int getSaveHeight() {
        return saveHeight;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public int getFaceType() {
        return faceType;
    }

    public String getFlashMode() {
        return flashMode;
    }

    public boolean isAutoFocus() {
        return isAutoFocus;
    }

    public long getAutoFocusDuration() {
        return autoFocusDuration;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public Bitmap.CompressFormat getCompressFormat() {
        return mCompressFormat;
    }

    public Camera.PreviewCallback getPreviewCallback() {
        return mPreviewCallback;
    }

    public CameraOptCallback getCameraOptCallback() {
        return mCameraOptCallback;
    }

    public IRecordAdapter getRecordAdapter() {
        return mRecordAdapter;
    }

    public boolean isScaleEnable() {
        return isScaleEnable;
    }

    public int getAudioSamplingRate() {
        return audioSamplingRate;
    }

    public int getVideoFrameRate() {
        return videoFrameRate;
    }

    public static final class Builder {
        private boolean logEnable;

        private int previewWidth;
        private int previewHeight;

        private int     faceType          = Camera.CameraInfo.CAMERA_FACING_BACK;
        private String  flashMode         = Camera.Parameters.FLASH_MODE_OFF;//闪光灯模式
        private boolean isAutoFocus       = false;//是否自动聚焦
        private long    autoFocusDuration = 3000;

        private Camera.PreviewCallback mPreviewCallback;
        private CameraOptCallback      mCameraOptCallback;


        private boolean isScaleEnable;//是否支持缩放

        private int                   saveWidth       = 1920;
        private int                   saveHeight      = 1080;
        private int                   audioSamplingRate;//设置录制的音频采样率
        private int                   videoFrameRate  = 30;//设置要捕获的视频的帧速率
        private String                directoryPath;
        private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;

        private IRecordAdapter mRecordAdapter;

        public Builder() {
        }

        public Builder setLogEnable(boolean logEnable) {
            this.logEnable = logEnable;
            return this;
        }

        public Builder saveWidthAndHeight(int saveWidth, int saveHeight) {
            this.saveWidth = saveWidth;
            this.saveHeight = saveHeight;
            return this;
        }

        public Builder audioSamplingRate(int audioSamplingRate) {
            this.audioSamplingRate = audioSamplingRate;
            return this;
        }

        public Builder videoFrameRate(int videoFrameRate) {
            this.videoFrameRate = videoFrameRate;
            return this;
        }

        public Builder previewWidthAndHeight(int previewWidth, int previewHeight) {
            this.previewWidth = previewWidth;
            this.previewHeight = previewHeight;
            return this;
        }

        public Builder faceType(int faceType) {
            this.faceType = faceType;
            return this;
        }

        public Builder flashMode(String flashMode) {
            this.flashMode = flashMode;
            return this;
        }

        public Builder isAutoFocus(boolean isAutoFocus) {
            this.isAutoFocus = isAutoFocus;
            return this;
        }

        public Builder autoFocusDuration(long autoFocusDuration) {
            this.autoFocusDuration = autoFocusDuration;
            return this;
        }

        public Builder isScaleEnable(boolean isScaleEnable) {
            this.isScaleEnable = isScaleEnable;
            return this;
        }

        public Builder directoryPath(String directoryPath) {
            this.directoryPath = directoryPath;
            return this;
        }

        public Builder CompressFormat(Bitmap.CompressFormat CompressFormat) {
            this.mCompressFormat = CompressFormat;
            return this;
        }

        public Builder PreviewCallback(Camera.PreviewCallback PreviewCallback) {
            this.mPreviewCallback = PreviewCallback;
            return this;
        }

        public Builder CameraOptCallback(CameraOptCallback CameraOptCallback) {
            this.mCameraOptCallback = CameraOptCallback;
            return this;
        }

        public Builder RecordAdapter(IRecordAdapter RecordAdapter) {
            this.mRecordAdapter = RecordAdapter;
            return this;
        }

        public RecordSetting build() {
            RecordSetting recordSetting = new RecordSetting();
            recordSetting.logEnable = this.logEnable;
            recordSetting.autoFocusDuration = this.autoFocusDuration;
            recordSetting.saveWidth = this.saveWidth;
            recordSetting.saveHeight = this.saveHeight;
            recordSetting.mPreviewCallback = this.mPreviewCallback;
            recordSetting.faceType = this.faceType;
            recordSetting.flashMode = this.flashMode;
            recordSetting.mRecordAdapter = this.mRecordAdapter;
            recordSetting.mCompressFormat = this.mCompressFormat;
            recordSetting.directoryPath = this.directoryPath;
            recordSetting.mCameraOptCallback = this.mCameraOptCallback;
            recordSetting.previewWidth = this.previewWidth;
            recordSetting.previewHeight = this.previewHeight;
            recordSetting.isAutoFocus = this.isAutoFocus;
            recordSetting.isScaleEnable = this.isScaleEnable;

            return recordSetting;
        }
    }
}
