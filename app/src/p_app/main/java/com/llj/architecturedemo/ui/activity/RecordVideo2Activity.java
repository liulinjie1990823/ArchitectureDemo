package com.llj.architecturedemo.ui.activity;

import android.app.ProgressDialog;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.record.CameraHelper;
import com.llj.lib.record.RecordSetting;
import com.llj.lib.record.codec.FFmpegRecorderAdapter;
import com.llj.lib.record.data.FrameToRecord;
import com.llj.lib.record.data.RecordFragment;
import com.llj.lib.record.widget.LSurfaceView;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameFilter;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe: author llj date 2019/4/10
 */
@Route(path = CRouter.APP_RECORD_VIDEO2_ACTIVITY)
public class RecordVideo2Activity extends AppMvcBaseActivity {

  public static final String TAG = RecordVideo2Activity.class.getSimpleName();

  private static final String mSavePath =
      Environment.getExternalStorageDirectory() + "/" + "camera_test";//相机拍照/录像缓存路径


  private static final int PREFERRED_PREVIEW_WIDTH  = 640;
  private static final int PREFERRED_PREVIEW_HEIGHT = 480;

  // both in milliseconds
  private static final long MIN_VIDEO_LENGTH = 1 * 1000;
  private static final long MAX_VIDEO_LENGTH = 90 * 1000;

  @BindView(R.id.avatar_img)        ImageView    mAvatarImg;
  @BindView(R.id.surface_view)      LSurfaceView mSurfaceView;
  @BindView(R.id.flash_btn)         Button       mFlashBtn;
  @BindView(R.id.switch_btn)        Button       mSwitchBtn;
  @BindView(R.id.shot_btn)          Button       mShotBtn;
  @BindView(R.id.record_btn)        Button       mRecordBtn;
  @BindView(R.id.record_resume_btn) Button       mRecordResumeBtn;
  @BindView(R.id.record_pause_btn)  Button       mRecordPauseBtn;
  @BindView(R.id.record_stop_btn)   Button       mRecordStopBtn;


  private          int                                mCameraId;
  private          Camera                             mCamera;
  private          FFmpegFrameRecorder                mFrameRecorder;
  private          VideoRecordThread                  mVideoRecordThread;
  private          AudioRecordThread                  mAudioRecordThread;
  private volatile boolean                            mRecording = false;
  private          File                               mVideo;
  private          LinkedBlockingQueue<FrameToRecord> mFrameToRecordQueue;
  private          LinkedBlockingQueue<FrameToRecord> mRecycledFrameQueue;
  private          int                                mFrameToRecordCount;
  private          int                                mFrameRecordedCount;
  private          long                               mTotalProcessFrameTime;
  private          Stack<RecordFragment>              mRecordFragments;

  private int sampleAudioRateInHz = 44100;
  /* The sides of width and height are based on camera orientation.
  That is, the preview size is the size before it is rotated. */
  private int mPreviewWidth       = PREFERRED_PREVIEW_WIDTH;
  private int mPreviewHeight      = PREFERRED_PREVIEW_HEIGHT;
  // Output video size
  private int mVideoWidth         = 320;
  private int mVideoHeight        = 240;
  private int frameRate           = 30;
  private int frameDepth          = Frame.DEPTH_UBYTE;
  private int frameChannels       = 2;

  @Override
  public int layoutId() {
    return R.layout.activity_record_video2;
  }

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    setTranslucentStatusBar(getWindow(), true);

    // At most buffer 10 Frame
    mFrameToRecordQueue = new LinkedBlockingQueue<>(10);
    // At most recycle 2 Frame
    mRecycledFrameQueue = new LinkedBlockingQueue<>(2);
    mRecordFragments = new Stack<>();

    mFlashBtn.setOnClickListener(mOnClickListener);
    mSwitchBtn.setOnClickListener(mOnClickListener);
    mShotBtn.setOnClickListener(mOnClickListener);
    mRecordBtn.setOnClickListener(mOnClickListener);
    mRecordResumeBtn.setOnClickListener(mOnClickListener);
    mRecordPauseBtn.setOnClickListener(mOnClickListener);
    mRecordStopBtn.setOnClickListener(mOnClickListener);

    mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
        doAfterAllPermissionsGranted();
      }

      @Override
      public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

      }

      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {

      }
    });

  }

  private View.OnClickListener mOnClickListener = new DebouncingOnClickListener() {
    @Override
    public void doClick(View v) {
      switch (v.getId()) {
        case R.id.flash_btn:
          break;
        case R.id.switch_btn:
          break;
        case R.id.shot_btn:
          break;
        case R.id.record_btn:
          //                    Task.callInBackground(new Callable<Boolean>() {
          //                        @Override
          //                        public Boolean call() throws Exception {
          //                            mSurfaceView.startRecorder();
          //                            return true;
          //                        }
          //                    });
          break;
        case R.id.record_resume_btn:
          resumeRecording();
          break;
        case R.id.record_pause_btn:
          pauseRecording();
          break;
        case R.id.record_stop_btn:
          pauseRecording();

          stopRecording();
          stopRecorder();
          break;
      }
    }
  };

  /**
   * 初始化相机操作类 build()方法一旦调用，就会启用相机
   */
  private void initCamera() {
    RecordSetting recordSetting = new RecordSetting.Builder()
        .setLogEnable(true)
        .previewWidthAndHeight(640, 480)
        .saveWidthAndHeight(320, 240)
        .isAutoFocus(false)
        .directoryPath(mSavePath)
        .faceType(Camera.CameraInfo.CAMERA_FACING_BACK)
        .flashMode(Camera.Parameters.FLASH_MODE_AUTO)
        .isScaleEnable(true)
        .recordAdapter(new FFmpegRecorderAdapter())
        .build();
  }


  @Override
  public void initData() {
    initCamera();
  }


  private void doAfterAllPermissionsGranted() {
    acquireCamera();
    //        SurfaceTexture surfaceTexture = mPreview.getSurfaceTexture();
    final SurfaceHolder holder = mSurfaceView.getHolder();
    if (holder != null) {
      // SurfaceTexture already created
      startPreview(holder);
    }
    if (mFrameRecorder == null) {
      initRecorder();
      startRecorder();
    }
    startRecording();
  }

  private void startPreview(SurfaceHolder surfaceTexture) {
    if (mCamera == null) {
      return;
    }

    Camera.Parameters parameters = mCamera.getParameters();
    parameters.setPreviewSize(mPreviewWidth, mPreviewHeight);
    mCamera.setParameters(parameters);

    mCamera.setDisplayOrientation(90);

    // YCbCr_420_SP (NV21) format
    byte[] bufferByte = new byte[mPreviewWidth * mPreviewHeight * 3 / 2];
    mCamera.addCallbackBuffer(bufferByte);
    mCamera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {

      private long lastPreviewFrameTime;

      @Override
      public void onPreviewFrame(byte[] data, Camera camera) {
        long thisPreviewFrameTime = System.currentTimeMillis();
        if (lastPreviewFrameTime > 0) {
          Log.d(TAG,
              "Preview frame interval: " + (thisPreviewFrameTime - lastPreviewFrameTime) + "ms");
        }
        lastPreviewFrameTime = thisPreviewFrameTime;

        // get video data
        if (mRecording) {
          if (mAudioRecordThread == null || !mAudioRecordThread.isRunning()) {
            // wait for AudioRecord to init and start
            mRecordFragments.peek().setStartTimestamp(System.currentTimeMillis());
          } else {
            // pop the current record fragment when calculate total recorded time
            RecordFragment curFragment = mRecordFragments.pop();
            long recordedTime = calculateTotalRecordedTime(mRecordFragments);
            // push it back after calculation
            mRecordFragments.push(curFragment);
            long curRecordedTime = System.currentTimeMillis()
                - curFragment.getStartTimestamp() + recordedTime;
            // check if exceeds time limit
            if (curRecordedTime > MAX_VIDEO_LENGTH) {
              pauseRecording();
              new FinishRecordingTask().execute();
              return;
            }

            long timestamp = 1000 * curRecordedTime;
            Frame frame;
            FrameToRecord frameToRecord = mRecycledFrameQueue.poll();
            if (frameToRecord != null) {
              frame = frameToRecord.getFrame();
              frameToRecord.setTimestamp(timestamp);
            } else {
              frame = new Frame(mPreviewWidth, mPreviewHeight, frameDepth, frameChannels);
              frameToRecord = new FrameToRecord(timestamp, frame);
            }
            ((ByteBuffer) frame.image[0].position(0)).put(data);

            if (mFrameToRecordQueue.offer(frameToRecord)) {
              mFrameToRecordCount++;
            }
          }
        }
        mCamera.addCallbackBuffer(data);
      }
    });

    try {
      //            mCamera.setPreviewTexture(surfaceTexture);
      mCamera.setPreviewDisplay(surfaceTexture);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    mCamera.startPreview();
  }

  private void stopPreview() {
    if (mCamera != null) {
      mCamera.stopPreview();
      mCamera.setPreviewCallbackWithBuffer(null);
    }
  }

  private void acquireCamera() {
    try {
      mCamera = Camera.open(mCameraId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void releaseCamera() {
    if (mCamera != null) {
      mCamera.release();        // release the camera for other applications
      mCamera = null;
    }
  }

  private void initRecorder() {
    Log.i(TAG, "init mFrameRecorder");
    String mSavePath = Environment.getExternalStorageDirectory() + "/" + "camera_test";//相机拍照/录像缓存路径
    String recordedTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String recordFilePath = mSavePath + "/" + recordedTime + ".mp4";
    mVideo = CameraHelper.getOutputMediaFile(recordedTime, CameraHelper.MEDIA_TYPE_VIDEO);
    mVideo = new File(recordFilePath);

    Log.i(TAG, "Output Video: " + mVideo);

    mFrameRecorder = new FFmpegFrameRecorder(mVideo, mVideoWidth, mVideoHeight, 1);
    mFrameRecorder.setFormat("mp4");
    mFrameRecorder.setSampleRate(sampleAudioRateInHz);
    mFrameRecorder.setFrameRate(frameRate);

    // Use H264
    mFrameRecorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
    // See: https://trac.ffmpeg.org/wiki/Encode/H.264#crf
    /*
     * The range of the quantizer scale is 0-51: where 0 is lossless, 23 is default, and 51 is worst possible. A lower value is a higher quality and a subjectively sane range is 18-28. Consider 18 to be visually lossless or nearly so: it should look the same or nearly the same as the input but it isn't technically lossless.
     * The range is exponential, so increasing the CRF value +6 is roughly half the bitrate while -6 is roughly twice the bitrate. General usage is to choose the highest CRF value that still provides an acceptable quality. If the output looks good, then try a higher value and if it looks bad then choose a lower value.
     */
    mFrameRecorder.setVideoOption("crf", "28");
    mFrameRecorder.setVideoOption("preset", "superfast");
    mFrameRecorder.setVideoOption("tune", "zerolatency");

    Log.i(TAG, "mFrameRecorder initialize success");
  }

  private void releaseRecorder(boolean deleteFile) {
    if (mFrameRecorder != null) {
      try {
        mFrameRecorder.release();
      } catch (FFmpegFrameRecorder.Exception e) {
        e.printStackTrace();
      }
      mFrameRecorder = null;

      if (deleteFile) {
        mVideo.delete();
      }
    }
  }

  private void startRecorder() {
    try {
      mFrameRecorder.start();
    } catch (FFmpegFrameRecorder.Exception e) {
      e.printStackTrace();
    }
  }

  private void stopRecorder() {
    if (mFrameRecorder != null) {
      try {
        mFrameRecorder.stop();
      } catch (FFmpegFrameRecorder.Exception e) {
        e.printStackTrace();
      }
    }

    mRecordFragments.clear();

  }

  private void startRecording() {
    mAudioRecordThread = new AudioRecordThread(sampleAudioRateInHz);
    mAudioRecordThread.start();
    mVideoRecordThread = new VideoRecordThread(frameRate, 0);
    mVideoRecordThread.start();
  }

  private void stopRecording() {
    if (mAudioRecordThread != null) {
      if (mAudioRecordThread.isRunning()) {
        mAudioRecordThread.stopRunning();
      }
    }

    if (mVideoRecordThread != null) {
      if (mVideoRecordThread.isRunning()) {
        mVideoRecordThread.stopRunning();
      }
    }

    try {
      if (mAudioRecordThread != null) {
        mAudioRecordThread.join();
      }
      if (mVideoRecordThread != null) {
        mVideoRecordThread.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    mAudioRecordThread = null;
    mVideoRecordThread = null;

    mFrameToRecordQueue.clear();
    mRecycledFrameQueue.clear();
  }

  private void resumeRecording() {
    if (!mRecording) {
      RecordFragment recordFragment = new RecordFragment();
      recordFragment.setStartTimestamp(System.currentTimeMillis());
      mRecordFragments.push(recordFragment);
      mRecording = true;
    }
  }

  private void pauseRecording() {
    if (mRecording) {
      mRecordFragments.peek().setEndTimestamp(System.currentTimeMillis());
      mRecording = false;
    }
  }

  private long calculateTotalRecordedTime(Stack<RecordFragment> recordFragments) {
    long recordedTime = 0;
    for (RecordFragment recordFragment : recordFragments) {
      recordedTime += recordFragment.getDuration();
    }
    return recordedTime;
  }

  class RunningThread extends Thread {

    boolean isRunning;

    public boolean isRunning() {
      return isRunning;
    }

    public void stopRunning() {
      this.isRunning = false;
    }
  }

  class AudioRecordThread extends RunningThread {

    private AudioRecord mAudioRecord;
    private ShortBuffer audioData;

    public AudioRecordThread(int sampleRateInHz) {
      int bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz,
          AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
      mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz,
          AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);

      audioData = ShortBuffer.allocate(bufferSize);
    }

    @Override
    public void run() {
      android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

      Log.d(TAG, "mAudioRecord startRecording");
      mAudioRecord.startRecording();

      isRunning = true;
      /* ffmpeg_audio encoding loop */
      while (isRunning) {
        if (mRecording && mFrameRecorder != null) {
          int bufferReadResult = mAudioRecord.read(audioData.array(), 0, audioData.capacity());
          audioData.limit(bufferReadResult);
          if (bufferReadResult > 0) {
            Log.v(TAG, "bufferReadResult: " + bufferReadResult);
            try {
              mFrameRecorder.recordSamples(audioData);
            } catch (FFmpegFrameRecorder.Exception e) {
              Log.v(TAG, e.getMessage());
              e.printStackTrace();
            }
          }
        }
      }
      Log.d(TAG, "mAudioRecord stopRecording");
      mAudioRecord.stop();
      mAudioRecord.release();
      mAudioRecord = null;
      Log.d(TAG, "mAudioRecord released");
    }
  }

  class VideoRecordThread extends RunningThread {

    private int mFrameRate;
    private int mRotation;

    public VideoRecordThread(int frameRate, int rotation) {
      this.mFrameRate = frameRate;
      this.mRotation = rotation;
    }

    @Override
    public void run() {
      Log.d(TAG, "mVideoRecord startRecording");
      int previewWidth = mPreviewWidth;
      int previewHeight = mPreviewHeight;

      List<String> filters = new ArrayList<>();
      // Transpose
      String transpose = null;
      String hflip = null;
      String vflip = null;
      String crop = null;
      String scale = null;
      int cropWidth;
      int cropHeight;
      Camera.CameraInfo info = new Camera.CameraInfo();
      Camera.getCameraInfo(mCameraId, info);
      switch (mRotation) {
        case Surface.ROTATION_0:
          switch (info.orientation) {
            case 270:
              if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                transpose = "transpose=clock_flip"; // Same as preview display
              } else {
                transpose = "transpose=cclock"; // Mirrored horizontally as preview display
              }
              break;
            case 90:
              if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                transpose = "transpose=cclock_flip"; // Same as preview display
              } else {
                transpose = "transpose=clock"; // Mirrored horizontally as preview display
              }
              break;
          }
          cropWidth = previewHeight;
          cropHeight = cropWidth * mVideoHeight / mVideoWidth;
          crop = String
              .format("crop=%d:%d:%d:%d", cropWidth, cropHeight, (previewHeight - cropWidth) / 2,
                  (previewWidth - cropHeight) / 2);
          // swap width and height
          scale = String.format("scale=%d:%d", mVideoHeight, mVideoWidth);
          break;
        case Surface.ROTATION_90:
        case Surface.ROTATION_270:
          switch (mRotation) {
            case Surface.ROTATION_90:
              // landscape-left
              switch (info.orientation) {
                case 270:
                  if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    hflip = "hflip";
                  }
                  break;
              }
              break;
            case Surface.ROTATION_270:
              // landscape-right
              switch (info.orientation) {
                case 90:
                  if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    hflip = "hflip";
                    vflip = "vflip";
                  }
                  break;
                case 270:
                  if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    vflip = "vflip";
                  }
                  break;
              }
              break;
          }
          cropHeight = previewHeight;
          cropWidth = cropHeight * mVideoWidth / mVideoHeight;
          crop = String
              .format("crop=%d:%d:%d:%d", cropWidth, cropHeight, (previewWidth - cropWidth) / 2,
                  (previewHeight - cropHeight) / 2);
          scale = String.format("scale=%d:%d", mVideoWidth, mVideoHeight);
          break;
        case Surface.ROTATION_180:
          break;
      }
      // transpose
      if (transpose != null) {
        filters.add(transpose);
      }
      // horizontal flip
      if (hflip != null) {
        filters.add(hflip);
      }
      // vertical flip
      if (vflip != null) {
        filters.add(vflip);
      }
      // crop
      if (crop != null) {
        filters.add(crop);
      }
      // scale (to designated size)
      if (scale != null) {
        filters.add(scale);
      }

      FFmpegFrameFilter frameFilter = new FFmpegFrameFilter(TextUtils.join(",", filters),
          previewWidth, previewHeight);
      frameFilter.setPixelFormat(avutil.AV_PIX_FMT_NV21);
      frameFilter.setFrameRate(mFrameRate);
      try {
        frameFilter.start();
      } catch (FrameFilter.Exception e) {
        e.printStackTrace();
      }

      isRunning = true;
      FrameToRecord recordedFrame;

      while (isRunning || !mFrameToRecordQueue.isEmpty()) {
        try {
          //获取一帧
          recordedFrame = mFrameToRecordQueue.take();
        } catch (InterruptedException ie) {
          ie.printStackTrace();
          try {
            frameFilter.stop();
          } catch (FrameFilter.Exception e) {
            e.printStackTrace();
          }
          break;
        }

        if (mFrameRecorder != null) {
          long timestamp = recordedFrame.getTimestamp();
          if (timestamp > mFrameRecorder.getTimestamp()) {
            mFrameRecorder.setTimestamp(timestamp);
          }
          //开始处理时间
          long startTime = System.currentTimeMillis();

          Frame filteredFrame = null;
          try {
            frameFilter.push(recordedFrame.getFrame());
            filteredFrame = frameFilter.pull();
          } catch (FrameFilter.Exception e) {
            e.printStackTrace();
          }
          try {
            mFrameRecorder.record(filteredFrame);
          } catch (FFmpegFrameRecorder.Exception e) {
            e.printStackTrace();
          }

          //结束处理时间
          long endTime = System.currentTimeMillis();
          long processTime = endTime - startTime;
          mTotalProcessFrameTime += processTime;

          Log.d(TAG, "----------------------------------------------");

          Log.d(TAG, "This frame process time: " + processTime + "ms");
          long totalAvg = mTotalProcessFrameTime / ++mFrameRecordedCount;
          Log.d(TAG, "Avg frame process time: " + totalAvg + "ms");
        }
        Log.d(TAG, mFrameRecordedCount + " / " + mFrameToRecordCount);
        mRecycledFrameQueue.offer(recordedFrame);
      }
    }

    public void stopRunning() {
      super.stopRunning();
      if (getState() == Thread.State.WAITING) {
        interrupt();
      }
    }
  }

  abstract class ProgressDialogTask<Params, Progress, Result> extends
      AsyncTask<Params, Progress, Result> {

    private int            promptRes;
    private ProgressDialog mProgressDialog;

    public ProgressDialogTask(int promptRes) {
      this.promptRes = promptRes;
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      mProgressDialog = ProgressDialog.show(RecordVideo2Activity.this,
          null, getString(promptRes), true);
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
      super.onProgressUpdate(values);
      //            mProgressDialog.bindProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Result result) {
      super.onPostExecute(result);
      mProgressDialog.dismiss();
    }
  }

  class FinishRecordingTask extends ProgressDialogTask<Void, Integer, Void> {

    public FinishRecordingTask() {
      super(R.string.about_china_hunbohui);
    }

    @Override
    protected Void doInBackground(Void... params) {
      stopRecording();
      stopRecorder();
      releaseRecorder(false);
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);

    }
  }
}
