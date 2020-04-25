package com.llj.plugin.upload;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.println;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.AppPlugin;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;
import org.gradle.api.Action;
import org.gradle.api.DomainObjectSet;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * ArchitectureDemo. describe:
 *
 * @author llj
 * @date 2020/4/25
 */
class ApkUploadPlugin implements Plugin<Project> {

  private static final String PGY_URL         = "https://www.pgyer.com/";
  private static final String PGY_UPLOAD_PATH = "/apiv2/app/upload";

  private static final String DING_TALK_URL       = "https://oapi.dingtalk.com/";
  private static final String DING_TALK_SEND_PATH = "/robot/send";

  @Override
  public void apply(Project project) {
    ApkUploadExtensions upload = project.getExtensions()
        .create("upload", ApkUploadExtensions.class, project);
    getAndroidVariants(project).all(new Action<ApplicationVariant>() {
      @Override
      public void execute(ApplicationVariant variant) {
        String variantName = variant.getName();
        println("variantName:" + variantName);
        variant.getOutputs().all(new Action<BaseVariantOutput>() {
          @Override
          public void execute(BaseVariantOutput baseVariantOutput) {
            String apkFile = baseVariantOutput.getOutputFile().getAbsolutePath();
            println("apkFile:${apkFile}");

            //获得对应buildType的参数
            final BuildTypeExtensions buildType = upload.buildTypes.findByName(variantName);

            if (buildType != null) {
              //上传本地apk
              Task uploadLocalApkToPgy = project.task("uploadLocal${variantName}ToPgy")
                  .doFirst(new Action<Task>() {
                    @Override
                    public void execute(Task task) {
                      uploadPgyApk(project, apkFile, upload, buildType, variantName);
                    }
                  });
              uploadLocalApkToPgy.setGroup("uploadPgy");

              //上传apk,apk会重新生成
              Task uploadApkToPgy = project.task("assemble${variantName}ToPgy").doFirst(
                  new Action<Task>() {
                    @Override
                    public void execute(Task task) {
                      uploadPgyApk(project, apkFile, upload, buildType, variantName);
                    }
                  });
              uploadApkToPgy.setGroup("uploadPgy");
              //该任务会依赖assemble任务
              uploadApkToPgy.dependsOn(project.getTasks().getByName("assemble${variantName}"));
            }
          }
        });


      }
    });

  }

  public void uploadPgyApk(Project project, String apkFile, ApkUploadExtensions upload,
      BuildTypeExtensions buildType, String variantName) {
  }

  private static DomainObjectSet<ApplicationVariant> getAndroidVariants(Project project) {

    if (project.getPlugins().hasPlugin(AppPlugin.class)) {
      return ((AppExtension) project.getExtensions().getByName("android")).getApplicationVariants();
    }
    throw new GradleException("Plugin requires the android plugin to be configured");
  }
}
