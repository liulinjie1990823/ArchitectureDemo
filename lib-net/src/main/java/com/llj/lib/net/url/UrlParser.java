package com.llj.lib.net.url;

import okhttp3.HttpUrl;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/20
 */
public interface UrlParser {
    void init();

    HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url);
}
