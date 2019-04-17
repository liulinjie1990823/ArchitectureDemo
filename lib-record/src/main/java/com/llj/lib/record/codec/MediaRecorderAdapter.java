package com.llj.lib.record.codec;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;

import com.llj.lib.record.CameraHelper;
import com.llj.lib.record.CameraUtil;
import com.llj.lib.record.RecordSetting;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/10
 */
public class MediaRecorderAdapter implements IRecordAdapter {

    private MediaRecorder mMediaRecorder;
    private boolean       mIsRecording;
    private String        mRecordFilePath;

    private Camera mCamera;

    @Override
    public void initRecorder(Camera camera, SurfaceHolder surfaceHolder, int displayRotation, RecordSetting recordSetting) {
        mCamera = camera;
        //Step 1 :Unlock and set camera to MediaRecorder
        mMediaRecorder = new MediaRecorder();
        camera.unlock();//必须解锁
        mMediaRecorder.setCamera(camera);

        //Step 2 :Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        //Step 3 :Set a CamcorderProfile(requires API Level 8 or higher)
        //PS:此处需要注意~~~profile中的videoFrameWidth和videoFrameHeight如果超出预览视图的宽高，就会录制失败!
        //PS:此处需要注意~~~profile中的videoFrameWidth和videoFrameHeight如果超出预览视图的宽高，就会录制失败!
        //PS:此处需要注意~~~profile中的videoFrameWidth和videoFrameHeight如果超出预览视图的宽高，就会录制失败!
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        int targetWidth = recordSetting.getPreviewHeight();
        int targetHeight = recordSetting.getPreviewWidth();

        Camera.Size bestSize = CameraHelper.getBestSize(targetWidth, targetHeight, camera.getParameters().getSupportedPreviewSizes());
        profile.videoFrameWidth = bestSize.width;
        profile.videoFrameHeight = bestSize.height;

        mMediaRecorder.setProfile(profile);//此质量直接影响录制文件的总大小
        mMediaRecorder.setOrientationHint(recordSetting.getFaceType() == Camera.CameraInfo.CAMERA_FACING_FRONT ? 180 : 0);//反录制镜像！！！

        //Step 4 :Set output file
        String suffix = "";
        switch (profile.fileFormat) {
            case MediaRecorder.OutputFormat.THREE_GPP:
                suffix = ".3gp";
                break;
            case MediaRecorder.OutputFormat.MPEG_4:
                suffix = ".mp4";
                break;
        }
        mRecordFilePath = recordSetting.getDirectoryPath() + "/" + CameraUtil.getDateFormatStr() + suffix;
        mMediaRecorder.setOutputFile(mRecordFilePath);

        //Step 5 :Set the preview output
        mMediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());

        //Step 6 :Prepare configured MediaRecorder
    }

    @Override
    public boolean startRecorder() {
        //Step 6 :Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            mIsRecording = true;
        } catch (Exception e) {
            e.printStackTrace();
            releaseRecorder(mCamera, false);
            return false;
        }
        return true;
    }

    @Override
    public boolean resumeRecording() {
        return false;
    }

    @Override
    public boolean pauseRecording() {
        return false;
    }

    @Override
    public boolean stopRecorder() {
        mMediaRecorder.stop();
        return true;
    }

    @Override
    public String getRecordFilePath() {
        return mRecordFilePath;
    }

    @Override
    public boolean releaseRecorder(Camera camera, boolean deleteFile) {
        if (mMediaRecorder == null) {
            return false;
        }
        try {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mMediaRecorder = null;
            mIsRecording = false;
            if (camera != null)
                camera.lock();
        }
        return true;
    }

    @Override
    public boolean isRecording() {
        return mIsRecording;
    }
}
