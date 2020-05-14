package com.llj.lib.jni.test;

/**
 * ArchitectureDemo. describe: author llj date 2019/3/27
 */
public class JniTest {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("helloworld");

    }

    public native String stringFromJNI();

    public native String stringFromJNI2();

    //声明为本地方法
    public native void test2();

    public native int test3();

    public native boolean test4();
}
