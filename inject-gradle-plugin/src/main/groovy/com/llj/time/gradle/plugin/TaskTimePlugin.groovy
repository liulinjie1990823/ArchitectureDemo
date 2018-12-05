package com.llj.time.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class TaskTimePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
//        project.task('testTask') << {
//            println("Hello gradle plugin")
//        }
        project.gradle.addListener(new TimeListener())
    }
}