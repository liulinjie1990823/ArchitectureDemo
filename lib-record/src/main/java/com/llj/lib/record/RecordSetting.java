package com.llj.lib.record;

import android.graphics.Bitmap;
import android.hardware.Camera;
import com.llj.lib.record.codec.IRecordAdapter;
import org.bytedeco.ffmpeg.global.avcodec;


/**
 * ArchitectureDemo. describe: author llj date 2019/4/10
 */
public class RecordSetting {

  private boolean logEnable;

  private String format;
  private int    saveWidth;//保存宽度
  private int    saveHeight;//保存高度

  private int audioSamplingRate;//设置录制的音频采样率
  private int audioChannels;//音频通道
  private int audioBitrate;//音频码率
  private int audioCodec;

  private int videoFrameRate;//设置要捕获的视频的帧速率
  private int videoBitrate;//视频码率
  private int videoCodec;

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

  public void setFaceType(int faceType) {
    this.faceType = faceType;
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

  public int getAudioChannels() {
    return audioChannels;
  }

  public String getFormat() {
    return format;
  }

  public int getAudioBitrate() {
    return audioBitrate;
  }

  public int getVideoBitrate() {
    return videoBitrate;
  }

  public int getAudioCodec() {
    return audioCodec;
  }

  public int getVideoCodec() {
    return videoCodec;
  }

  public static final class Builder {

    private boolean logEnable;

    private String directoryPath;

    private int previewWidth;
    private int previewHeight;

    //camera相关配置
    private int     faceType          = Camera.CameraInfo.CAMERA_FACING_BACK;
    private String  flashMode         = Camera.Parameters.FLASH_MODE_OFF;//闪光灯模式
    private boolean isAutoFocus       = false;//是否自动聚焦
    private long    autoFocusDuration = 3000;

    private Camera.PreviewCallback mPreviewCallback;
    private CameraOptCallback      mCameraOptCallback;

    private boolean isScaleEnable;//是否支持缩放


    private IRecordAdapter mRecordAdapter;
    private String         format            = "mp4";
    //视频保存宽高
    private int            saveWidth         = 1080;
    private int            saveHeight        = 1920;
    //音频相关配置
    private int            audioSamplingRate = 44100;//设置录制的音频采样率
    private int            audioChannels     = 1;//音频通道
    private int            audioBitrate      = 64000;//音频码率
    private int            audioCodec;
    //视频相关配置
    private int            videoFrameRate    = 30;//设置要捕获的视频的帧速率
    private int            videoBitrate      = 400000;//视频码率
    private int            videoCodec        = avcodec.AV_CODEC_ID_H264;


    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;


    public Builder() {
    }

    public Builder setLogEnable(boolean logEnable) {
      this.logEnable = logEnable;
      return this;
    }

    public Builder setFormat(String format) {
      this.format = format;
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

    public Builder audioChannels(int audioChannels) {
      this.audioChannels = audioChannels;
      return this;
    }

    public Builder audioBitrate(int audioBitrate) {
      this.audioBitrate = audioBitrate;
      return this;
    }

    public Builder audioCodec(int audioCodec) {
      this.audioCodec = audioCodec;
      return this;
    }

    public Builder videoFrameRate(int videoFrameRate) {
      this.videoFrameRate = videoFrameRate;
      return this;
    }

    public Builder videoBitrate(int videoBitrate) {
      this.videoBitrate = videoBitrate;
      return this;
    }

    public Builder videoCodec(int videoCodec) {
      this.videoCodec = videoCodec;
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

    public Builder compressFormat(Bitmap.CompressFormat CompressFormat) {
      this.mCompressFormat = CompressFormat;
      return this;
    }

    public Builder previewCallback(Camera.PreviewCallback PreviewCallback) {
      this.mPreviewCallback = PreviewCallback;
      return this;
    }

    public Builder cameraOptCallback(CameraOptCallback CameraOptCallback) {
      this.mCameraOptCallback = CameraOptCallback;
      return this;
    }

    public Builder recordAdapter(IRecordAdapter RecordAdapter) {
      this.mRecordAdapter = RecordAdapter;
      return this;
    }

    public RecordSetting build() {
      RecordSetting recordSetting = new RecordSetting();
      recordSetting.logEnable = this.logEnable;

      recordSetting.directoryPath = this.directoryPath;

      recordSetting.mRecordAdapter = this.mRecordAdapter;
      recordSetting.format = this.format;
      recordSetting.saveWidth = this.saveWidth;
      recordSetting.saveHeight = this.saveHeight;
      recordSetting.audioSamplingRate = this.audioSamplingRate;
      recordSetting.audioChannels = this.audioChannels;
      recordSetting.audioBitrate = this.audioBitrate;
      recordSetting.audioCodec = this.audioCodec;
      recordSetting.videoFrameRate = this.videoFrameRate;
      recordSetting.videoBitrate = this.videoBitrate;
      recordSetting.videoCodec = this.videoCodec;

      recordSetting.isAutoFocus = this.isAutoFocus;
      recordSetting.autoFocusDuration = this.autoFocusDuration;

      recordSetting.mPreviewCallback = this.mPreviewCallback;
      recordSetting.faceType = this.faceType;
      recordSetting.flashMode = this.flashMode;

      recordSetting.mCompressFormat = this.mCompressFormat;
      recordSetting.mCameraOptCallback = this.mCameraOptCallback;
      recordSetting.previewWidth = this.previewWidth;
      recordSetting.previewHeight = this.previewHeight;
      recordSetting.isScaleEnable = this.isScaleEnable;

      return recordSetting;
    }
  }
}
