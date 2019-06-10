package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.help.FilePathHelper;
import com.llj.lib.image.loader.FrescoImageLoader;
import com.llj.lib.image.loader.ICustomImageLoader;
import com.llj.lib.kodo.KodoUploadManager;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/16
 */
@Route(path = CRouter.APP_KODO_ACTIVITY)
public class KodoActivity extends AppMvcBaseActivity {
    public static final        String           RECORDER_PATH = FilePathHelper.CACHE_PATH + "recoder";
    @BindView(R.id.sdv_show)   SimpleDraweeView mSdvShow;
    @BindView(R.id.btn_upload) Button           mBtnUpload;
    @BindView(R.id.btn_show)   Button           mBtnShow;

    private UploadManager mUploadManager;

    private ICustomImageLoader<GenericDraweeView> mImageLoader;

    private String mToken;

    private String mUrl;

    @Override
    public int layoutId() {
        return R.layout.activity_kodo;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        mImageLoader = FrescoImageLoader.getInstance(this);

        mBtnUpload.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
//                File file = new File(Environment.getExternalStorageDirectory() + "/" + "camera_test/" + "test1.jpeg");
                File file = new File(Environment.getExternalStorageDirectory() + "/" + ".mv/" + "73F09569AFDF7A7328AD427144F348DD.mp4");
                String accessKey = getResources().getString(R.string.kodo_access_key);
                String secretKey = getResources().getString(R.string.kodo_secret_key);
                String bucketName = getResources().getString(R.string.bucket_name);
                mToken = KodoUploadManager.getUploadToken(accessKey, secretKey, bucketName);
                mUploadManager.put(file, null, mToken, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        mUrl = "http://pq1ofoe5a.bkt.clouddn.com/" + response.optString("key");

                        showToast("上传成功");
                        showToast(mUrl);
                    }
                }, new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {

                    }
                }, new UpCancellationSignal() {
                    @Override
                    public boolean isCancelled() {
                        return false;
                    }
                }));
            }
        });
        mBtnShow.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                mImageLoader.loadImage(mUrl, 0, 0, mSdvShow);
            }
        });
    }

    @Override
    public void initData() {
        init();
    }

    private void init() {
        FileRecorder fileRecorder = null;
        try {
            fileRecorder = new FileRecorder(RECORDER_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        KeyGenerator keyGen = new KeyGenerator() {
            public String gen(String key, File file) {
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
            }
        };
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .recorder(fileRecorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();

        mUploadManager = KodoUploadManager.init(config);
    }
}
