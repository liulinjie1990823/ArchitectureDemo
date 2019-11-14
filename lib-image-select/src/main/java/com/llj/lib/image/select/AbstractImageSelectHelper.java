package com.llj.lib.image.select;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
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
public abstract class AbstractImageSelectHelper {
    static final int CHOOSE_PHOTO_FROM_CAMERA      = 10001; //系统拍照
    static final int CHOOSE_PHOTO_FROM_ALBUM       = 10002; //系统相册
    static final int CHOOSE_PHOTO_FROM_SYSTEM_CROP = 10003; //系统剪裁

    protected static final String CAPTURE_IMAGE_NAME       = "_captureImage.jpg";
    protected static final String CROP_IMAGE_NAME          = "_cropImage.jpg";
    protected static final String COMPRESS_CROP_IMAGE_NAME = "_compressCropImage.jpg";

    private String mCropImageFilePath;
    private int    mQuality    = 100;
    private int    mOutputSize = 300;
    private String mImageDir;

    protected Activity getActivity() {
        return null;
    }

    protected Fragment getFragment() {
        return null;
    }


    public String getImageTempDir() {
        return mImageDir;
    }

    public void setImageTempDir(String imageDir) {
        mImageDir = imageDir;
    }

    public String getCropImageFilePath() {
        return mCropImageFilePath;
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

    public int getOutputSize() {
        return mOutputSize;
    }

    public void setOutputSize(int outputSize) {
        mOutputSize = outputSize;
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
    File getFileFromCompressBitmap(Bitmap bmp, File file, int quality) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            // 直接压缩80%
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            fileOutputStream.close();
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

     String createFilePath(String name) {
        return getImageTempDir() + File.separator + UUID.randomUUID().toString() + name;
    }

    /**
     * 裁剪图片
     */
    void toSystemCrop(File input, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.setDataAndType(getUriCompat(input), "image/*");
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
        mCropImageFilePath = createFilePath(CROP_IMAGE_NAME);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCropImageFilePath))); // 专入目标文件

        if (getFragment() != null) {
            getFragment().startActivityForResult(intent, CHOOSE_PHOTO_FROM_SYSTEM_CROP);
        } else {
            getActivity().startActivityForResult(intent, CHOOSE_PHOTO_FROM_SYSTEM_CROP);
        }
    }

    /**
     * 兼容android7.0
     * @param file
     * @return
     */
    Uri getUriCompat(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = getActivity().getApplicationInfo().packageName + ".selectphotofileprovider";
            uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    void handleCropImage(OnGetFileListener onGetFileListener) {
        if (TextUtils.isEmpty(getCropImageFilePath())) {
            return;
        }
        File outFile = new File(getCropImageFilePath());
        if (!outFile.exists()) {
            return;
        }
        if (getQuality() != 100) {
            //进行质量压缩过
            Bitmap bitmap = BitmapFactory.decodeFile(outFile.getAbsolutePath());

            File compressCropImageFile = new File(createFilePath(COMPRESS_CROP_IMAGE_NAME));

            File compressFile = getFileFromCompressBitmap(bitmap, compressCropImageFile, getQuality());
            if (compressFile != null && compressFile.exists()) {
                onGetFileListener.AfterGetFile(compressFile);
            }
        } else {
            //没有进行质量压缩过
            onGetFileListener.AfterGetFile(outFile);
        }
    }
}
