package com.llj.inject.gradle.plugin

import com.llj.inject.gradle.plugin.util.Log
import com.llj.inject.gradle.plugin.util.DataHelper
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class InjectPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println ":applied plugin-code-inject"

        project.extensions.create('codeInjectConfig', InjectPluginParams)

        project.extensions.getByType(BaseExtension).registerTransform(new InjectTransform(project))

        initDir(project)

        project.afterEvaluate {
            Log.setQuiet(project.codeInjectConfig.keepQuiet);
            Log.setShowHelp(project.codeInjectConfig.showHelp);
            Log.logHelp();

            if (project.codeInjectConfig.watchTimeConsume) {
                Log.info "watchTimeConsume enabled"
                project.gradle.addListener(new TimeListener())
            } else {
                Log.info "watchTimeConsume disabled"
            }
        }
    }


    void initDir(Project project) {
        File pluginTmpDir = new File(project.buildDir, 'LazierTracker')
        if (!pluginTmpDir.exists()) {
            pluginTmpDir.mkdir()
        }
        DataHelper.ext.pluginTmpDir = pluginTmpDir
    }
}