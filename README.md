# 什么是热修复（热更新，热补丁）？
  一句话概括，动态地修复和更新App的行为
# 热修复有什么好处？
  用户下载了我们的app，如果出现bug或者我们要修改某个功能，我们就要重新编译一个版本让用户覆盖安装才能解决问题，不过这份成本非常高，我们首先要发一个版本，其次用户还得重新安装下载apk，这个过程可能会使用户流失掉，热修复，可以在用户神不知鬼不觉的情况下就完成bug的修复和新功能的实现
<!--more-->
# 有了热修复就可以一劳永逸吗？
  目前市面上的所有热修复框架都不支持四大组件的生成和AndroidManifest文件的编写，如果有重大版本更新或者新页面，还是需要走正常发布流程。而bug的修复和一些资源的替换，才可以使用热修复技术。发布正式版本和发布热修复版本同样需要经过测试，因为各种热修复技术都会有兼容性的问题。
# 接入准备工作
-   了解android dex文件和classLoader原理（热修复技术的底层核心实现，自行google了解）
-   了解当前市面上比较流行的几种热修复技术以及技术选型

|  | Tinker | QZone |AndFix |Robust |
| :------:| :------: | :------: |:------: |:------: |
| 类替换	 | yes | yes |no |no |
| So替换     | yes | no |no |no |
| 资源替换	 | yes | yes |no |no |
| 全平台支持 | yes | yes |no |yes |
| 即时生效	 | no | no |yes |yes |
| 性能损耗		 | 较小	 | 较大	 |较小	 |较小	 |
| 补丁包大小			 | 较小	 | 较大	 |一般	 |一般	 |
| 开发透明		 | yes | yes |no |no |
| 复杂度		 | 较低 | 较低 |复杂 |复杂 |
| Rom体积			 | Dalvik较大	 | 较小 |较小 |较小 |
| 成功率				 | 较高	 | 较高 |一般 |最高 |


因为AndFix https://github.com/alibaba/AndFix 已经三年多没更新，所以我们首先排除这个，然后因为我们经常要用到字符串更改之类的需求，Robust没有资源替换，所以排除这个，剩下Tinker和QZone，我们对比发现Tinker性能损耗和补丁包大小都比较小，所以我们选择[Tinker](https://github.com/Tencent/tinker)为我们的热修复基础。

  **方案对比以及技术选型需要主要的地方**
- 1.我们的需求是什么，需求是衡量一切的标准。
- 2.满足需求的情况下哪个学习成本最低。
- 3.学习成本一样的情况下，优先选择大公司的方案)。

# Tinker已知的问题
由于原理与系统限制，Tinker有以下已知问题：
1. Tinker不支持修改AndroidManifest.xml，Tinker不支持新增四大组件(1.9.0支持新增非export的Activity)；
2. 由于Google Play的开发者条款限制，不建议在GP渠道动态更新代码；
3. 在Android N上，补丁对应用启动时间有轻微的影响；
4. 不支持部分三星android-21机型，加载补丁时会主动抛出"TinkerRuntimeException:checkDexInstall failed"；
5. 对于资源替换，不支持修改remoteView。例如transition动画，notification icon以及桌面图标。


# 什么是TinkerPatch
  Tinker 需要使用者有一个后台可以下发和管理补丁包，并且需要处理传输安全等部署工作，TinkerPatch 平台帮你做了这些工作，提供了补丁后台托管，版本管理，保证传输安全等功能，让你无需搭建一个后台，无需关心部署操作，只需引入一个 SDK 即可立即使用 Tinker。

此外，通过深入研究 Tinker 源码，TinkerTinkerPatch 平台在 Tinker的基础上加入了以下特性：

-   一键傻瓜式接入；无需理解复杂的热修复原理，一行代码即可接入热修复。实现了自动反射 Appliction 与 Library，使用者无需对自己的项目做任何的改动；
-   补丁管理；实现了热补丁的版本管理，补丁的自动重试与异常时自动回退等功能。同时我们可以简单实现条件下发补丁，在出现异常情况时，我们也可以快速回滚补丁；
-   编译优化；简化了 Tinker 的编译复杂度，实现了备份路径选择，功能开关等功能。
# 如何接入TinkerPatch
1. 添加 gradle 插件依赖
-    在项目根目录的gradle.properties（没有则新建一个）填写以下代码。目的是方便管理Tinker和TinkerPatch的版本。
     
```
TINKER_VERSION=1.9.8
TINKERPATCH_VERSION=1.2.8
```
-    在项目根目录的build.gradle中加入
```
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.0'
        classpath "com.tinkerpatch.sdk:tinkerpatch-gradle-plugin:${TINKERPATCH_VERSION}"
    }
}
```

2. 集成 TinkerPatch SDK,在app moudle下的build.gradle添加以下依赖。注意在com.android.tools.build:gradle 3.0 以下版本implementation改为compile,而compileOnly改为provided等...
```
    //若使用annotation需要单独引用,对于tinker的其他库都无需再引用
    annotationProcessor("com.tinkerpatch.tinker:tinker-android-anno:${TINKER_VERSION}") {
        changing = true
    }
    compileOnly("com.tinkerpatch.tinker:tinker-android-anno:${TINKER_VERSION}") { changing = true }
    implementation("com.tinkerpatch.sdk:tinkerpatch-android-sdk:${TINKERPATCH_VERSION}") {
        changing = true
    }
```

3.TinkerPatch 相关的配置，我们把TinkerPatch的配置全部写在一个文件中，新建一个文件名为tinkerpatch.gradle （和app moudle 中 build.gradle同级）

```
apply plugin: 'tinkerpatch-support'

/**
 * TODO: 请按自己的需求修改为适应自己工程的参数
 */
def bakPath = file("${buildDir}/bakApk/")
def baseInfo = "app-1.0.0-0727-09-51-05"
def variantName = "release"

/**
 * 对于插件各参数的详细解析请参考
 * http://tinkerpatch.com/Docs/SDK
 */
tinkerpatchSupport {
    /** 可以在debug的时候关闭 tinkerPatch **/
    /** 当disable tinker的时候需要添加multiDexKeepProguard和proguardFiles,
        这些配置文件本身由tinkerPatch的插件自动添加，当你disable后需要手动添加
        你可以copy本示例中的proguardRules.pro和tinkerMultidexKeep.pro,
        需要你手动修改'tinker.sample.android.app'本示例的包名为你自己的包名, com.xxx前缀的包名不用修改
     **/
    tinkerEnable = true
    reflectApplication = true
    /**
     * 是否开启加固模式，只能在APK将要进行加固时使用，否则会patch失败。
     * 如果只在某个渠道使用了加固，可使用多flavors配置
     **/
    protectedApp = true
    /**
     * 实验功能
     * 补丁是否支持新增 Activity (新增Activity的exported属性必须为false)
     **/
    supportComponent = true

    autoBackupApkPath = "${bakPath}"

    appKey = "4ca6340061006805"

    /** 注意: 若发布新的全量包, appVersion一定要更新 **/
    appVersion = "1.0.0"

    def pathPrefix = "${bakPath}/${baseInfo}/${variantName}/"
    def name = "${project.name}-${variantName}"

    baseApkFile = "${pathPrefix}/${name}.apk"
    baseProguardMappingFile = "${pathPrefix}/${name}-mapping.txt"
    baseResourceRFile = "${pathPrefix}/${name}-R.txt"

    /**
     *  若有编译多flavors需求, 可以参照： https://github.com/TinkerPatch/tinkerpatch-flavors-sample
     *  注意: 除非你不同的flavor代码是不一样的,不然建议采用zip comment或者文件方式生成渠道信息（相关工具：walle 或者 packer-ng）
     **/
}

/**
 * 用于用户在代码中判断tinkerPatch是否被使能
 */
android {
    defaultConfig {
        buildConfigField "boolean", "TINKER_ENABLE", "${tinkerpatchSupport.tinkerEnable}"
    }
}

/**
 * 一般来说,我们无需对下面的参数做任何的修改
 * 对于各参数的详细介绍请参考:
 * https://github.com/Tencent/tinker/wiki/Tinker-%E6%8E%A5%E5%85%A5%E6%8C%87%E5%8D%97
 */
tinkerPatch {
    ignoreWarning = false
    useSign = true
    dex {
        dexMode = "jar"
        pattern = ["classes*.dex"]
        loader = []
    }
    lib {
        pattern = ["lib/*/*.so"]
    }

    res {
        pattern = ["res/*", "r/*", "assets/*", "resources.arsc", "AndroidManifest.xml"]
        ignoreChange = []
        largeModSize = 100
    }

    packageConfig {
    }
    sevenZip {
        zipArtifact = "com.tencent.mm:SevenZip:1.1.10"
//        path = "/usr/local/bin/7za"
    }
    buildConfig {
        keepDexApply = false
    }
}

```
具体含义可以参考 http://www.tinkerpatch.com/Docs/SDK

4. 新建一个SampleApplication，并配置在AndroidManifest文件中
   
```
public class SampleApplication extends Application {

    ...

    @Override
    public void onCreate() {
        super.onCreate();
        // 我们可以从这里获得Tinker加载过程的信息
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
            .reflectPatchLibrary()
            .setPatchRollbackOnScreenOff(true)
            .setPatchRestartOnSrceenOff(true)
            .setFetchPatchIntervalByHours(3);

        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }

    ...
```
5.在程序入口Activity的onCreate()方法中执行加载补丁方法
```
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TinkerPatch.with().fetchPatchUpdate(true);
    }
```

# 使用步骤
### 1.构建基准包
    方法一：在AS Terminal 命令窗口 执行gradlew assemblerelease 命令
    方法二：在AS gradle执行assemblerelease命令行，如下图
  ![image](http://i2.bvimg.com/656323/ae6a95ca6936677a.png)
  
### 2.把基准包信息填入tinkerpatch.gradle中
 
```
def bakPath = file("${buildDir}/bakApk/")
def baseInfo = "app-1.0.0-0801-14-13-17"
def variantName = "release"
```
### 3.复制保存基准包
 此时此基准包将作为正式服务器版本发布出去。需要保管妥当，将bakApk中对应的版本复制一份保存在本地，以免执行AS中Clean Project任务时删掉。如下图，
 将app-1.0.0-0801-14-13-17整个文件夹复制保存于本地。如果在构建补丁包的时候没有找到的话，就把本地的copy进去。
 ![image](http://i2.bvimg.com/656323/45c239f622cc649d.png)
 
### 4.构建补丁包
  在线上的APP出现bug或者需要进行资源替换，就要生产补丁包，方法和构建基准包方法一样，有两种：
  
  方法一：在AS Terminal 命令窗口 执行gradlew tinkerPatchRelease 命令
  
  方法二：在AS gradle执行tinkerPatchRelease命令行，如下图
  
  ![image](http://i2.bvimg.com/656323/318e285656661cbe.png)
  
  补丁包将位于 build/outputs/tinkerPatch下。如下图
  
 ![image](http://i2.bvimg.com/656323/cba2fdc07e331b9a.png)

### 4.发布补丁包
####   1.注册登陆 http://www.tinkerpatch.com
####   2.新增APP,APP名要和应用程序名称一致
####   3.添加APP版本,把程序中app version      name填写进去。注意，构建补丁包的appVersion code和name都是不变的。这个补丁包仅作用于1.0.0。
####   4.发布新补丁，选择补丁包中patch_signed_7zip.apk这个加签补丁包
####   5.发布成功之后，线上的APP在进入主程序入口的时候，会执行加载补丁包，此时杀死程序重新进入或者锁屏再开屏（初始化TinkerPatch的时候设置setPatchRestartOnSrceenOff为true的情况下）这两种方法，都可以呈现出补丁包的内容。此时热修复已完成。
### 5.监控补丁安装情况，成功率，删除补丁，删除版本，暂停下发等功能，可以详细阅读 http://www.tinkerpatch.com/Docs/intro


# 参考代码
https://github.com/laibinzhi/TinkerPatchSampleDemo