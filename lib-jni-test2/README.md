### 

1. 创建工程
2. 编写java和c代码
3. 编写CMakeLists.txt文件
4. 双击module,在弹框中连接c++  
![image](http://img.freelooper.com/QQ20190419-233728@2x.png)
5. 在gradle中配置
```
android {
    defaultConfig {
        externalNativeBuild {
            cmake {
                cppFlags ""
                abiFilters 'arm64-v8a', 'armeabi-v7a', 'x86', 'x86_64'
            }
        }
    }
    externalNativeBuild {
        cmake {
            path file('CMakeLists.txt')
        }
    }
}
```
6. 点击Build中的make module 'lib-jni-test2',会在下面目录中生成so
```
/Users/liulinjie/GitHub/ArchitectureDemo/lib-jni-test2/build/intermediates/cmake/debug/obj/armeabi-v7a
```