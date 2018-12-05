package com.llj.inject.gradle.plugin

public class InjectPluginParams {
    String pluginName = ''
    boolean enableModify = true
    boolean watchTimeConsume = false
    boolean keepQuiet = false
    boolean showHelp = true
    HashSet<String> targetPackages = []
}