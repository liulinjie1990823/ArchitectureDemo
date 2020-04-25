package com.llj.plugin.upload

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

class ApkUploadExtensions1 {
    String dingTalkAccessToken
    String gitLog
    boolean testDingDing
    NamedDomainObjectContainer<BuildTypeExtensions1> buildTypes

    ApkUploadExtensions1(Project project) {
        buildTypes = project.container(BuildTypeExtensions1)
    }

    //创建内部Extension，名称为方法名 inner
    void buildTypes(Action<? super NamedDomainObjectContainer<BuildTypeExtensions1>> action) {
        action.execute(buildTypes)
    }
}