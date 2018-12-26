package com.llj.architecturedemo.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TemplateManager {
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;

    public final static String LOCAL_TEMPLATE_PATH = "local_path3";

    private final String ASSET_LOCAL_TEMPLATE_NAME = "mvtemplate.zip";

    private static TemplateManager mManager;
    private        ExecutorService mExecutorService;
    private        Handler         mCallbackHandler = new Handler();

    public static String rootDir            = Environment.getExternalStorageDirectory().getPath();
    public static String FOLDER_MV_TEMPLATE = rootDir + File.separator + "mvphotos";

    private TemplateManager() {
        mExecutorService = Executors.newSingleThreadExecutor();
        File file = new File(FOLDER_MV_TEMPLATE);
        if (!file.exists()) {
            Log.d("TemplateManager", "create mvphotos" + file.mkdirs());
        }
    }

    public static TemplateManager getInstance() {
        if (mManager == null) {
            mManager = new TemplateManager();
        }
        return mManager;
    }

    public String generateUnZipPath(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return FOLDER_MV_TEMPLATE + File.separator + md5(url);
    }


    private static final String HASH_ALGORITHM = "MD5";
    private static final int    RADIX          = 10 + 26; // 10 digits + 26 letters

    public static String md5(String imageUri) {
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        return bi.toString(RADIX);
    }

    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public boolean isFolderExists(String url) {
        String localPath = generateUnZipPath(url);
        File localFile = new File(localPath);
        File jsonFile = new File(localFile, "001.json");
        File jsonFile2 = new File(localFile, "template.json");

        if (!localFile.exists() || localFile.length() <= 0 || !jsonFile.exists() || !jsonFile2.exists()) {
            return false;
        }
        return true;
    }


    public void unzip(final String id, final String zipLocalPath, final String unzipLocalPath, final OnDownLoadListener listener) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final int code;
                if (unzipSingle(zipLocalPath, unzipLocalPath)) {
                    code = SUCCESS;
                } else {
                    code = FAILURE;
                }
                mCallbackHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onZip(id, code);
                    }
                });
            }
        };
        mExecutorService.execute(runnable);
    }

    private boolean unzipSingle(String zipPath, String unzipPath) {
        if (!TextUtils.isEmpty(zipPath) && !TextUtils.isEmpty(unzipPath)) {
            try {
                ZipUtil.unzip(zipPath, unzipPath);
                return true;
            } catch (Exception e) {
                FileUtil.deleteFolder(unzipPath, true);
                Writer writer = new StringWriter();
                PrintWriter pw = new PrintWriter(writer);
                e.printStackTrace(pw);
                pw.close();
                String error = writer.toString();
                return false;
            }
        } else {
            return false;
        }
    }

    public void copyLocalTemplate(Context context) {
        if (!isLocalTemplateExists()) {
            String unZipPath = generateUnZipPath(LOCAL_TEMPLATE_PATH);
            try {
                File file = new File(unZipPath);
                if (!file.exists()) {
                    Log.d("TemplateManager", "create template folder " + file.mkdirs());
                }
                ZipUtil.unZipFromAssets(context, ASSET_LOCAL_TEMPLATE_NAME, unZipPath, true);
            } catch (IOException e) {
                e.printStackTrace();
                //解压失败，删除这个文件夹
                FileUtil.deleteFolder(unZipPath, true);
            }
        }
    }


    private boolean isLocalTemplateExists() {
        return isFolderExists(LOCAL_TEMPLATE_PATH);
    }

    public interface OnDownLoadListener {
        void onProgress(String id, float progress);

        void onDownload(String id, int code);

        void onZip(String id, int code);
    }

    // for photos use

    String[] photos = new String[]{
            "a.jpeg",
            "b.jpeg",
            "c.jpeg",
            "d.jpeg",
            "e.jpeg",
    };

    public String getPhotoString() {
        String rootDir = Environment.getExternalStorageDirectory().getPath();
        String mvDir = rootDir + File.separator + "mvphotos" + File.separator;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < photos.length; i++) {
            sb.append(mvDir).append(photos[i]);
            if (i != photos.length - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public String getLicensePath() {
        return rootDir + File.separator + "mvphotos" + File.separator + "License.lic";
    }

    public String getLicenseString(Context context) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream inputStream = context.getAssets().open("License.lic");
            int count;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = inputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, count);
            }
            inputStream.close();
            return baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void copyLocalLicense(Context context) {
        String rootDir = Environment.getExternalStorageDirectory().getPath();
        String mvDir = rootDir + File.separator + "mvphotos" + File.separator;
        try {
            InputStream inputStream = context.getAssets().open("License.lic");
            File file = new File(mvDir + "License.lic");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int count;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyLocalPhoto(Context context) {
        String rootDir = Environment.getExternalStorageDirectory().getPath();
        String mvDir = rootDir + File.separator + "mvphotos" + File.separator;
        for (int i = 0; i < photos.length; i++) {
            try {
                InputStream inputStream = context.getAssets().open("demo_photos/" + photos[i]);
                File file = new File(mvDir + photos[i]);
                if (!file.exists()) {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int count = 0;
                    byte[] buffer = new byte[1024 * 1024];
                    while ((count = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
