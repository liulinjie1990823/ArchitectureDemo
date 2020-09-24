/**
 * describe
 *
 * @author liulinjie
 * @date 2020/9/1 11:06 AM
 */
object Versions {
  const val applicationId = "com.llj.architecturedemo"
  const val minSdkVersion = 21
  const val compileSdkVersion = 29
  const val targetSdkVersion = 29
  const val compileJdkVersion = 1.8
  const val buildToolsVersion = "29.0.3"
  const val versionCode = 100
  const val versionName = "1.0.0"


  const val kotlin = "1.4.10"
  const val bintray_release = "0.8.1"
  const val walle = "1.1.7"

  const val lifecycle = "2.2.0"
  const val room = "2.2.5"

  const val dagger = "2.29.1"
  const val dagger_hilt = "2.29-alpha"
  const val autodispose = "1.4.0"

  const val butterknife = "10.2.3"

  const val retrofit2 = "2.9.0"
  const val okhttp = "4.9.0"
  const val fresco = "2.3.0"

  const val smartrefresh = "1.1.3"
  const val skin = "4.0.5"

  const val auto_value = "1.6.2"

  const val stetho = "1.5.1"
  const val leakcanary = "2.4"

  const val nim = "7.5.0"
  const val socialization = "2.0.18"
}

object Deps {

  //插件
  const val android_gradle_plugin_old = "com.android.tools.build:gradle:3.2.1"
  const val android_gradle_plugin = "com.android.tools.build:gradle:3.6.3"
  const val android_gradle_plugin_new = "com.android.tools.build:gradle:4.0.0"
  const val butterknife_gradle_plugin = "com.jakewharton:butterknife-gradle-plugin:${Versions.butterknife}"
  const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  const val walle_plugin = "com.meituan.android.walle:plugin:${Versions.walle}"

  //语言
  const val kotlin_stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
  const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

  const val walle = "com.meituan.android.walle:library:${Versions.walle}"

  const val support_design = "com.google.android.material:material:1.0.0"

  //支持库
  //https://developer.android.com/jetpack/androidx/releases/activity
  const val androidx_activity = "androidx.activity:activity:1.1.0"
  const val androidx_annotations = "androidx.annotation:annotation:1.1.0"
  const val androidx_appcompat = "androidx.appcompat:appcompat:1.2.0"
  const val androidx_fragment = "androidx.fragment:fragment:1.2.5"
  const val androidx_fragment_ktx = "androidx.fragment:fragment-ktx:1.2.5"
  const val androidx_arch_common = "androidx.arch.core:core-common:2.1.0"
  const val androidx_arch_runtime = "androidx.arch.core:core-runtime:2.1.0"
  const val androidx_core = "androidx.core:core:1.3.1"
  const val androidx_core_ktx = "androidx.core:core-ktx:1.3.1"
  const val androidx_collection = "androidx.collection:collection:1.1.0"


  //lifecycle
  const val lifecycle_common = "androidx.lifecycle:lifecycle-common:${Versions.lifecycle}"
  const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
  const val lifecycle_service = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"
  const val lifecycle_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
  const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
  const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle}"
  const val lifecycle_viewmodel_savedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
  const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycle}"
  const val lifecycle_process = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
  const val legacy_support_v4 = "androidx.legacy:legacy-support-v4:1.0.0"

  const val work_runtime = "androidx.work:work-runtime:2.3.4"
  const val work_runtime_ktx = "androidx.work:work-runtime-ktx:2.3.4"
  const val localbroadcastmanager = "androidx.localbroadcastmanager:localbroadcastmanager:1.0.0"

  //分包
  const val multidex = "androidx.multidex:multidex:2.0.1"
  const val multidex_instrumentation = "androidx.multidex:multidex-instrumentation:2.0.1"

  //ui
  const val constraint_layout = "androidx.constraintlayout:constraintlayout:2.0.1"//约束布局
  const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
  const val recyclerview_selection = "androidx.recyclerview:recyclerview-selection:1.1.0"
  const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
  const val viewpager = "androidx.viewpager:viewpager:1.0.0"
  const val cardview = "androidx.cardview:cardview:1.0.0"
  const val coordinatorlayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
  const val drawerlayout = "androidx.drawerlayout:drawerlayout:1.1.1"
  const val slidingpanelayout = "androidx.slidingpanelayout:slidingpanelayout:1.1.0"
  const val transition = "androidx.transition:transition:1.3.1"

  //room
  //https://developer.android.com/jetpack/androidx/releases/room
  const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
  const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
  const val room_rxjava2 = "androidx.room:room-rxjava2:${Versions.room}"

  //权限
  //https://github.com/yanzhenjie/AndPermission
  const val permission = "com.yanzhenjie:permission:2.0.3"

  //框架
  //https://github.com/google/dagger
  const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
  const val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
  const val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
  const val dagger_android_compiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
  const val dagger_hilt = "com.google.dagger:hilt-android:${Versions.dagger_hilt}"
  const val dagger_hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger_hilt}"

  //https://github.com/ReactiveX/RxJava
  const val rxjava = "io.reactivex:rxjava:1.2.3"
  const val rxjava2 = "io.reactivex.rxjava2:rxjava:2.2.19"
  const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"


  const val autodispose = "com.uber.autodispose:autodispose:${Versions.autodispose}"
  const val autodispose_android = "com.uber.autodispose:autodispose-android:${Versions.autodispose}"
  const val autodispose_android_archcomponents = "com.uber.autodispose:autodispose-android-archcomponents:${Versions.autodispose}"

  //页面路由
  //https://github.com/alibaba/ARouter
  const val arouter_api = "com.alibaba:arouter-api:1.5.0"
  const val arouter_compiler = "com.alibaba:arouter-compiler:1.2.2"

  //注解
  //https://github.com/JakeWharton/butterknife
  const val butterknife = "com.jakewharton:butterknife:${Versions.butterknife}"
  const val butterknife_compiler = "com.jakewharton:butterknife-compiler:${Versions.butterknife}"

  //事件
  //https://github.com/greenrobot/EventBus
  const val eventbus = "org.greenrobot:eventbus:3.2.0"

  //网络
  //https://github.com/square/retrofit
  const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
  const val retrofit2_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
  const val retrofit2_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
  const val retrofit2_adapter_rxjava = "com.squareup.retrofit2:adapter-rxjava:${Versions.retrofit2}"

  //https://github.com/square/okhttp
  const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
  const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

  //https://github.com/google/gson
  const val gson = "com.google.code.gson:gson:2.8.6"
  const val fastjson = "com.alibaba:fastjson:1.1.70.android"
  const val bolts = "com.parse.bolts:bolts-tasks:1.4.0"
  const val PersistentCookieJar = "com.github.franmontiel:PersistentCookieJar:v1.0.1"

  //图片加载
  //https://github.com/facebook/fresco
  const val fresco = "com.facebook.fresco:fresco:${Versions.fresco}"
  const val fresco_animated_gif = "com.facebook.fresco:animated-gif:${Versions.fresco}"
  const val fresco_animated_webp = "com.facebook.fresco:animated-webp:${Versions.fresco}"
  const val fresco_webpsupport = "com.facebook.fresco:webpsupport:${Versions.fresco}"
  const val fresco_imagepipeline_okhttp3 = "com.facebook.fresco:imagepipeline-okhttp3:${Versions.fresco}"

  //glide
  //https://github.com/bumptech/glide
  const val glide = "com.github.bumptech.glide:glide:4.11.0"
  const val glide_compile = "com.github.bumptech.glide:compiler:4.11.0"
  const val glide_integration = "com.github.bumptech.glide:okhttp3-integration:4.11.0"
  const val glide_bitmappool = "com.amitshekhar.android:glide-bitmap-pool:0.0.1"
  const val glide_transformations = "jp.wasabeef:glide-transformations:2.0.1"
  const val glidepalette = "com.github.florent37:glidepalette:2.1.2"

  //监控内存泄露
  //https://github.com/square/leakcanary
  const val leakcanary_android = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
  const val leakcanary_android_no_op = "com.squareup.leakcanary:leakcanary-android-no-op:1.6.3"

  //测试
  //https://github.com/facebook/stetho
  const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
  const val stetho_okhttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
  const val stetho_urlconnection = "com.facebook.stetho:stetho-urlconnection:${Versions.stetho}"

  const val timber = "com.jakewharton.timber:timber:4.7.1"
  const val logger = "com.orhanobut:logger:2.2.0"


  const val auto_value_annotations = "com.google.auto.const value:auto-const value-annotations:${Versions.auto_value}"
  const val auto_value_compile = "com.google.auto.const value:auto-const value:${Versions.auto_value}"


  const val lombok = "org.projectlombok:lombok:1.16.18"

  //视频播放器
  const val jiaozivideoplayer = "com.jiehun.libvideo:libvideo:1.0.0-release"

  //城市选择
  const val indexablerecyclerview = "me.yokeyword:indexablerecyclerview:1.3.0"

  //工具
  const val lib_utils = "com.llj:lib-utils:0.0.10"
  const val lib_base_event = "com.llj:lib-base-event:0.0.1"
  const val lib_image_loader = "com.llj:lib-image-loader:0.0.5"
  const val lib_image_select = "com.llj:lib-image-select:0.0.3"
  const val lib_statelayout = "com.llj:lib-statelayout:0.0.2"
  const val lib_statusbar = "com.llj:lib-statusbar:0.0.1"
  const val lib_swipeback = "com.llj:lib-swipeback:0.0.1"
  const val lib_universalAdapter = "com.llj:lib-universalAdapter:1.1.7"
  const val lib_scrollable = "com.llj:lib-scrollable:0.0.1"
  const val lib_webView = "com.llj:lib-webview:0.0.2"
  const val lib_jpeg_turbo_utils = "com.llj:lib-jpeg-turbo-utils:0.0.1"
  const val lib_record = "com.llj:lib-record:0.0.1"

  const val net = "com.llj:lib-net:0.0.9"

  const val tracker = "com.llj:lib-tracker:0.0.1"

  const val base = "com.llj:lib-base:0.0.12"


  const val base_compiler = "com.llj:lib-base-compiler:0.0.1"
  const val component_annotation = "com.llj:lib-component-annotation:0.0.1"
  const val component_api = "com.llj:lib-component-api:0.0.1"
  const val component_compiler = "com.llj:lib-component-compiler:0.0.1"

  const val jump_annotation = "com.llj:lib-jump-annotation:0.0.2"
  const val jump_api = "com.llj:lib-jump-api:0.0.2"
  const val jump_compiler = "com.llj:lib-jump-compiler:0.0.2"
  const val mvp_annotation = "com.llj:lib-mvp-annotation:0.0.1"
  const val mvp_compiler = "com.llj:lib-mvp-compiler:0.0.1"


  const val socialization = "com.llj:lib-socialization:${Versions.socialization}"
  const val socialization_qq = "com.llj:lib-socialization-qq:${Versions.socialization}"
  const val socialization_sina = "com.llj:lib-socialization-sina:${Versions.socialization}"
  const val socialization_wechat = "com.llj:lib-socialization-wechat:${Versions.socialization}"


  const val app_loading = "com.llj:app-loading:0.0.1"
  const val app_application = "com.llj:app-application:0.0.1"
  const val component_service = "com.llj:component-service:0.0.1"


  const val loadmore = "com.llj.loadmore:lib-loadmore:1.0.3"
  const val toast = "com.hjq:toast:6.0"
  //ui

  //换肤
  //https://github.com/ximsfei/Android-skin-support
  const val skin_support = "skin.support:skin-support:${Versions.skin}"
  const val skin_support_appcompat = "skin.support:skin-support-appcompat:${Versions.skin}"
  const val skin_support_design = "skin.support:skin-support-design:${Versions.skin}"
  const val skin_support_cardview = "skin.support:skin-support-cardview:${Versions.skin}"
  const val skin_support_constraint_layout = "skin.support:skin-support-constraint-layout:${Versions.skin}"

  //必须组件
  //https://github.com/scwang90/SmartRefreshLayout
  const val SmartRefreshLayout = "com.scwang.smartrefresh:SmartRefreshLayout:${Versions.smartrefresh}"
  const val SmartRefreshHeader = "com.scwang.smartrefresh:SmartRefreshHeader:${Versions.smartrefresh}"
  const val MagicIndicator = "com.github.hackware1993:MagicIndicator:1.6.0"//tab
  const val AutoScrollViewPager = "com.github.demoNo:AutoScrollViewPager:v1.0.2"//轮播

  const val vlayout = "com.alibaba.android:vlayout:1.2.20"
  const val ChipsLayoutManager = "com.beloo.widget:ChipsLayoutManager:0.3.7@aar"//tags
  const val BasePopup = "com.github.razerdp:BasePopup:2.2.1"//window弹框
  const val BasePopup_support = "com.github.razerdp:BasePopup-compat-support:2.2.1"//window弹框
  const val circleprogressbar = "com.dinuscxj:circleprogressbar:1.3.0"//圆形进度
  const val pickerView = "com.contrarywind:Android-PickerView:4.1.9"//选择城市
  const val blurry = "jp.wasabeef:blurry:3.0.0"//高斯模糊
  const val ExpandableTextView = "cn.carbs.android:ExpandableTextView:1.0.3"//折叠TextView
  const val badgeview = "q.rorbin:badgeview:1.1.3"//标签
  const val hyman_flowlayout = "com.hyman:flowlayout-lib:1.1.2"//流式布局
  const val lankton_flowlayout = "cn.lankton:flowlayout:3.1.0"//支持单行流布局
  const val fillblankview = "com.xw.repo:fillblankview:2.3@aar"//密码输入
  const val gpuimage = "jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.1"//滤镜

  const val observablescrollview = "com.github.ksoichiro:android-observablescrollview:1.6.0"
  const val firebase = "com.google.firebase:firebase-core:16.0.7"
  const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.9.9"
  const val android_beacon_library = "org.altbeacon:android-beacon-library:2.15.2"
  const val stat = "com.wh.repo:Android-Exposure-Stat:1.0.5"

  //网易云信IM
  const val im_base = "com.netease.nimlib:basesdk:${Versions.nim}"
  const val im_push = "com.netease.nimlib:push:${Versions.nim}"
  const val im_chatroom = "com.netease.nimlib:chatroom:${Versions.nim}"

  const val meizu_push = "com.meizu.flyme.internet:push-internal:3.6.3@aar"
  const val oppo_push = "com.heytap.mcssdk:mcssdk:2.0.2"
  const val huawei_push = "com.huawei.hms:push:3.0.3.301"

  //huawei_push                       ="com.huawei.hms:push:4.0.3.301"
  const val diooto = "com.wh.diooto:Codiooto:1.1.3"
  const val sketch_gif = "me.panpf:sketch-gif:2.7.1-rc01"
  const val sketch = "me.panpf:sketch:2.7.1-rc01"

  //RxCache
  const val rxcache = "com.github.VictorAlbertos.RxCache:runtime:1.7.0-1.x"
  const val Jolyglot = "com.github.VictorAlbertos.Jolyglot:gson:0.0.3"

  //悬浮窗
  const val float_window = "com.imuxuan:floatingview:1.5"

}
