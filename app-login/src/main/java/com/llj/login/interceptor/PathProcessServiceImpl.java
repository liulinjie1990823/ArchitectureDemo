package com.llj.login.interceptor;

import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PathReplaceService;

/**
 * ArchitectureDemo.
 * <p>
 * 因为ARouter处理的path必须以"/"开头，所以这里需要对所有外链加上
 *
 * @author llj
 * @date 2019-10-22
 */
@Route(path = "/login/PathReplaceService")
public class PathProcessServiceImpl implements PathReplaceService {
    @Override
    public String forString(String path) {
        if (!path.startsWith("/"))
            return "/" + path;
        return path;
    }

    @Override
    public Uri forUri(Uri uri) {
        return null;
    }

    @Override
    public void init(Context context) {

    }
}
