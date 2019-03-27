//
// Created by 刘林杰 on 2019/3/27.
//


#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_llj_lib_jni_test_JniTest_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}