package com.llj.lib.jni.test2;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/27
 */
public class JniTest2 {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("helloworld2");

    }

    public native String stringFromJNI();


}
