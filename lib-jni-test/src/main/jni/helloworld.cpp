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


//按照命名规则注册
extern "C"
JNIEXPORT jstring JNICALL
Java_com_llj_lib_jni_test_JniTest_stringFromJNI(JNIEnv *env, jobject) {
  std::string hello = "Hello from C++";
  return env->NewStringUTF(hello.c_str());
}


jstring native_test5(JNIEnv *env) {
  return env->NewStringUTF("测试5");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_llj_lib_jni_test_JniTest_stringFromJNI2(JNIEnv *env, jobject) {
  return native_test5(env);
}


//替换java端的test2
extern "C"
void test2(JNIEnv *env, jobject) {
  LOGI("native_test2");
}

//替换java端的test3
extern "C"
jint test3(JNIEnv *env, jobject) {
  return 190;
}

//替换java端的test3
extern "C"
jboolean test4(JNIEnv *env, jobject) {
  return static_cast<jboolean>(true);
}


//java类
static const char *classPathName = "com/llj/lib/jni/test/JniTest";
//注册Java端的方法  以及本地相对应的方法
static JNINativeMethod methods[] = {{"test2", "()V", (void *) test2},
                                    {"test3", "()I", (int *) test3},
                                    {"test4", "()Z", (bool *) test4}
};

//实现jni_onload 动态注册方法
//可以
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
  JNIEnv *env = NULL;
  // 1.获取JNI环境对象
  if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
    return JNI_ERR;
  }

  //2.查找类
  jclass cl = env->FindClass(classPathName);
  if (cl == nullptr) {
    return JNI_ERR;
  }

  //3.注册方法
  int rc = env->RegisterNatives(cl, methods, sizeof(methods) / sizeof(JNINativeMethod));
  if (rc != JNI_OK) {
    return rc;
  }

  return JNI_VERSION_1_6;//必须返回这个值
}






