package com.llj.lib.kodo;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/16
 */
public class KodoUploadManager {

    private static UploadManager sUploadManager;


    public static UploadManager init(Configuration config) {
        sUploadManager = new UploadManager(config);
        return sUploadManager;
    }

    public static UploadManager getInstance() {
        return sUploadManager;
    }


    public static String getUploadToken(String accessKey, String secretKey, String bucketName) {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucketName);
    }


}
