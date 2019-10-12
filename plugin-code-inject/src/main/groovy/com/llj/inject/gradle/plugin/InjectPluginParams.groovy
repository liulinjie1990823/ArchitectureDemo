package com.llj.inject.gradle.plugin

class InjectPluginParams {
    boolean enableModify = true
    boolean watchTimeConsume = false
    boolean keepQuiet = false
    boolean showHelp = true
    HashSet<String> targetPackages = []
    HashSet<String> superFragments = []
}