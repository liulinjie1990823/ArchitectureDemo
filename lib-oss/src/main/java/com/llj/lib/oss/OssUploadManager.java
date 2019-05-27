package com.llj.lib.oss;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-05-24
 */
public class OssUploadManager {


    /**
     * @param context context
     * @param config  配置参数
     *
     * @return
     */
    public static OSS init(Context context, IConfig config) {
        //设置鉴权方式
        OSSCredentialProvider provider;
        if (config.getStsServer() == null || config.getStsServer().length() == 0) {
            provider = new OSSStsTokenCredentialProvider(config.getAccessKeyId(), config.getSecretKeyId(), config.getSecurityToken());
        } else {
            provider = new OSSAuthCredentialsProvider(config.getStsServer());
        }

        //设置超时参数
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        return new OSSClient(context.getApplicationContext(), config.getEndpoint(), provider, conf);
    }

}
