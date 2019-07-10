##### app

> 任务分为以下几种：
- generate
- check
- merge
- process
- compile
- transformClasses
- transformNativeLibs
- transformResources
- transformDexArchive

> 执行./gradlew app:assembleDebug
- app:buildInfoDebugLoader	
- app:preBuild 创建文件夹
- app:preDebugBuild	 创建文件夹
- app:generateDebugResValues 创建文件夹
- app:compileDebugRenderscript
- app:compileDebugAidl
- app:generateDebugResources 创建文件夹
- app:checkDebugManifest  
- app:generateDebugBuildConfig
- app:prepareLintJar
- app:mainApkListPersistenceDebug	27ms
- app:createDebugCompatibleScreenManifests	38ms
- app:processDebugManifest（合并Manifest）	1s 582ms
- app:mergeDebugResources	17s 871ms
```
解压所有的aar包输出到app/build/intermediates/exploded-aar，并且把所有的资源文件合并到app/build/intermediates/res/merged/debug目录里
```
- app:splitsDiscoveryTaskDebug	61ms
- app:mergeDebugShaders	20ms
- app:compileDebugShaders	128ms
- app:generateDebugAssets	4ms  
- app:validateSigningDebug	6ms
- app:signingConfigWriterDebug	6ms
- app:extractTryWithResourcesSupportJarDebug	6ms
- app:compileDebugNdk
- app:mergeDebugJniLibFolders	8ms
- app:processDebugJavaRes
- app:fastDeployDebugExtractor	48ms
- app:generateDebugInstantRunAppInfo	25ms
- app:processDebugResources	2s 858ms
- app:generateDebugSources
- app:processInstantRunDebugResourcesApk	15ms
- app:checkManifestChangesDebug	6ms
- app:mergeDebugAssets	50ms
- app:packageInstantRunResourcesDebug	
- app:javaPreCompileDebug	47ms
- app:compileDebugJavaWithJavac	java文件编译成class
- app:transformClassesWithStackFramesFixerForDebug	568ms
- app:transformClassesWithDesugarForDebug	8s 32ms
- app:transformClassesWithProfilers-transformForDebug 混淆
- app:transformClassesWithExtractJarsForDebug	278ms
- app:transformClassesWithInstantRunVerifierForDebug	799ms
- app:transformClassesWithDependencyCheckerForDebug	138ms
- app:transformNativeLibsWithMergeJniLibsForDebug	1s 496ms
- app:transformResourcesWithMergeJavaResForDebug	4s 166ms
- app:transformNativeLibsAndResourcesWithJavaResourcesVerifierForDebug	145ms
- app:transformClassesWithInstantRunForDebug	3s 607ms
- app:transformClassesEnhancedWithInstantReloadDexForDebug	6ms
- app:incrementalDebugTasks
- app:preColdswapDebug	1ms
- app:transformClassesWithInstantRunSlicerForDebug	1s 338ms
- app:transformClassesWithDexBuilderForDebug	20s 782ms
- app:transformDexArchiveWithExternalLibsDexMergerForDebug	4s 84ms
- app:transformDexArchiveWithDexMergerForDebug	4s 413ms
- app:transformDexWithInstantRunDependenciesApkForDebug	1s 299ms
- app:transformDexWithInstantRunSlicesApkForDebug	2s 351ms
- app:transformNativeLibsWithStripDebugSymbolForDebug	951ms
- app:packageDebug	5s 97ms
- app:buildInfoGeneratorDebug	11ms
- app:compileDebugSources	3ms
- app:assembleDebug	1ms