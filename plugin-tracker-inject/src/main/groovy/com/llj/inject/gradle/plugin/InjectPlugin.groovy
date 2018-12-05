package com.llj.inject.gradle.plugin

import com.llj.inject.gradle.plugin.util.Log
import com.llj.inject.gradle.plugin.util.DataHelper
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class InjectPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println ":applied LazierTracker"

        project.extensions.create('codelessConfig', InjectPluginParams)

        registerTransform(project)

        initDir(project)
        project.afterEvaluate {
            Log.setQuiet(project.codelessConfig.keepQuiet);
            Log.setShowHelp(project.codelessConfig.showHelp);
            Log.logHelp();
            if (project.codelessConfig.watchTimeConsume) {
                Log.info "watchTimeConsume enabled"
                project.gradle.addListener(new TimeListener())
            } else {
                Log.info "watchTimeConsume disabled"
            }
        }
    }


    def static registerTransform(Project project) {
        def android = project.extensions.getByType(BaseExtension)
        InjectTransform transform = new InjectTransform(project)
        android.registerTransform(transform)
    }

    void initDir(Project project) {
        File pluginTmpDir = new File(project.buildDir, 'LazierTracker')
        if (!pluginTmpDir.exists()) {
            pluginTmpDir.mkdir()
        }
        DataHelper.ext.pluginTmpDir = pluginTmpDir
    }
}