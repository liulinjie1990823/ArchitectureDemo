package com.llj.lib.image.select;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.UUID;

/**
 * 从系统拍照返回
 * Created by liulj on 15/12/3.
 */
public class ImageCaptureHelper extends AbsImageSelectHelper {
    private File     mCameraOutFile;
    private Activity mActivity;
    private Fragment mFragment;

    void captureImage(Activity activity) {
        mActivity = activity;
        realCaptureImage(activity);
    }

    void captureImage(Fragment fragment) {
        mFragment = fragment;
        mActivity = mFragment.getActivity();
        realCaptureImage(fragment);
    }

    private void realCaptureImage(Activity activity) {
        activity.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
    }

    private void realCaptureImage(Fragment fragment) {
        fragment.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_CAMERA);
    }

    @Override
    protected Activity getActivity() {
        return mActivity;
    }

    @Override
    public Fragment getFragment() {
        return mFragment;
    }

    public File getCameraOutFile() {
        return mCameraOutFile;
    }

    private File makeOutFile() {
        String photoPath = UUID.randomUUID().toString() + "image.jpg";// 通过uuid生成照片唯一名字
        return new File(getImageDir(), photoPath);// 在该路径下构件文件对象
    }

    @Override
    protected Intent createIntent() {
        mCameraOutFile = makeOutFile();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraOutFile));
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent, OnGetFileListener onGetFileListener) {
        // 设置图片
        if (requestCode == CHOOSE_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            // 拍照返回,如果先前传入的文件路径下有文件，就通过回调返回
            if (getCameraOutFile() == null || !getCameraOutFile().exists()) {
                return;
            }
            toSystemCrop(Uri.fromFile(getCameraOutFile()), getOutputSize());

        } else if (requestCode == CHOOSE_PHOTO_FROM_SYSTEM_CROP && resultCode == Activity.RESULT_OK) {
            // 头像裁剪返回
            handleCropImage(onGetFileListener);
        }
    }
}
