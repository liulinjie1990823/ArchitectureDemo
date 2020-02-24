package com.llj.inject.gradle.plugin

import com.android.annotations.NonNull
import com.android.annotations.Nullable
import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.llj.inject.gradle.plugin.util.DataHelper
import com.llj.inject.gradle.plugin.util.Log
import com.llj.inject.gradle.plugin.util.ModifyClassUtil
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class InjectTransform extends Transform {

    private AppExtension mAndroid
    private Project mProject
    private HashSet<String> mTargetPackages = []
    private HashSet<String> mSuperFragments = []

    InjectTransform(Project project) {
        mProject = project
    }

    /**
     * Transform的名字
     */
    @Override
    String getName() {
        //关系任务名和Transform文件夹下创建的文件夹
        //transformClassesWithCode-injectForDebug
        return "code-inject"
    }

    /**
     * 用于指明Transform的输入类型，可以作为输入过滤的手段
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 指Transform要操作内容的范围
     *
     *     EXTERNAL_LIBRARIES        只有外部库
     *     PROJECT                   只有项目内容
     *     PROJECT_LOCAL_DEPS        只有项目的本地依赖(本地jar)
     *     PROVIDED_ONLY             只提供本地或远程依赖项
     *     SUB_PROJECTS              只有子项目。
     *     SUB_PROJECTS_LOCAL_DEPS   只有子项目的本地依赖项(本地jar)。
     *     TESTED_CODE               由当前变量(包括依赖项)测试的代码
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 用于指明是否是增量构建
     * @return
     */
    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(
            @NonNull Context context,
            @NonNull Collection<TransformInput> inputs,
            @NonNull Collection<TransformInput> referencedInputs,
            @Nullable TransformOutputProvider outputProvider,
            boolean isIncremental) throws IOException, TransformException, InterruptedException {

        mAndroid = mProject.extensions.getByType(AppExtension)

        //应用包名
        String appPackageName = getAppPackageName()
//        if (appPackageName != null) {
//            mTargetPackages.add(appPackageName)
//        }


        //设置的需要扫描的包名
        HashSet<String> inputPackages = mProject.codeInjectConfig.targetPackages
        if (inputPackages != null) {
            mTargetPackages.addAll(inputPackages)
        }

        //fragment的父类，用来辨别superName，将.转换成/
        HashSet<String> superFragments = mProject.codeInjectConfig.superFragments
        if (superFragments != null) {
            Iterator<String> iterator = superFragments.iterator()
            while (iterator.hasNext()) {
                String item = iterator.next()
                if (item != null) {
                    item = item.replace(".", File.separator)
                    mSuperFragments.add(item)
                }
            }
        }

        //遍历输入文件
        inputs.each { TransformInput input ->
            //遍历jarInputs
            input.jarInputs.each { JarInput jarInput ->
                String destName = jarInput.file.name  //destName = "64"
                // 重名名输出文件,因为可能同名,会覆盖
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath).substring(0, 8) //hexName = "3687e7d3"
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }
                //获得输出文件
                //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/intermediates/transforms/code-inject/debug/64.jar"
                File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                def modifiedJar = null
                //判断jar包是否需要改动
                if (jarNeedModify(jarInput.file)) {
                    //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/intermediates/transforms/cc-register/debug/64.jar"
                    //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/tmp/transformClassesWithCode-injectForDebug"
                    modifiedJar = modifyJarFile(jarInput.file, context.getTemporaryDir())
                }
                //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/tmp/transformClassesWithCode-injectForDebug/3687e7d3_64.jar"
                if (modifiedJar == null) {
                    modifiedJar = jarInput.file;
                } else {
                    //保存到自己设置的文件夹
                    saveModifiedJarForCheck(modifiedJar);
                }
                FileUtils.copyFile(modifiedJar, dest);
            }
            //遍历directoryInputs
            input.directoryInputs.each { DirectoryInput directoryInput ->

                // /Users/liulinjie/GitHub/ArchitectureDemo/app/build/intermediates/transforms/code-inject/debug/183
                File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY);
                // /Users/liulinjie/GitHub/ArchitectureDemo/app/build/intermediates/transforms/cc-register/debug/183
                File dir = directoryInput.file
                if (dir) {
                    HashMap<String, File> modifyMap = new HashMap<>();
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File classFile ->
                        File modified = modifyClassFile(dir, classFile, context.getTemporaryDir());
                        //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/tmp/transformClassesWithCode-injectForDebug/com_llj_architecturedemo_ui_fragment_MineFragment_ViewBinding$7.class"
                        if (modified != null) {
                            //key为相对路径
                            //key = "/com/llj/architecturedemo/ui/fragment/MineFragment_ViewBinding$7.class"
                            modifyMap.put(classFile.absolutePath.replace(dir.absolutePath, ""), modified);
                        }
                    }
                    FileUtils.copyDirectory(directoryInput.file, dest);
                    modifyMap.entrySet().each { Map.Entry<String, File> en ->
                        //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/intermediates/transforms/code-inject/debug/183/com/llj/architecturedemo/ui/fragment/MineFragment_ViewBinding$7.class"
                        File target = new File(dest.absolutePath + en.getKey());
                        Log.info(target.getAbsolutePath());
                        if (target.exists()) {
                            target.delete();
                        }
                        //保存到自己设置的文件夹
                        saveModifiedJarForCheck(en.getValue());

                        //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/tmp/transformClassesWithCode-injectForDebug/com_llj_architecturedemo_ui_fragment_MineFragment_ViewBinding$7.class"
                        //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/intermediates/transforms/code-inject/debug/183/com/llj/architecturedemo/ui/fragment/MineFragment_ViewBinding$7.class"
                        FileUtils.copyFile(en.getValue(), target);
                        en.getValue().delete();
                    }
                }
            }
        }
    }

    private void saveModifiedJarForCheck(File optJar) {
        File dir = DataHelper.ext.pluginTmpDir;
        File checkJarFile = new File(dir, optJar.getName());
        if (checkJarFile.exists()) {
            checkJarFile.delete();
        }
        //optJar = {File@77798} "/Users/liulinjie/GitHub/ArchitectureDemo/app/build/tmp/transformClassesWithCode-injectForDebug/3687e7d3_64.jar"
        //checkJarFile = {File@77834} "/Users/liulinjie/GitHub/ArchitectureDemo/app/build/code-inject-modify/3687e7d3_64.jar"
        FileUtils.copyFile(optJar, checkJarFile);
    }

    /**
     * 植入代码
     * @param buildDir 是项目的build class目录,就是我们需要注入的class所在地
     * @param lib 这个是hackdex的目录,就是AntilazyLoad类的class文件所在地
     */
    private File modifyJarFile(File jarFile, File tempDir) {
        if (jarFile) {
            //设置输出到的jar
            def hexName = DigestUtils.md5Hex(jarFile.absolutePath).substring(0, 8);
            //"/Users/liulinjie/GitHub/ArchitectureDemo/app/build/tmp/transformClassesWithCodeInjectForDebug/3687e7d3_64.jar"
            def optJar = new File(tempDir, hexName + "_" + jarFile.name)
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJar));

            // 读取原jar
            def file = new JarFile(jarFile);
            Enumeration enumeration = file.entries();
            while (enumeration.hasMoreElements()) {
                //某个class文件
                JarEntry jarEntry = (JarEntry) enumeration.nextElement();
                InputStream inputStream = file.getInputStream(jarEntry);

                String entryName = jarEntry.getName();

                ZipEntry zipEntry = new ZipEntry(entryName);
                jarOutputStream.putNextEntry(zipEntry);

                byte[] modifiedClassBytes = null;
                byte[] sourceClassBytes = IOUtils.toByteArray(inputStream);
                if (entryName.endsWith(".class")) {
                    //转换成类名
                    String className = pathToClassname(entryName)
                    if (classNeedModify(className)) {
                        //判断该class是否需要修改
                        modifiedClassBytes = ModifyClassUtil.modifyClasses(className, sourceClassBytes, mSuperFragments)
                    }
                }

                jarOutputStream.write(modifiedClassBytes == null ? sourceClassBytes : modifiedClassBytes)
                jarOutputStream.closeEntry();
            }
            Log.info("${hexName} is modified");

            jarOutputStream.close();
            file.close();
            return optJar;
        }
        return null;
    }


    /**
     *
     * @param dir
     * @param classFile
     * @param tempDir
     * @return
     */
    private File modifyClassFile(File dir, File classFile, File tempDir) {
        File modified = null
        try {
            //com.llj.architecturedemo.ui.fragment.DialogTestFragmen
            String className = pathToClassname(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))
            byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile));

            if (classNeedModify(className)) {
                byte[] modifiedClassBytes = ModifyClassUtil.modifyClasses(className, sourceClassBytes, mSuperFragments)
                if (modifiedClassBytes) {
                    modified = new File(tempDir, className.replace('.', '_') + '.class')
                    ///Users/liulinjie/GitHub/ArchitectureDemo/app/build/tmp/transformClassesWithCode-injectForDebug/comlljarchitecturedemouifragmentDialogTestFragment.class
                    if (modified.exists()) {
                        modified.delete();
                    }
                    modified.createNewFile()
                    new FileOutputStream(modified).write(modifiedClassBytes)
                }
            }
        } catch (Exception e) {
        }
        return modified

    }

    /**
     * 只扫描特定包下的类
     * @param className 形如 mAndroid.app.Fragment 的类名
     * @return
     */
    private boolean classNeedModify(String className) {
        if ((!mProject.codeInjectConfig.enableModify) || mTargetPackages == null) {
            return false
        }
        Iterator<String> iterator = mTargetPackages.iterator()
        // 注意，闭包里的return语句相当于continue，不会跳出遍历，故用while或for
        while (iterator.hasNext()) {
            String packageName = iterator.next()
            //判断类名中是否包含指定的包名，true表示是需要修改的类
            if (className.contains(packageName)) {
                //在指定的包名mTargetPackages中，且不是R,BuildConfig文件
                return (!className.contains("R\$") && !className.endsWith("R") && !className.endsWith("BuildConfig"))
            }
        }
        return false
    }

    /**
     * 该jar文件是否包含需要修改的类
     * @param jarFile
     * @return
     */
    private boolean jarNeedModify(File jarFile) {
        boolean modified = false

        if (mTargetPackages == null || mTargetPackages.size() <= 0) {
            return modified
        }
        if (jarFile) {
            //读取原jar
            def file = new JarFile(jarFile)
            Enumeration enumeration = file.entries()
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                if (!entryName.endsWith(".class")) {
                    continue
                }
                //entryName:com/billy/cc/core/component/BaseForwardInterceptor.class
                //className:com.billy.cc.core.component.BaseForwardInterceptor
                String className = pathToClassname(entryName)
                if (classNeedModify(className)) {
                    modified = true
                    break
                }
            }
            file.close()
        }
        return modified
    }

    /**
     * 获取应用程序包名
     * 读取manifest下的（package）tag
     * @return "com.llj.architecturedemo"
     */
    private String getAppPackageName() {
        String packageName = null
        try {
            def manifestFile = mAndroid.sourceSets.main.manifest.srcFile
            Log.info("XmlParser manifestFile: " + manifestFile)
            packageName = new XmlParser().parse(manifestFile).attribute('package')
            Log.info("XmlParser packageName: " + packageName)
        } catch (Exception e) {
            Log.info("XmlParser Exception: " + e.getMessage())
        }
        return packageName
    }

    private String pathToClassname(String entryName) {
        return entryName.replace(File.separator, ".").replace(".class", "")
    }
}