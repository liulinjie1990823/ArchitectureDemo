package com.llj.lib.image.select;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;

/**
 * 图片操控类
 * Created by liulj on 15/12/3.
 */
public class ImageSelect {
    static final int IMAGE_TYPE_CAPTURE = 0;
    static final int IMAGE_TYPE_PICK    = 1;
    private int mImageType;

    private ImageCaptureHelper mImageCaptureHelper;
    private ImagePickHelper    mImagePickHelper;

    private Activity mActivity;
    private Fragment mFragment;


    public ImageSelect(String imageDir) {
        init(imageDir);
    }

    public ImageCaptureHelper getImageCaptureHelper() {
        return mImageCaptureHelper;
    }

    public ImagePickHelper getImagePickHelper() {
        return mImagePickHelper;
    }


    private void init(String imageDir) {
        mImageCaptureHelper = new ImageCaptureHelper();
        mImagePickHelper = new ImagePickHelper();

        mImageCaptureHelper.setImageDir(imageDir);
        mImagePickHelper.setImageDir(imageDir);
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void pickImage(Activity activity) {
        mImageType = IMAGE_TYPE_PICK;
        mActivity = activity;
        mImagePickHelper.pickImage(activity);
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void pickImage(Fragment fragment) {
        mImageType = IMAGE_TYPE_PICK;
        mFragment = fragment;
        mImagePickHelper.pickImage(fragment);
    }

    @RequiresPermission(allOf = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void captureImage(Activity activity) {
        mImageType = IMAGE_TYPE_CAPTURE;
        mActivity = activity;
        mImageCaptureHelper.captureImage(activity);
    }

    @RequiresPermission(allOf = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void captureImage(Fragment fragment) {
        mImageType = IMAGE_TYPE_CAPTURE;
        mFragment = fragment;
        mImageCaptureHelper.captureImage(fragment);

    }

    //在对应的onActivityResult中调用
    public void onActivityResult(int requestCode, int resultCode, Intent intent, AbsImageSelectHelper.OnGetFileListener onGetFileListener) {
        if (mImageType == IMAGE_TYPE_CAPTURE) {
            mImageCaptureHelper.onActivityResult(requestCode, resultCode, intent, onGetFileListener);
        } else if (mImageType == IMAGE_TYPE_PICK) {
            mImagePickHelper.onActivityResult(requestCode, resultCode, intent, onGetFileListener);
        }
    }
}
