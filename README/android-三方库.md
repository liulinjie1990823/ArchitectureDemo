[TOC]

# 技术选型


## 类库选择

### 必须
##### 注解view
- butterknife <https://github.com/JakeWharton/butterknife> com.jakewharton:butterknife:9.0.0-rc2

##### 解耦
- [dagger2]https://github.com/google/dagger
- [anvil](https://github.com/square/anvil)

##### 事件
- [EventBus](https://github.com/greenrobot/EventBus)

##### 页面路由
- [arouter](https://github.com/alibaba/ARouter)

##### rxjava2
- [rxJava2](https://github.com/ReactiveX/RxJava)
- [AutoDispose](https://github.com/uber/AutoDispose)

##### 应用启动框架
- [alpha](https://github.com/alibaba/alpha)

##### 线程操作
- [bolt](https://github.com/BoltsFramework/Bolts-Android)

##### 网络
- [okhttp](https://github.com/square/okhttp) com.squareup.okhttp3:okhttp:3.12.1
- [retrofit](https://github.com/square/retrofit) com.squareup.retrofit2:retrofit:2.5.0
- okhttp_logging_interceptor
- PersistentCookieJar

##### 内存回收存储
- SaveState <https://github.com/PrototypeZ/SaveState>

##### json解析
- [gson](https://github.com/google/gson)
- fastjson-android
- [moshi](https://github.com/square/moshi)

##### 图片加载
- [fresco](https://github.com/facebook/fresco)
- [glide](https://github.com/bumptech/glide)

##### 权限校验
- permission：<https://github.com/yanzhenjie/AndPermission> com.yanzhenjie:permission:2.0.0-rc12

##### 二维码
- ZXing：
- ZBar：

##### 渠道包
- walle <https://github.com/Meituan-Dianping/walle>
- VasDolly <https://github.com/Tencent/VasDolly/>

##### 文件下载
- [okdownload](https://github.com/lingochamp/okdownload)
- [aria](https://github.com/AriaLyy/Aria)


##### 进程通信
- Andromeda <https://github.com/iqiyi/Andromeda>


### 选择
##### 文件存储服务
- oss <https://github.com/aliyun/aliyun-oss-android-sdk> com.aliyun.dpa:oss-android-sdk:2.9.2

##### 组件化
- cc <https://github.com/luckybilly/CC>

##### 插件化
- tinker <https://github.com/Tencent/tinker>

##### 数据库
- [DBFlow](https://github.com/agrosner/DBFlow)

##### KV存储
- MMKV <https://github.com/Tencent/MMKV>
- Treasure <https://github.com/baoyongzhang/Treasure>

##### app更新
- CheckVersionLib <https://github.com/AlexLiuSheng/CheckVersionLib>  com.allenliu.versionchecklib:library:2.1.9

##### 反射工具类
- [jOOR](https://github.com/jOOQ/jOOR)

##### ffmpeg
- javacv <https://github.com/bytedeco/javacv>
- FFmpeg-Android <https://github.com/bravobit/FFmpeg-Android>

##### 繁体和简体转换
- [opencc](https://github.com/qichuan/android-opencc)

##### 性能检测
- [flipper](https://github.com/facebook/flipper) com.facebook.flipper:flipper:0.14.1
- [leakcanary](https://github.com/square/leakcanary)
- [matrix](https://github.com/Tencent/matrix)
- [AndroidGodEye](https://github.com/Kyson/AndroidGodEye)


##### ui库
- webView：tbs服务
- 约束布局：com.android.support.constraint:constraint-layout:2.0.0-beta3
- 换肤：<https://github.com/ximsfei/Android-skin-support>
- vlayout：<https://github.com/alibaba/vlayout> com.alibaba.android:vlayout:1.2.18@aar
- 刷新：<https://github.com/scwang90/SmartRefreshLayout>  com.scwang.smartrefresh:SmartRefreshLayout:1.1.0-alpha-19
- 指示器：<https://github.com/hackware1993/MagicIndicator> com.github.hackware1993:MagicIndicator:1.5.0
- 轮播：<https://github.com/demoNo/AutoScrollViewPager> com.github.demoNo:AutoScrollViewPager:v1.0.2
- PageTransformer：
- 日期城市选择器：<https://github.com/Bigkoo/Android-PickerView> com.contrarywind:Android-PickerView:3.2.7
- tag标签：<https://github.com/qstumn/BadgeView> q.rorbin:badgeview:1.1.3
- 流式布局：<https://github.com/lankton/android-flowlayout> cn.lankton:flowlayout:3.1.0
- 密码输入框：<https://github.com/woxingxiao/FillBlankView> com.xw.repo:fillblankview:2.1@aar
- 展开TextView：<https://github.com/Carbs0126/ExpandableTextView> cn.carbs.android:ExpandableTextView:1.0.3
- 图片压缩：<https://github.com/Curzibn/Luban> top.zibin:Luban:1.1.8
- 图片压缩：<https://github.com/zetbaitsu/Compressor> id.zelory:compressor:2.1.0
- 图片进度框：<https://github.com/mrwonderman/android-square-progressbar> ch.halcyon:squareprogressbar:1.6.4
- 圆形进度框：<https://github.com/dinuscxj/CircleProgressBar> com.dinuscxj:circleprogressbar:1.3.0
- 圆角布局：<https://github.com/GcsSloop/rclayout> com.gcssloop.widget:rclayout:1.8.1
- 礼物弹幕：<https://github.com/Yuphee/RewardLayout> com.github.Yuphee:RewardLayout:1.0.5.7
- 刮奖效果：<https://github.com/myinnos/AndroidScratchCard> com.github.myinnos:AndroidScratchCard:v1.0
- 图文混排效果：<https://github.com/sendtion/XRichText> com.github.sendtion:XRichText:1.8
- 布局回收优化：<https://github.com/facebook/litho>
- 直尺效果：<https://github.com/kevalpatel2106/android-ruler-picker> 
- PopupWindow：<https://github.com/razerdp/BasePopup> com.github.razerdp:BasePopup:2.2.1
- 高斯模糊：<https://github.com/wasabeef/Blurry> jp.wasabeef:blurry:3.0.0

## plugin选择

- gradle-task-tree：<https://github.com/dorongold/gradle-task-tree>

