# ArchitectureDemo

### 快速开发框架基础类库
- 基础框架库 [lib-base](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-base)
- 图片加载封装 [lib-image-loader](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-image-loader)
- 系统图片选择封装 [lib-image-select](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-image-select)
- 地图 [lib-map](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-map)
- 网络 [lib-net](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-net)
- opengl封装 [lib-opengl](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-opengl)
- 系统图片选择封装 [lib-oss](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-oss)
- 二维码扫描 [lib-qrcodecore](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-qrcodecore)
- 拍照和录制视频 [lib-record](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-record)
- 社会化组件 [lib-socialization](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-socialization)
- 页面状态切换 [lib-statelayout](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-statelayout)
- 状态栏 [lib-statusbar](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-statusbar)
- 滑动返回关闭 [lib-swipeback](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-swipeback)
- 页面埋点 [lib-tracker](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-tracker)
- 通用适配器 [lib-universalAdapter](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-universalAdapter)
- 公共工具类 [lib-utils](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-utils)
- webview [lib-webview](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/lib-webview)
- 自动上传pgy并发钉钉通知 [plugin-upload](https://github.com/liulinjie1990823/ArchitectureDemo/tree/master/plugin-upload)

### 其他三方类库选择
[library](https://github.com/liulinjie1990823/ArchitectureDemo/blob/master/README/A_LIBRARY.md)

### ide插件选择
[plugin](https://github.com/liulinjie1990823/ArchitectureDemo/blob/master/README/A_PLUGIN.md)

### 加快编译

### apk瘦身

### 整体框架
1. 组件化使用cc框架：<https://github.com/luckybilly/CC>，组件件的交互使用cc的IComponent接口，在onCall中进行调用
 一些常用组件化方案对比：<https://github.com/luckybilly/AndroidComponentizeLibs>
2. 代码解耦使用dagger2：<https://github.com/google/dagger>
3. 页面跳转使用ARouter：<https://github.com/alibaba/ARouter>

### 页面跳转
1. 界面是否登录通过一个int类型的值判断，每个跳转都要经过LoginInterceptor，判断如果需要登录，将要跳转界面的path和Bundle一同
发送到登录页，登录完成后关闭登录页继续跳转到指定页面
2. 跳转链接中增加版本号和内页字段，用来判断是否跳内页还是外页

### 权限相关
1. 必须权限 
 
网络相关权限：
```
    <!-- 获取运营商信息，用于支持提供运营商信息相关的接口 -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <!-- 用于访问wifi网络信息，wifi信息会用于进行网络定位 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <!-- 这个权限用于获取wifi的获取权限，wifi信息会用来进行网络定位 -->
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <!-- 用于访问网络，网络定位需要上网 -->
    <uses-permission android:name="android.permission.INTERNET"/>
    <!-- 允许程序改变网络连接状态 -->
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>

```
读写存储权限：
```
    <!-- 写入扩展存储，向扩展卡写入数据，用于写入缓存定位数据 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <!-- 读取扩展存储 -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

电话相关权限：
```
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.READ_CALL_LOG" />
    <uses-permission android:name="android.permission.WRITE_CALL_LOG" />
    <uses-permission android:name="com.android.voicemail.permission.ADD_VOICEMAIL" />
    <uses-permission android:name="android.permission.USE_SIP" />
    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
```


## License
```text
Copyright 2018 liulinjie

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
