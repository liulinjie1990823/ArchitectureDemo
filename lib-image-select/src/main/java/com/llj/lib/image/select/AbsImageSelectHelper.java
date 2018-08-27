package com.llj.lib.image.select;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by llj on 15/12/3.
 */
public abstract class AbsImageSelectHelper {
    //系统拍照
    static final int CHOOSE_PHOTO_FROM_CAMERA      = 10001;
    //系统相册
    static final int CHOOSE_PHOTO_FROM_ALBUM       = 10002;
    //系统剪裁
    static final int CHOOSE_PHOTO_FROM_SYSTEM_CROP = 10003;


    private String mCropImagePath;//剪切后存放的位置
    private int mQuality = 100;
    private int mOutputSize = 300;
    private String mImageDir;

    protected Activity getActivity() {
        return null;
    }

    protected Fragment getFragment() {
        return null;
    }

    /**
     * 设置图片压缩后的质量，默认为100
     *
     * @param quality 图片质量 0 - 100
     */
    protected void setQuality(int quality) {
        mQuality = quality;
    }

    public int getQuality() {
        return mQuality;
    }

    public String getImageDir() {
        return mImageDir;
    }
    public void setImageDir(String imageDir) {
        mImageDir = imageDir;
    }

    public int getOutputSize() {
        return mOutputSize;
    }

    public void setOutputSize(int outputSize) {
        mOutputSize = outputSize;
    }



    private String getCropImagePath() {
        return mCropImagePath;
    }

    protected abstract Intent createIntent();

    protected abstract void onActivityResult(int requestCode, int resultCode, Intent intent, OnGetFileListener onGetFileListener);

    public interface OnGetFileListener {
        void AfterGetFile(File file);
    }


    /**
     * 压缩到固定大小（100k）
     *
     * @param image
     *
     * @return
     */
    protected static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * bitmap转file，压缩85%
     *
     * @param bmp
     * @param file
     *
     * @return
     */
   private File getFileFromCompressBitmap(Bitmap bmp, File file, int quality) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            // 直接压缩80%
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 裁剪图片
     */
    void toSystemCrop(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);// 黑边
        intent.putExtra("noFaceDetection", true); // no face detection
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        // 剪切返回，头像存放的路径
        mCropImagePath = getImageDir() + File.separator + UUID.randomUUID().toString() + "cropImage.png";
        intent.putExtra("output", Uri.fromFile(new File(getCropImagePath()))); // 专入目标文件
        if (getFragment() != null) {
            getFragment().startActivityForResult(intent, CHOOSE_PHOTO_FROM_SYSTEM_CROP);
        } else {
            getActivity().startActivityForResult(intent, CHOOSE_PHOTO_FROM_SYSTEM_CROP);
        }
    }

    void handleCropImage(OnGetFileListener onGetFileListener) {
        if (TextUtils.isEmpty(getCropImagePath())) {
            return;
        }
        File outFile = new File(getCropImagePath());
        if (!outFile.exists()) {
            return;
        }
        if (getQuality() != 100) {
            //进行质量压缩过
            Bitmap bitmap = BitmapFactory.decodeFile(outFile.getAbsolutePath());
            String compressCropImagePath = getImageDir() + File.separator + UUID.randomUUID().toString() + "_compressCropImage.png";
            File compressFile = getFileFromCompressBitmap(bitmap, new File(compressCropImagePath), getQuality());
            if (compressFile != null && compressFile.exists()) {
                onGetFileListener.AfterGetFile(compressFile);
            }
        } else {
            //没有进行质量压缩过
            onGetFileListener.AfterGetFile(outFile);
        }
    }
}
