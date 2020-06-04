# https://developer.android.com/studio/projects/configure-cmake

cmake_minimum_required(VERSION 3.4.1)
include_directories(src/main/jni/include)

set(distribution_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs)

message(DEBUG ${distribution_DIR})
message(DEBUG "dadadadadada")
#引入libjpeg
add_library(libjpeg
        SHARED
        IMPORTED)
set_target_properties(libjpeg
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libjpeg.so)

#引入libturbojpeg
add_library(libturbojpeg
        SHARED
        IMPORTED)
set_target_properties(libturbojpeg
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/${ANDROID_ABI}/libturbojpeg.so)


find_library(log-lib log)
find_library(android-lib android)
find_library(bitmap-lib jnigraphics)

#设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})

add_library(turbojpeg-utils
        SHARED
        src/main/jni/native-lib.cc)

target_link_libraries(turbojpeg-utils
        ${log-lib}
        ${android-lib}
        ${bitmap-lib}
        libjpeg
        libturbojpeg)
