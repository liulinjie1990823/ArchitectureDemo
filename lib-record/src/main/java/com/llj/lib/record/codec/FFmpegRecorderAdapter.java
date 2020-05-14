package com.llj.lib.record.codec;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.llj.lib.record.RecordSetting;
import com.llj.lib.record.data.FrameToRecord;
import com.llj.lib.record.data.RecordFragment;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameFilter;

/**
 * ArchitectureDemo. describe: author llj date 2019/4/12
 */
public class FFmpegRecorderAdapter implements IRecordAdapter {

  public static final String TAG = FFmpegRecorderAdapter.class.getSimpleName();

  private static final long MAX_VIDEO_LENGTH = 90 * 1000;

  private volatile boolean mRecording = false;

  private FFmpegFrameRecorder mFrameRecorder;

  private File mVideo;
  private int  mFrameToRecordCount;//onPreviewFrame中获取的帧
  private int  mFrameRecordedCount;//VideoRecordThread中已经处理的帧
  private long mTotalProcessFrameTime;//总的处理时间

  private LinkedBlockingQueue<FrameToRecord> mFrameToRecordQueue;
  private LinkedBlockingQueue<FrameToRecord> mRecycledFrameQueue;//帧记录的缓存
  private Stack<RecordFragment>              mRecordFragments;//片段录制的时间记录

  private int mCameraId;
  private int mPreviewWidth;
  private int mPreviewHeight;
  private int mVideoWidth;
  private int mVideoHeight;

  private int mFrameDepth    = Frame.DEPTH_UBYTE;
  private int mFrameChannels = 2;


  private VideoRecordThread mVideoRecordThread;
  private AudioRecordThread mAudioRecordThread;


  private int mAudioSamplingRate;
  private int mVideoFrameRate;
  private int mDisplayRotation;


  public FFmpegRecorderAdapter() {
    // At most buffer 10 Frame
    mFrameToRecordQueue = new LinkedBlockingQueue<>(10);
    // At most recycle 2 Frame
    mRecycledFrameQueue = new LinkedBlockingQueue<>(2);
    mRecordFragments = new Stack<>();
  }

  @Override
  public void initCamera(Camera camera, int displayRotation, RecordSetting recordSetting) {

    mAudioSamplingRate = recordSetting.getAudioSamplingRate();
    mVideoFrameRate = recordSetting.getVideoFrameRate();
    mDisplayRotation = displayRotation;
    mCameraId = recordSetting.getFaceType();
    mPreviewWidth = recordSetting.getPreviewWidth();
    mPreviewHeight = recordSetting.getPreviewHeight();
    mVideoWidth = recordSetting.getSaveWidth();
    mVideoHeight = recordSetting.getSaveHeight();

    // YCbCr_420_SP (NV21) format
//        byte[] bufferByte = new byte[recordSetting.getPreviewWidth() * recordSetting.getPreviewHeight() * 3 / 2];
    byte[] bufferByte = new byte[
        recordSetting.getPreviewWidth() * recordSetting.getPreviewHeight() * ImageFormat
            .getBitsPerPixel(camera.getParameters().getPreviewFormat()) / 8];

    camera.addCallbackBuffer(bufferByte);
    camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {

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
        if (isRecording()) {
          if (mAudioRecordThread == null || !mAudioRecordThread.isRunning()) {
            // wait for AudioRecord to init and start
            mRecordFragments.peek().setStartTimestamp(System.currentTimeMillis());
          } else {
            // pop the current record fragment when calculate total recorded time
            RecordFragment curFragment = mRecordFragments.pop();
            long recordedTime = calculateTotalRecordedTime(mRecordFragments);
            // push it back after calculation
            mRecordFragments.push(curFragment);
            long curRecordedTime =
                System.currentTimeMillis() - curFragment.getStartTimestamp() + recordedTime;
            // check if exceeds time limit
            if (curRecordedTime > MAX_VIDEO_LENGTH) {
              pauseRecording();
              new FinishRecordingTask().execute();
              return;
            }

            long timestamp = 1000 * curRecordedTime;
            Frame frame;
            //
            FrameToRecord frameToRecord = mRecycledFrameQueue.poll();
            if (frameToRecord != null) {
              frame = frameToRecord.getFrame();
              frameToRecord.setTimestamp(timestamp);
            } else {
              frame = new Frame(recordSetting.getPreviewHeight(), recordSetting.getPreviewWidth(),
                  mFrameDepth, mFrameChannels);
              frameToRecord = new FrameToRecord(timestamp, frame);
            }
            ((ByteBuffer) frame.image[0].position(0)).put(data);

            if (mFrameToRecordQueue.offer(frameToRecord)) {
              mFrameToRecordCount++;
            }
          }
        }
        camera.addCallbackBuffer(data);
      }
    });

  }

  @Override
  public void initRecorder(Camera camera, SurfaceHolder surfaceHolder,
      RecordSetting recordSetting) {
    if (mFrameRecorder != null) {
      return;
    }

    String recordFilePath =
        recordSetting.getDirectoryPath() + "/" + System.currentTimeMillis() + ".mp4";
    mVideo = new File(recordFilePath);

    mFrameRecorder = new FFmpegFrameRecorder(mVideo, recordSetting.getSaveWidth(),
        recordSetting.getSaveHeight());
    mFrameRecorder.setFormat(recordSetting.getFormat());

    //AUDIO
    mFrameRecorder.setAudioChannels(recordSetting.getAudioChannels());
    mFrameRecorder.setSampleRate(recordSetting.getAudioSamplingRate());
    mFrameRecorder.setAudioBitrate(recordSetting.getAudioBitrate());

    //VIDEO
    mFrameRecorder.setFrameRate(recordSetting.getVideoFrameRate());
    mFrameRecorder.setVideoBitrate(recordSetting.getVideoBitrate());
    mFrameRecorder.setVideoCodec(recordSetting.getVideoCodec());
    mFrameRecorder.setVideoOption("crf", "28");
    mFrameRecorder.setVideoOption("preset", "superfast");
    mFrameRecorder.setVideoOption("tune", "zerolatency");

  }


  private long calculateTotalRecordedTime(Stack<RecordFragment> recordFragments) {
    long recordedTime = 0;
    for (RecordFragment recordFragment : recordFragments) {
      recordedTime += recordFragment.getDuration();
    }
    return recordedTime;
  }

  @Override
  public boolean startRecorder() {
    if (mFrameRecorder == null) {
      return false;
    }
    try {
      //开启录制
      mFrameRecorder.start();
      //开启处理线程
      startRecording();

    } catch (FFmpegFrameRecorder.Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  private void startRecording() {
    mAudioRecordThread = new AudioRecordThread(mAudioSamplingRate);
    mAudioRecordThread.start();
    mVideoRecordThread = new VideoRecordThread(mVideoFrameRate, mDisplayRotation);
    mVideoRecordThread.start();
  }

  //开启一次片段录制
  @Override
  public boolean resumeRecording() {
    if (!mRecording) {
      RecordFragment recordFragment = new RecordFragment();
      recordFragment.setStartTimestamp(System.currentTimeMillis());
      mRecordFragments.push(recordFragment);
      mRecording = true;
    }
    return true;
  }

  //结束一次片段录制。记录结束时间
  @Override
  public boolean pauseRecording() {
    if (mRecording) {
      mRecordFragments.peek().setEndTimestamp(System.currentTimeMillis());
      mRecording = false;
    }
    return true;
  }

  @Override
  public boolean stopRecorder() {
    if (mFrameRecorder == null) {
      return false;
    }
    //停止录制线程
    stopRecording();

    try {
      mFrameRecorder.stop();
    } catch (FFmpegFrameRecorder.Exception e) {
      e.printStackTrace();
    }
    mRecordFragments.clear();

    return true;
  }

  private boolean stopRecording() {
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
    return true;
  }


  @Override
  public boolean releaseRecorder(Camera camera, boolean deleteFile) {
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
    return true;
  }

  @Override
  public boolean isRecording() {
    return mRecording;
  }

  @Override
  public String getRecordFilePath() {
    return null;
  }


  class AudioRecordThread extends RunningThread {

    private AudioRecord mAudioRecord;
    private ShortBuffer mAudioData;

    public AudioRecordThread(int sampleRateInHz) {
      int bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, AudioFormat.CHANNEL_IN_MONO,
          AudioFormat.ENCODING_PCM_16BIT);
      mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz,
          AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);

      mAudioData = ShortBuffer.allocate(bufferSize);
    }

    @Override
    public void run() {
      android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

      Log.d(TAG, "mAudioRecord startRecording");
      mAudioRecord.startRecording();

      isRunning = true;
      /* ffmpeg_audio encoding loop */
      while (isRunning) {
        if (isRecording() && mFrameRecorder != null) {
          int bufferReadResult = mAudioRecord.read(mAudioData.array(), 0, mAudioData.capacity());
          mAudioData.limit(bufferReadResult);
          if (bufferReadResult > 0) {
            Log.v(TAG, "bufferReadResult: " + bufferReadResult);
            try {
              mFrameRecorder.recordSamples(mAudioData);
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
//                filters.add("transpose=1");
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
          previewHeight, previewWidth);
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

  class RunningThread extends Thread {

    boolean isRunning;

    public boolean isRunning() {
      return isRunning;
    }

    public void stopRunning() {
      this.isRunning = false;
    }
  }

  class FinishRecordingTask extends AsyncTask<Void, Integer, Void> {

    public FinishRecordingTask() {
    }

    @Override
    protected Void doInBackground(Void... params) {
      stopRecorder();
      releaseRecorder(null, false);
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
    }
  }
}
