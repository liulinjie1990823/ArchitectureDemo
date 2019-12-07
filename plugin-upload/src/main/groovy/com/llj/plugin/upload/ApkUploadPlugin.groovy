package com.llj.plugin.upload

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.BaseVariant
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovy.json.internal.LazyMap
import org.apache.commons.lang.StringUtils
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.protocol.HTTP
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

import java.nio.charset.Charset

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/21
 */
class ApkUploadPlugin implements Plugin<Project> {

    private static final String PGY_URL = "https://www.pgyer.com/"
    private static final String PGY_UPLOAD_PATH = "/apiv2/app/upload"

    private static final String DING_TALK_URL = "https://oapi.dingtalk.com/"
    private static final String DING_TALK_SEND_PATH = "/robot/send"

    @Override
    void apply(Project project) {

        ApkUploadExtensions upload = project.extensions.create("upload", ApkUploadExtensions, project)
        getAndroidVariants(project).all { variant ->
            variant.outputs.all { output ->
                String variantName = variant.name.capitalize()

                def apkFile = variant.outputs[0].outputFile.getAbsolutePath()
                println("apkFile:${apkFile}")

                //获得对应buildType的参数
                BuildTypeExtensions buildType = upload.buildTypes.findByName(variant.name)

                if (buildType != null) {

                    //上传本地apk
                    def uploadLocalApkToPgy = project.task("uploadLocal${variantName}ToPgy").doFirst {
                        uploadPgyApk(project, apkFile, upload, buildType, variantName)
                    }
                    uploadLocalApkToPgy.setGroup("uploadPgy")

                    //上传apk,apk会重新生成
                    def uploadApkToPgy = project.task("assemble${variantName}ToPgy").doFirst {
                        uploadPgyApk(project, apkFile, upload, buildType, variantName)
                    }
                    uploadApkToPgy.setGroup("uploadPgy")
                    //该任务会依赖assemble任务
                    uploadApkToPgy.dependsOn(project.tasks.getByName("assemble${variantName}"))
                }
            }
        }
    }

    private void uploadPgyApk(Project project, String apkFile, ApkUploadExtensions upload, BuildTypeExtensions buildType, String variantName) {
        println "----------------------------------"
        println("buildType:${buildType.toString()}")
        println("dingTalkAccessToken:" + upload.dingTalkAccessToken)
        println("gitLog:" + upload.gitLog)
        String branch = getGitBranch(project)
        String commit = getGitCommit(project, upload.gitLog)
        String[] split = commit.split("\\n")

        StringBuilder builder = new StringBuilder();
        for (String temp : split) {
            builder.append("- ").append(temp).append(" \\n ")
        }
        commit = builder.toString()
        println "----------------------------------"

        if (upload.testDingDing) {
            noticeDingTalk(project, upload, branch, commit, variantName, new LazyMap())
            return
        }

        def desc
        if (project.hasProperty("desc")) {
            desc = project.properties.get("desc")
        } else {
            if (split.size() > 0) {
                desc = buildType.toString() + " " + branch + " " + split[0]
            } else {
                desc = buildType.toString() + " " + branch
            }
        }
        def http = new HTTPBuilder(PGY_URL)
        http.request(POST, ContentType.JSON) { req ->
            uri.path = PGY_UPLOAD_PATH
            MultipartEntity entity = new MultipartEntity()
            entity.addPart("file", new FileBody(new File(apkFile)))
            entity.addPart("_api_key", new StringBody(buildType.pgyApiKey))
            entity.addPart("appKey", new StringBody(buildType.pgyAppKey))
            entity.addPart("buildPassword", new StringBody(buildType.password))
            entity.addPart("buildInstallType", StringUtils.isEmpty(buildType.installType) ? new StringBody("1") : new StringBody(buildType.installType))
            entity.addPart("buildUpdateDescription", new StringBody(desc, Charset.forName(HTTP.UTF_8)))
            req.entity = entity
            requestContentType = 'multipart/form-data'
            response.success = { resp, json ->
                assert resp.status == 200
                if (json.code == 0) {
                    println "pgy upload success"
                    println("json.data:" + json.data.toString())
                    try {
                        noticeDingTalk(project, upload, branch, commit, variantName, json.data)
                    } catch (Exception e) {
                        println e.toString()
                    }
                } else {
                    println "pgy upload fail, ${json.message}"
                }
            }
            response.failure = { resp ->
                println "pgyer upload fail, ${resp.status}"
            }
        }
    }

    /**
     * 获取分支信息
     * @param project
     * @return
     */
    private String getGitBranch(Project project) {
        def gitDir = new File("${new File("${project.getRootDir()}").getAbsolutePath()}/.git")

        if (!gitDir.isDirectory()) {
            return 'non-git-build'
        }

        def cmd = 'git symbolic-ref --short -q HEAD'
        String result = cmd.execute().text.trim()
        println "getGitBranch:" + result
        return result
    }

    /**
     * 获取git提交记录
     * @param project
     * @param commit
     * @return
     */
    private String getGitCommit(Project project, String commit) {
        def gitDir = new File("${new File("${project.getRootDir()}").getAbsolutePath()}/.git")

        if (!gitDir.isDirectory()) {
            return 'non-git-build'
        }

//        def cmd = "git log --pretty=format:%cn--%cd--%s --date=local --after=\"yesterday\" --grep=fix"
        def cmd = commit
        if (cmd == null || cmd.length() == 0) {
            return ""
        }
        String result = cmd.execute().text.trim()
        println "getGitCommit:" + result
        return result
    }

    /**
     * 钉钉通知
     * @param project
     * @param upload
     * @param branch
     * @param commit
     * @param variantName
     * @param data
     */
    private void noticeDingTalk(Project project, ApkUploadExtensions upload, String branch, String commit, String variantName, LazyMap data) {
        println "----------------------------------"
        try {
            def http = new HTTPBuilder(DING_TALK_URL)
            http.request(POST, JSON) { req ->
                uri.path = DING_TALK_SEND_PATH
                uri.query = ["access_token": upload.dingTalkAccessToken]
                body = "{\n" +
                        "    \"actionCard\": {\n" +
                        "        \"title\": \"Android：${data.buildName}\", \n" +
                        "        \"text\": \"![screenshot](${data.buildQRCodeURL}) \\n #### **Android**：${data.buildName} \\n\\n - build版本：${variantName} \\n - git分支：${branch} \\n - commit记录：\\n ${commit}- 版本信息：${data.buildVersion} \\n - 应用大小：${FileSizeUtil.getPrintSize(data.buildFileSize == null ? 0 : Long.valueOf(data.buildFileSize))} \\n - 更新时间：${data.buildUpdated} \\n - 更新内容：${data.buildUpdateDescription}\", \n" +
                        "        \"hideAvatar\": \"0\", \n" +
                        "        \"btnOrientation\": \"0\", \n" +
                        "        \"singleTitle\" : \"点击下载最新应用包\",\n" +
                        "        \"singleURL\" : \"https://www.pgyer.com/${data.buildShortcutUrl}\"\n" +
                        "    }, \n" +
                        "    \"msgtype\": \"actionCard\"\n" +
                        "}"
                response.success = { resp, json ->
                    assert resp.status == 200
                    if (json.errcode == 0) {
                        println "ding message send success"
                    } else {
                        println json.errmsg
                    }
                }
                response.failure = { resp ->
                    println "ding message send fail, ${resp.status}"
                }
            }
        } catch (Exception e) {
            println e.toString()
        }

    }

    private static final String sPluginMisConfiguredErrorMessage = "Plugin requires the 'android' plugin to be configured."

    /**
     * get android variant list of the project
     * @param project the compiling project
     * @return android variants
     */
    private static DomainObjectCollection<BaseVariant> getAndroidVariants(Project project) {

        if (project.getPlugins().hasPlugin(AppPlugin)) {
            return project.getPlugins().getPlugin(AppPlugin).extension.applicationVariants
        }
        throw new ProjectConfigurationException(sPluginMisConfiguredErrorMessage, null)
    }
}
