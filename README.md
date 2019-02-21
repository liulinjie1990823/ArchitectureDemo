# ArchitectureDemo

### 整体框架
1. 组件化使用cc框架来解耦：https://github.com/luckybilly/CC
 一些常用组件化方案对比：https://github.com/luckybilly/AndroidComponentizeLibs
2. 页面跳转使用Arouter来跳转，同时也可以使用实现IProvider接口，并下沉到中间件中，然后在其他组件中使用注解获取相应对象，来实现通信

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
