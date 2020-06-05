package com.llj.plugin.upload

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.google.gson.Gson
import groovyx.net.http.*
import groovyx.net.http.util.SslUtils
import okhttp3.OkHttpClient
import org.gradle.api.*

import java.util.function.BiConsumer
import java.util.function.Consumer

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/21
 */
class ApkUploadPlugin1 implements Plugin<Project> {

    private static final String PGY_URL = "https://www.pgyer.com/"
    private static final String PGY_UPLOAD_PATH = "/apiv2/app/upload"

    private static final String DING_TALK_URL = "https://oapi.dingtalk.com/"
    private static final String DING_TALK_SEND_PATH = "/robot/send"

    @Override
    void apply(Project project) {

        ApkUploadExtensions1 upload = project.extensions.create("upload", ApkUploadExtensions1, project)

        getAndroidVariants(project).all { variant ->
            variant.outputs.all { output ->
                String variantName = variant.name.capitalize()

                println("variantName:${variantName}")

                String apkFile = variant.outputs[0].outputFile.getAbsolutePath()
                println("apkFile:${apkFile}")

                //获得对应buildType的参数
                BuildTypeExtensions1 buildType = upload.buildTypes.findByName(variant.name)

                if (buildType != null) {

                    //上传本地apk
//                    def uploadLocalApkToPgy = project.task("uploadLocal${variantName}ToPgy").doFirst {
//                        uploadPgyApk(project, apkFile, upload, buildType, variantName)
//                    }
                    def uploadLocalApkToPgy = project.task("uploadLocal${variantName}ToPgy").doFirst(new Action<Task>() {
                        @Override
                        void execute(Task task) {
                            uploadPgyApk(project, apkFile, upload, buildType, variantName)
                        }
                    })
                    uploadLocalApkToPgy.setGroup("uploadPgy")

                    //上传apk,apk会重新生成
//                    def uploadApkToPgy = project.task("assemble${variantName}ToPgy").doFirst {
//                        uploadPgyApk(project, apkFile, upload, buildType, variantName)
//                    }
                    def uploadApkToPgy = project.task("assemble${variantName}ToPgy").doFirst(new Action<Task>() {
                        @Override
                        void execute(Task task) {
                            uploadPgyApk(project, apkFile, upload, buildType, variantName)
                        }
                    })
                    uploadApkToPgy.setGroup("uploadPgy")
                    //该任务会依赖assemble任务
                    uploadApkToPgy.dependsOn(project.tasks.getByName("assemble${variantName}"))
                }
            }
        }
    }

    public void uploadPgyApk(Project project, String apkFile, ApkUploadExtensions1 upload, BuildTypeExtensions1 buildType, String variantName) {
        println "----------------------------------" + "uploadPgyApk begin" + "----------------------------------"
        println("buildType:${buildType.toString()}")
        println("dingTalkAccessToken:" + upload.dingTalkAccessToken)
        println("gitLog:" + upload.gitLog)
        String branch = getGitBranch(project)
        String commit = getGitCommit(project, upload.gitLog)
        String commitAuthor = getGitCommitAuthor(project,)
        String[] split = commit.split("\\n")

        StringBuilder builder = new StringBuilder();
        for (String temp : split) {
            builder.append("- ").append(temp).append(" \\n ")
        }
        commit = builder.toString()


        String desc = project.properties.get("pgyBuildDesc")
        if (desc == null || desc.isEmpty()) {
            desc = branch
        }

        println("desc:" + desc)

        if (upload.testDingDing) {
            noticeDingTalk(project, upload, commitAuthor, branch, commit, variantName, new PgyVo.DataVo())
            return
        }

        HttpBuilder http = OkHttpBuilder.configure {
            client.clientCustomizer(new Consumer<OkHttpClient.Builder>() {
                @Override
                void accept(OkHttpClient.Builder builder1) {
                    builder1.readTimeout(60000, java.util.concurrent.TimeUnit.MILLISECONDS)
                    builder1.writeTimeout(60000, java.util.concurrent.TimeUnit.MILLISECONDS)
                }
            })
            SslUtils.ignoreSslIssues(execution)
            request.uri = PGY_URL
            request.uri.path = PGY_UPLOAD_PATH
            request.contentType = ContentTypes.MULTIPART_FORMDATA[0]
            request.body = MultipartContent.multipart {
                field "_api_key", ContentTypes.TEXT[0], buildType.pgyApiKey
                field "appKey", ContentTypes.TEXT[0], buildType.pgyAppKey
                field "buildPassword", ContentTypes.TEXT[0], buildType.password
                field "buildInstallType", ContentTypes.TEXT[0], isEmpty(buildType.installType) ? "1" : buildType.installType
                field "buildUpdateDescription", ContentTypes.TEXT[0], desc
                part 'file', new File(apkFile).name, ContentTypes.BINARY[0], new File(apkFile)
            }
            request.encoder(ContentTypes.MULTIPART_FORMDATA[0], new BiConsumer<ChainedHttpConfig, ToServer>() {
                @Override
                void accept(ChainedHttpConfig chainedHttpConfig, ToServer toServer) {
                    OkHttpEncoders.multipart(chainedHttpConfig, toServer)
                }
            })
        }

        http.post {
            response.parser(ContentTypes.JSON[0]) { ChainedHttpConfig cfg, FromServer fromServer ->
                println "----------------------------------" + "uploadPgyApk response" + "----------------------------------"

                assert fromServer.statusCode == 200

                String text = fromServer.inputStream.text
                println("pgy.data:" + text)
                PgyVo json = new Gson().fromJson(text, PgyVo.class)

                if (json.code == 0) {
                    println "pgy upload success"
                    try {
                        noticeDingTalk(project, upload, commitAuthor, branch, commit, variantName, json.data)
                    } catch (Exception e) {
                        println e.toString()
                    }
                } else {
                    println "pgy upload fail, ${json.message}"
                }
            }
            response.exception { t ->
                t.printStackTrace()
                println "pgy upload exception"
            }
        }

//        def http = new HttpBuilder(PGY_URL)
//        http.request(POST, ContentType.JSON) { req ->
//            uri.path = PGY_UPLOAD_PATH
//            MultipartEntity entity = new MultipartEntity()
//            entity.addPart("file", new FileBody(new File(apkFile)))
//            entity.addPart("_api_key", new StringBody(buildType.pgyApiKey))
//            entity.addPart("appKey", new StringBody(buildType.pgyAppKey))
//            entity.addPart("buildPassword", new StringBody(buildType.password))
//            entity.addPart("buildInstallType", StringUtils.isEmpty(buildType.installType) ? new StringBody("1") : new StringBody(buildType.installType))
//            entity.addPart("buildUpdateDescription", new StringBody(desc, Charset.forName(HTTP.UTF_8)))
//            req.entity = entity
//            requestContentType = 'multipart/form-data'
//            response.success = { resp, json ->
//                assert resp.status == 200
//                if (json.code == 0) {
//                    println "pgy upload success"
//                    println("json.data:" + json.data.toString())
//                    try {
//                        noticeDingTalk(project, upload, branch, commit, variantName, json.data)
//                    } catch (Exception e) {
//                        println e.toString()
//                    }
//                } else {
//                    println "pgy upload fail, ${json.message}"
//                }
//            }
//            response.failure = { resp ->
//                println "pgyer upload fail, ${resp.status}"
//            }
//        }
    }


    public class PgyVo {
        public int code;
        public String message;

        public DataVo data;

        public class DataVo {

            public String buildName;//应用名称
            public String buildQRCodeURL;//二维码
            public String buildBuildVersion;//编译版本
            public String buildVersion;//app版本号
            public String buildUpdated;//更新时间
            public String buildFileSize;//App 文件大小
            public String buildUpdateDescription;//应用更新说明
            public String buildShortcutUrl;//应用更新说明

        }
    }

    private boolean isEmpty(String string) {
        return string == null || string.length() == 0

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

    private String getGitCommitAuthor(Project project) {
        def gitDir = new File("${new File("${project.getRootDir()}").getAbsolutePath()}/.git")

        if (!gitDir.isDirectory()) {
            return 'non-git-build'
        }

        def cmd = "git config user.name"
        if (cmd == null || cmd.length() == 0) {
            return ""
        }
        String result = cmd.execute().text.trim()
        println "getGitCommitAuthor:" + result
        return result
    }


    public class DingDingVo {
        public int errcode;
        public String errmsg;

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
    private void noticeDingTalk(Project project, ApkUploadExtensions1 upload, String commitAuthor, String branch, String commit, String variantName, PgyVo.DataVo data) {
        println "----------------------------------" + "noticeDingTalk begin" + "----------------------------------"
        try {
            HttpBuilder http = OkHttpBuilder.configure {
                SslUtils.ignoreSslIssues(execution)
                request.uri = DING_TALK_URL
                request.uri.path = DING_TALK_SEND_PATH
                request.uri.query = ["access_token": upload.dingTalkAccessToken]
                request.contentType = ContentTypes.JSON[0]
                request.body = "{\n" +
                        "    \"actionCard\": {\n" +
                        "        \"title\": \"Android：${data.buildName}\", \n" +
                        "        \"text\": \"![screenshot](${data.buildQRCodeURL}) \\n #### **Android**：${data.buildName} \\n\\n - buildAuthor：${commitAuthor} \\n - buildType：${variantName} \\n - pgyBuildVersion：${data.buildBuildVersion} \\n - git分支：${branch} \\n - commit记录：\\n ${commit}- 版本信息：${data.buildVersion} \\n - 应用大小：${FileSizeUtil.getPrintSize(data.buildFileSize == null ? 0 : Long.valueOf(data.buildFileSize))} \\n - 更新时间：${data.buildUpdated} \\n - 更新内容：${data.buildUpdateDescription}\", \n" +
                        "        \"hideAvatar\": \"0\", \n" +
                        "        \"btnOrientation\": \"0\", \n" +
                        "        \"singleTitle\" : \"点击下载最新应用包\",\n" +
                        "        \"singleURL\" : \"https://www.pgyer.com/${data.buildShortcutUrl}\"\n" +
                        "    }, \n" +
                        "    \"msgtype\": \"actionCard\"\n" +
                        "}"
            }
            http.post {
                response.parser(ContentTypes.JSON[0]) { ChainedHttpConfig cfg, FromServer fromServer ->
                    println "----------------------------------" + "noticeDingTalk response" + "----------------------------------"

                    assert fromServer.statusCode == 200

                    String text = fromServer.inputStream.text
                    println("ding.data:" + text)
                    DingDingVo json = new Gson().fromJson(text, DingDingVo.class)

                    if (json.errcode == 0) {
                        println "ding message send success"
                    } else {
                        println "ding message send fail, ${json.errmsg}"
                    }
                }
                response.exception { t ->
                    t.printStackTrace()
                    println "ding message send fail"
                }
            }
//            def http = new HTTPBuilder(DING_TALK_URL)
//            http.request(POST, JSON) { req ->
//                uri.path = DING_TALK_SEND_PATH
//                uri.query = ["access_token": upload.dingTalkAccessToken]
//                body = "{\n" +
//                        "    \"actionCard\": {\n" +
//                        "        \"title\": \"Android：${data.buildName}\", \n" +
//                        "        \"text\": \"![screenshot](${data.buildQRCodeURL}) \\n #### **Android**：${data.buildName} \\n\\n - build版本：${variantName} \\n - 蒲公英build版本：${data.buildBuildVersion} \\n - git分支：${branch} \\n - commit记录：\\n ${commit}- 版本信息：${data.buildVersion} \\n - 应用大小：${FileSizeUtil.getPrintSize(data.buildFileSize == null ? 0 : Long.valueOf(data.buildFileSize))} \\n - 更新时间：${data.buildUpdated} \\n - 更新内容：${data.buildUpdateDescription}\", \n" +
//                        "        \"hideAvatar\": \"0\", \n" +
//                        "        \"btnOrientation\": \"0\", \n" +
//                        "        \"singleTitle\" : \"点击下载最新应用包\",\n" +
//                        "        \"singleURL\" : \"https://www.pgyer.com/${data.buildShortcutUrl}\"\n" +
//                        "    }, \n" +
//                        "    \"msgtype\": \"actionCard\"\n" +
//                        "}"
//                response.success = { resp, json ->
//                    assert resp.status == 200
//                    if (json.errcode == 0) {
//                        println "ding message send success"
//                    } else {
//                        println json.errmsg
//                    }
//                }
//                response.failure = { resp ->
//                    println "ding message send fail, ${resp.status}"
//                }
//            }
        } catch (Exception e) {
            println e.toString()
        }

    }


    /**
     * get android variant list of the project
     * @param project the compiling project
     * @return android variants
     */
    private static DomainObjectSet<ApplicationVariant> getAndroidVariants(Project project) {

        if (project.getPlugins().hasPlugin(AppPlugin.class)) {
            return ((AppExtension) project.getExtensions().getByName("android")).getApplicationVariants();
        }
        throw new GradleException("Plugin requires the android plugin to be configured");
    }
}
