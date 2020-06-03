# https://developer.android.com/studio/projects/configure-cmake

cmake_minimum_required(VERSION 3.4.1)
include_directories(./src/main/jni/include)
link_directories(./src/main/jni/lib/${ANDROID_ABI})

find_library(log-lib log)
find_library(android-lib android)
find_library(bitmap-lib jnigraphics)

#设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})

add_library(native-lib
        SHARED
        src/main/jni/native-lib.cc)

target_link_libraries(native-lib
        ${log-lib}
        ${android-lib}
        ${bitmap-lib}
        turbojpeg)
