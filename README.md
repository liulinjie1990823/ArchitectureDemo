# ArchitectureDemo

1. 组件化使用cc框架来解耦：https://github.com/luckybilly/CC
 一些常用组件化方案对比：https://github.com/luckybilly/AndroidComponentizeLibs
2. 页面跳转使用Arouter来跳转，同时也可以使用实现IProvider接口，并下沉到中间件中，然后在其他组件中使用注解获取相应对象，来实现通信

### 页面跳转
1. 界面是否登录通过一个int类型的值判断，每个跳转都要经过LoginInterceptor，判断如果需要登录，将要跳转界面的path和Bundle一同
发送到登录页，登录完成后关闭登录页继续跳转到指定页面
2. 跳转链接中增加版本号和内页字段，用来判断是否跳内页还是外页



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
