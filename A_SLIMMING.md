
# APK瘦身

- 使用armeabi一套so架构
- 使用xxhdpi一套图片
- 使用nimbledroid分析apk文件大小
- 使用tinypng压缩图片,使用shape,WebP,SVG
- 使用资源瘦身shrinkResources true
- 使用代码混淆minifyEnabled true
主流资源文件的混淆列表https://github.com/krschultz/android-proguard-snippets
- 移除无用依赖库
- 移除无用资源Remove Unused Resources，需要验证移除后不崩溃
- 使用AndResGuard对资源进行混淆优化 https://github.com/shwenzhang/AndResGuard
- 使用ReDex字节码优化