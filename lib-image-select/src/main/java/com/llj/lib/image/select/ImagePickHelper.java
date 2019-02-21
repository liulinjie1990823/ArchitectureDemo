package com.llj.lib.image.select;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 从相册选择图片
 * Created by llj on 15/12/3.
 */
public class ImagePickHelper extends ImageSelectHelper {
    private Activity mActivity;
    private Fragment mFragment;

    void pickImage(Context activity) {
        mActivity = (Activity) activity;
        realPickImage(mActivity);
    }

    void pickImage(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
        realPickImage(fragment);
    }

    private void realPickImage(Activity activity) {
        activity.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_ALBUM);
    }

    private void realPickImage(Fragment fragment) {
        fragment.startActivityForResult(createIntent(), CHOOSE_PHOTO_FROM_ALBUM);
    }

    @Override
    protected Activity getActivity() {
        return mActivity;
    }

    @Override
    public Fragment getFragment() {
        return mFragment;
    }

    @Override
    protected Intent createIntent() {
        // 调用系统相册
        Intent intent = new Intent(Intent.ACTION_PICK);// 系统相册action
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent, OnGetFileListener onGetFileListener) {
        if (requestCode == CHOOSE_PHOTO_FROM_ALBUM && resultCode == Activity.RESULT_OK) {
            // 相册返回,存放在path路径的文件中
            String path = null;
            if (intent == null || intent.getData() == null) {
                return ;
            }
            // 获得相册中图片的路径
            if ("file".equals(intent.getData().getScheme())) {
                path = intent.getData().getPath();

            } else if ("content".equals(intent.getData().getScheme())) {
                Cursor cursor = getActivity().getContentResolver().query(intent.getData(), null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
            }
            // 如果路径存在，就复制文件到temp文件夹中
            if (path != null && path.length() > 0) {
                // 通过uuid生成照片唯一名字，这里只是生成了个file的对象，文件并没有生成
                String cameraOrAlbumPath = UUID.randomUUID().toString() + "image2.png";
                File tempFile = new File(getImageDir(), cameraOrAlbumPath);
                if (copyFile(new File(path), tempFile)) {
                    if (tempFile.exists()) {
                        toSystemCrop(tempFile, getOutputSize());
                    }
                }
            }
        } else if (requestCode == CHOOSE_PHOTO_FROM_SYSTEM_CROP && resultCode == Activity.RESULT_OK) {
            // 头像裁剪返回
            handleCropImage(onGetFileListener);
        }
    }

    private static  boolean copyFile(File srcFile, File destFile) {
        if (!srcFile.exists()) {
            return false;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();

            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(destFile));

            int size;
            byte[] temp = new byte[1024];
            while ((size = bis.read(temp, 0, temp.length)) != -1) {
                bos.write(temp, 0, size);
            }
            bos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos = null;
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bis = null;
            }
        }
        return false;
    }

}
