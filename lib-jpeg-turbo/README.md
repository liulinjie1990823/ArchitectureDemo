1. 下载https://github.com/libjpeg-turbo/libjpeg-turbo源码到本地
2. 新建一个lib module，在main下建一个cpp文件夹，并将上面libjpeg-turbo文件夹中的代码都复制到cpp目录中
3. 配置gradle，在gradle中指定cmake文件的目录path file('src/main/cpp/CMakeLists.txt')
    ```
    dependencies {
        api fileTree(include: ['*.jar'], dir: 'libs')
    }


    android {
        defaultConfig {
            externalNativeBuild {
                cmake {
                    cppFlags ""
                    abiFilters 'armeabi-v7a'
    //                abiFilters 'armeabi-v7a','x86'
                }
            }
        }
        externalNativeBuild {
            cmake {
                path file('src/main/cpp/CMakeLists.txt')
            }
        }
        sourceSets {
            main {
                jniLibs.srcDirs = ['src/main/jniLibs']
            }
        }
        packagingOptions {
        }

    }

    ```
4. 然后点击build中的make project进行编译
5. 最后就会在如下目录生成得到对应的so文件
![](http://img.freelooper.com/mweb/15911717006460.jpg)