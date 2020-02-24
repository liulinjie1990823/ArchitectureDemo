package com.llj.inject.gradle.plugin

class InjectPluginParams {
    //是否开启修改代码
    boolean enableModify = true
    boolean watchTimeConsume = false
    //是否打印日志
    boolean keepQuiet = false
    //
    boolean showHelp = true

    //首先通过targetPackages可以将范围限定在摸个包或者某个类中
    HashSet<String> targetPackages = []
    //需要修改生命周期的fragment
    HashSet<String> superFragments = []
}