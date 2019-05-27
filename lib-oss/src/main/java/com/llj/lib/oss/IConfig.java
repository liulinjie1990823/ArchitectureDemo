package com.llj.lib.oss;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-05-24
 */
public interface IConfig {

    String getEndpoint();

    /**
     * 上传空间
     */
    String getBucket();
    /**
     * 上传空间
     */
    String getObjectKey();

    String getStsServer();

    String getAccessKeyId();
    String getSecretKeyId();
    String getSecurityToken();


    String getCallbackUrl();
    String getCallbackBody();
    String getCallbackBodyType();

}
