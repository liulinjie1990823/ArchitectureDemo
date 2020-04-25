package com.llj.plugin.upload;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2020/4/25
 */
class ApkUploadExtensions {

  String  dingTalkAccessToken;
  String  gitLog;
  boolean testDingDing;

  NamedDomainObjectContainer<BuildTypeExtensions> buildTypes;

  public ApkUploadExtensions(Project project) {
    buildTypes = project.container(BuildTypeExtensions.class);
  }

  //创建内部Extension，名称为方法名 inner
  void buildTypes(Action<? super NamedDomainObjectContainer<BuildTypeExtensions>> action) {
    action.execute(buildTypes);
  }

}
