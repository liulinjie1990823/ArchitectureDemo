package com.llj.lib.base.config;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-22
 */
public class JumpConfig {
    private String mNativeScheme;
    private String mLoginPath;
    private String mLoadingPath;

    public String getLoginPath() {
        return mLoginPath;
    }

    public void setLoginPath(String loginPath) {
        mLoginPath = loginPath;
    }

    public String getLoadingPath() {
        return mLoadingPath;
    }

    public void setLoadingPath(String loadingPath) {
        mLoadingPath = loadingPath;
    }

    public String getNativeScheme() {
        return mNativeScheme;
    }

    public void setNativeScheme(String nativeScheme) {
        mNativeScheme = nativeScheme;
    }
}
