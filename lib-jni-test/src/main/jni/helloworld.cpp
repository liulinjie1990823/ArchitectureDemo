//
// Created by 刘林杰 on 2019/3/27.
//

#include <jni.h>
#include <string>
#include<android/log.h>//包含Android log打印   需要再make文件中添加  LOCAL_LDLIBS := -llog


#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, "ndk_test", __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO  , "ndk_test", __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN  , "ndk_test", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR  , "ndk_test", __VA_ARGS__)

extern "C"
JNIEXPORT jstring JNICALL
Java_com_llj_lib_jni_test_JniTest_stringFromJNI(JNIEnv *env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


jstring native_test5(JNIEnv *env) {
    return env->NewStringUTF("测试5");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_llj_lib_jni_test_JniTest_stringFromJNI2(JNIEnv *env, jobject /* this */) {
    return native_test5(env);
}


//替换java端的test2
void native_test2() {
    LOGI("native_test2");
}

//替换java端的test3
int native_test3() {
    return 190;
}

//替换java端的test3
bool native_test4() {
    return true;
}


//注册Java端的方法  以及本地相对应的方法
JNINativeMethod method[] = {{"test2", "()V", (void *) native_test2},
                            {"test3", "()I", (int *) native_test3},
                            {"test4", "()Z", (bool *) native_test4}
};


//注册相应的类以及方法
JNIEXPORT jint
registerNativeMeth(JNIEnv *env) {
    jclass cl = env->FindClass("com/llj/lib/jni/test/JniTest");
    if ((env->RegisterNatives(cl, method, sizeof(method) / sizeof(method[0]))) < 0) {
        return -1;
    }
    return 0;
}


//实现jni_onload 动态注册方法
JNIEXPORT jint
JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return -1;
    }
    if (registerNativeMeth(env) != JNI_OK) {//注册方法
        return -1;
    }
    return JNI_VERSION_1_4;//必须返回这个值
}






