package com.llj.plugin.upload

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

class ApkUploadExtensions {
    String dingTalkAccessToken
    String gitLog
    boolean testDingDing
    NamedDomainObjectContainer<BuildTypeExtensions> buildTypes

    ApkUploadExtensions(Project project) {
        buildTypes = project.container(BuildTypeExtensions)
    }

    //创建内部Extension，名称为方法名 inner
    void buildTypes(Action<? super NamedDomainObjectContainer<BuildTypeExtensions>> action) {
        action.execute(buildTypes)
    }
}