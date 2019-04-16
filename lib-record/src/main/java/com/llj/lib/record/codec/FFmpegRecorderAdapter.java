package com.llj.lib.record.codec;

import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.SurfaceHolder;

import com.llj.lib.record.CameraHelper;
import com.llj.lib.record.RecordSetting;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import java.io.File;
import java.nio.ShortBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/12
 */
public class FFmpegRecorderAdapter implements IRecordAdapter {
    public static final String TAG = FFmpegRecorderAdapter.class.getSimpleName();

    private FFmpegFrameRecorder mFrameRecorder;

    private File mVideo;

    @Override
    public void initRecorder(RecordSetting recordSetting) {

        String recordedTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mVideo = CameraHelper.getOutputMediaFile(recordedTime, CameraHelper.MEDIA_TYPE_VIDEO);

        mFrameRecorder = new FFmpegFrameRecorder(mVideo, recordSetting.getSaveWidth(), recordSetting.getSaveHeight());
        mFrameRecorder.setFormat("mp4");

        //AUDIO
        mFrameRecorder.setAudioChannels(1);
        mFrameRecorder.setSampleRate(recordSetting.getAudioSamplingRate());

        //VIDEO
        mFrameRecorder.setFrameRate(recordSetting.getVideoFrameRate());
        mFrameRecorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        mFrameRecorder.setVideoOption("crf", "28");
        mFrameRecorder.setVideoOption("preset", "superfast");
        mFrameRecorder.setVideoOption("tune", "zerolatency");

    }

    @Override
    public boolean startRecorder(Camera camera, SurfaceHolder surfaceHolder, RecordSetting recordSetting) {
        if (mFrameRecorder == null) {
            return false;
        }
        try {
            mFrameRecorder.start();
        } catch (FFmpegFrameRecorder.Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void stopRecorder() {
        if (mFrameRecorder == null) {
            return;
        }
        try {
            mFrameRecorder.stop();
        } catch (FFmpegFrameRecorder.Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseRecorder(Camera camera) {

    }

    @Override
    public boolean isRecording() {
        return false;
    }

    @Override
    public String getRecordFilePath() {
        return null;
    }


    class AudioRecordThread extends RunningThread {
        private AudioRecord mAudioRecord;
        private ShortBuffer mAudioData;

        public AudioRecordThread(int sampleRateInHz) {
            int bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRateInHz, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);

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

//    class VideoRecordThread extends RunningThread {
//        private int mFrameRate;
//
//        public VideoRecordThread(int frameRate) {
//            this.mFrameRate=frameRate;
//        }
//
//        @Override
//        public void run() {
//            int previewWidth = mPreviewWidth;
//            int previewHeight = mPreviewHeight;
//
//            List<String> filters = new ArrayList<>();
//            // Transpose
//            String transpose = null;
//            String hflip = null;
//            String vflip = null;
//            String crop = null;
//            String scale = null;
//            int cropWidth;
//            int cropHeight;
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            Camera.getCameraInfo(mCameraId, info);
//            int rotation = getWindowManager().getDefaultDisplay().getRotation();
//            switch (rotation) {
//                case Surface.ROTATION_0:
//                    switch (info.orientation) {
//                        case 270:
//                            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                                transpose = "transpose=clock_flip"; // Same as preview display
//                            } else {
//                                transpose = "transpose=cclock"; // Mirrored horizontally as preview display
//                            }
//                            break;
//                        case 90:
//                            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                                transpose = "transpose=cclock_flip"; // Same as preview display
//                            } else {
//                                transpose = "transpose=clock"; // Mirrored horizontally as preview display
//                            }
//                            break;
//                    }
//                    cropWidth = previewHeight;
//                    cropHeight = cropWidth * videoHeight / videoWidth;
//                    crop = String.format("crop=%d:%d:%d:%d",
//                            cropWidth, cropHeight,
//                            (previewHeight - cropWidth) / 2, (previewWidth - cropHeight) / 2);
//                    // swap width and height
//                    scale = String.format("scale=%d:%d", videoHeight, videoWidth);
//                    break;
//                case Surface.ROTATION_90:
//                case Surface.ROTATION_270:
//                    switch (rotation) {
//                        case Surface.ROTATION_90:
//                            // landscape-left
//                            switch (info.orientation) {
//                                case 270:
//                                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                                        hflip = "hflip";
//                                    }
//                                    break;
//                            }
//                            break;
//                        case Surface.ROTATION_270:
//                            // landscape-right
//                            switch (info.orientation) {
//                                case 90:
//                                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                                        hflip = "hflip";
//                                        vflip = "vflip";
//                                    }
//                                    break;
//                                case 270:
//                                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                                        vflip = "vflip";
//                                    }
//                                    break;
//                            }
//                            break;
//                    }
//                    cropHeight = previewHeight;
//                    cropWidth = cropHeight * videoWidth / videoHeight;
//                    crop = String.format("crop=%d:%d:%d:%d",
//                            cropWidth, cropHeight,
//                            (previewWidth - cropWidth) / 2, (previewHeight - cropHeight) / 2);
//                    scale = String.format("scale=%d:%d", videoWidth, videoHeight);
//                    break;
//                case Surface.ROTATION_180:
//                    break;
//            }
//            // transpose
//            if (transpose != null) {
//                filters.add(transpose);
//            }
//            // horizontal flip
//            if (hflip != null) {
//                filters.add(hflip);
//            }
//            // vertical flip
//            if (vflip != null) {
//                filters.add(vflip);
//            }
//            // crop
//            if (crop != null) {
//                filters.add(crop);
//            }
//            // scale (to designated size)
//            if (scale != null) {
//                filters.add(scale);
//            }
//
//            FFmpegFrameFilter frameFilter = new FFmpegFrameFilter(TextUtils.join(",", filters), previewWidth, previewHeight);
//            frameFilter.setPixelFormat(avutil.AV_PIX_FMT_NV21);
//            frameFilter.setFrameRate(mFrameRate);
//            try {
//                frameFilter.start();
//            } catch (FrameFilter.Exception e) {
//                e.printStackTrace();
//            }
//
//            isRunning = true;
//            FrameToRecord recordedFrame;
//
//            while (isRunning || !mFrameToRecordQueue.isEmpty()) {
//                try {
//                    recordedFrame = mFrameToRecordQueue.take();
//                } catch (InterruptedException ie) {
//                    ie.printStackTrace();
//                    try {
//                        frameFilter.stop();
//                    } catch (FrameFilter.Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//
//                if (mFrameRecorder != null) {
//                    long timestamp = recordedFrame.getTimestamp();
//                    if (timestamp > mFrameRecorder.getTimestamp()) {
//                        mFrameRecorder.setTimestamp(timestamp);
//                    }
//                    long startTime = System.currentTimeMillis();
////                    Frame filteredFrame = recordedFrame.getFrame();
//                    Frame filteredFrame = null;
//                    try {
//                        frameFilter.push(recordedFrame.getFrame());
//                        filteredFrame = frameFilter.pull();
//                    } catch (FrameFilter.Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        mFrameRecorder.record(filteredFrame);
//                    } catch (FFmpegFrameRecorder.Exception e) {
//                        e.printStackTrace();
//                    }
//                    long endTime = System.currentTimeMillis();
//                    long processTime = endTime - startTime;
//                    mTotalProcessFrameTime += processTime;
//                    Log.d(TAG, "This frame process time: " + processTime + "ms");
//                    long totalAvg = mTotalProcessFrameTime / ++mFrameRecordedCount;
//                    Log.d(TAG, "Avg frame process time: " + totalAvg + "ms");
//                }
//                Log.d(TAG, mFrameRecordedCount + " / " + mFrameToRecordCount);
//                mRecycledFrameQueue.offer(recordedFrame);
//            }
//        }
//
//        public void stopRunning() {
//            super.stopRunning();
//            if (getState() == WAITING) {
//                interrupt();
//            }
//        }
//    }

    class RunningThread extends Thread {
        boolean isRunning;

        public boolean isRunning() {
            return isRunning;
        }

        public void stopRunning() {
            this.isRunning = false;
        }
    }
}
