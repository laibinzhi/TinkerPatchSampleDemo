# ʲô�����޸����ȸ��£��Ȳ�������
  һ�仰��������̬���޸��͸���App����Ϊ
# ���޸���ʲô�ô���
  �û����������ǵ�app���������bug��������Ҫ�޸�ĳ�����ܣ����Ǿ�Ҫ���±���һ���汾���û����ǰ�װ���ܽ�����⣬������ݳɱ��ǳ��ߣ���������Ҫ��һ���汾������û��������°�װ����apk��������̿��ܻ�ʹ�û���ʧ�������޸����������û���֪����������¾����bug���޸����¹��ܵ�ʵ��
<!--more-->
# �������޸��Ϳ���һ��������
  Ŀǰ�����ϵ��������޸���ܶ���֧���Ĵ���������ɺ�AndroidManifest�ļ��ı�д��������ش�汾���»�����ҳ�棬������Ҫ�������������̡���bug���޸���һЩ��Դ���滻���ſ���ʹ�����޸�������������ʽ�汾�ͷ������޸��汾ͬ����Ҫ�������ԣ���Ϊ�������޸����������м����Ե����⡣
# ����׼������
-   �˽�android dex�ļ���classLoaderԭ�����޸������ĵײ����ʵ�֣�����google�˽⣩
-   �˽⵱ǰ�����ϱȽ����еļ������޸������Լ�����ѡ��

|  | Tinker | QZone |AndFix |Robust |
| :------:| :------: | :------: |:------: |:------: |
| ���滻	 | yes | yes |no |no |
| So�滻     | yes | no |no |no |
| ��Դ�滻	 | yes | yes |no |no |
| ȫƽ̨֧�� | yes | yes |no |yes |
| ��ʱ��Ч	 | no | no |yes |yes |
| �������		 | ��С	 | �ϴ�	 |��С	 |��С	 |
| ��������С			 | ��С	 | �ϴ�	 |һ��	 |һ��	 |
| ����͸��		 | yes | yes |no |no |
| ���Ӷ�		 | �ϵ� | �ϵ� |���� |���� |
| Rom���			 | Dalvik�ϴ�	 | ��С |��С |��С |
| �ɹ���				 | �ϸ�	 | �ϸ� |һ�� |��� |


��ΪAndFix https://github.com/alibaba/AndFix �Ѿ������û���£��������������ų������Ȼ����Ϊ���Ǿ���Ҫ�õ��ַ�������֮�������Robustû����Դ�滻�������ų������ʣ��Tinker��QZone�����ǶԱȷ���Tinker������ĺͲ�������С���Ƚ�С����������ѡ��[Tinker](https://github.com/Tencent/tinker)Ϊ���ǵ����޸�������

  **�����Ա��Լ�����ѡ����Ҫ��Ҫ�ĵط�**
- 1.���ǵ�������ʲô�������Ǻ���һ�еı�׼��
- 2.���������������ĸ�ѧϰ�ɱ���͡�
- 3.ѧϰ�ɱ�һ��������£�����ѡ���˾�ķ���)��

# Tinker��֪������
����ԭ����ϵͳ���ƣ�Tinker��������֪���⣺
1. Tinker��֧���޸�AndroidManifest.xml��Tinker��֧�������Ĵ����(1.9.0֧��������export��Activity)��
2. ����Google Play�Ŀ������������ƣ���������GP������̬���´��룻
3. ��Android N�ϣ�������Ӧ������ʱ������΢��Ӱ�죻
4. ��֧�ֲ�������android-21���ͣ����ز���ʱ�������׳�"TinkerRuntimeException:checkDexInstall failed"��
5. ������Դ�滻����֧���޸�remoteView������transition������notification icon�Լ�����ͼ�ꡣ


# ʲô��TinkerPatch
  Tinker ��Ҫʹ������һ����̨�����·��͹���������������Ҫ�����䰲ȫ�Ȳ�������TinkerPatch ƽ̨����������Щ�������ṩ�˲�����̨�йܣ��汾������֤���䰲ȫ�ȹ��ܣ���������һ����̨��������Ĳ��������ֻ������һ�� SDK ��������ʹ�� Tinker��

���⣬ͨ�������о� Tinker Դ�룬TinkerTinkerPatch ƽ̨�� Tinker�Ļ����ϼ������������ԣ�

-   һ��ɵ��ʽ���룻������⸴�ӵ����޸�ԭ��һ�д��뼴�ɽ������޸���ʵ�����Զ����� Appliction �� Library��ʹ����������Լ�����Ŀ���κεĸĶ���
-   ��������ʵ�����Ȳ����İ汾�����������Զ��������쳣ʱ�Զ����˵ȹ��ܡ�ͬʱ���ǿ��Լ�ʵ�������·��������ڳ����쳣���ʱ������Ҳ���Կ��ٻع�������
-   �����Ż������� Tinker �ı��븴�Ӷȣ�ʵ���˱���·��ѡ�񣬹��ܿ��صȹ��ܡ�
# ��ν���TinkerPatch
1. ��� gradle �������
-    ����Ŀ��Ŀ¼��gradle.properties��û�����½�һ������д���´��롣Ŀ���Ƿ������Tinker��TinkerPatch�İ汾��
     
```
TINKER_VERSION=1.9.8
TINKERPATCH_VERSION=1.2.8
```
-    ����Ŀ��Ŀ¼��build.gradle�м���
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

2. ���� TinkerPatch SDK,��app moudle�µ�build.gradle�������������ע����com.android.tools.build:gradle 3.0 ���°汾implementation��Ϊcompile,��compileOnly��Ϊprovided��...
```
    //��ʹ��annotation��Ҫ��������,����tinker�������ⶼ����������
    annotationProcessor("com.tinkerpatch.tinker:tinker-android-anno:${TINKER_VERSION}") {
        changing = true
    }
    compileOnly("com.tinkerpatch.tinker:tinker-android-anno:${TINKER_VERSION}") { changing = true }
    implementation("com.tinkerpatch.sdk:tinkerpatch-android-sdk:${TINKERPATCH_VERSION}") {
        changing = true
    }
```

3.TinkerPatch ��ص����ã����ǰ�TinkerPatch������ȫ��д��һ���ļ��У��½�һ���ļ���Ϊtinkerpatch.gradle ����app moudle �� build.gradleͬ����

```
apply plugin: 'tinkerpatch-support'

/**
 * TODO: �밴�Լ��������޸�Ϊ��Ӧ�Լ����̵Ĳ���
 */
def bakPath = file("${buildDir}/bakApk/")
def baseInfo = "app-1.0.0-0727-09-51-05"
def variantName = "release"

/**
 * ���ڲ������������ϸ������ο�
 * http://tinkerpatch.com/Docs/SDK
 */
tinkerpatchSupport {
    /** ������debug��ʱ��ر� tinkerPatch **/
    /** ��disable tinker��ʱ����Ҫ���multiDexKeepProguard��proguardFiles,
        ��Щ�����ļ�������tinkerPatch�Ĳ���Զ���ӣ�����disable����Ҫ�ֶ����
        �����copy��ʾ���е�proguardRules.pro��tinkerMultidexKeep.pro,
        ��Ҫ���ֶ��޸�'tinker.sample.android.app'��ʾ���İ���Ϊ���Լ��İ���, com.xxxǰ׺�İ��������޸�
     **/
    tinkerEnable = true
    reflectApplication = true
    /**
     * �Ƿ����ӹ�ģʽ��ֻ����APK��Ҫ���мӹ�ʱʹ�ã������patchʧ�ܡ�
     * ���ֻ��ĳ������ʹ���˼ӹ̣���ʹ�ö�flavors����
     **/
    protectedApp = true
    /**
     * ʵ�鹦��
     * �����Ƿ�֧������ Activity (����Activity��exported���Ա���Ϊfalse)
     **/
    supportComponent = true

    autoBackupApkPath = "${bakPath}"

    appKey = "4ca6340061006805"

    /** ע��: �������µ�ȫ����, appVersionһ��Ҫ���� **/
    appVersion = "1.0.0"

    def pathPrefix = "${bakPath}/${baseInfo}/${variantName}/"
    def name = "${project.name}-${variantName}"

    baseApkFile = "${pathPrefix}/${name}.apk"
    baseProguardMappingFile = "${pathPrefix}/${name}-mapping.txt"
    baseResourceRFile = "${pathPrefix}/${name}-R.txt"

    /**
     *  ���б����flavors����, ���Բ��գ� https://github.com/TinkerPatch/tinkerpatch-flavors-sample
     *  ע��: �����㲻ͬ��flavor�����ǲ�һ����,��Ȼ�������zip comment�����ļ���ʽ����������Ϣ����ع��ߣ�walle ���� packer-ng��
     **/
}

/**
 * �����û��ڴ������ж�tinkerPatch�Ƿ�ʹ��
 */
android {
    defaultConfig {
        buildConfigField "boolean", "TINKER_ENABLE", "${tinkerpatchSupport.tinkerEnable}"
    }
}

/**
 * һ����˵,�������������Ĳ������κε��޸�
 * ���ڸ���������ϸ������ο�:
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
���庬����Բο� http://www.tinkerpatch.com/Docs/SDK

4. �½�һ��SampleApplication����������AndroidManifest�ļ���
   
```
public class SampleApplication extends Application {

    ...

    @Override
    public void onCreate() {
        super.onCreate();
        // ���ǿ��Դ�������Tinker���ع��̵���Ϣ
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

        // ��ʼ��TinkerPatch SDK, �������ÿɲ���API�½��е�,��ʼ��SDK
        TinkerPatch.init(tinkerApplicationLike)
            .reflectPatchLibrary()
            .setPatchRollbackOnScreenOff(true)
            .setPatchRestartOnSrceenOff(true)
            .setFetchPatchIntervalByHours(3);

        // ÿ��3��Сʱ(ͨ��setFetchPatchIntervalByHours����)ȥ���ʺ�̨ʱ���и���,ͨ��handlerʵ����ѵ��Ч��
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }

    ...
```
5.�ڳ������Activity��onCreate()������ִ�м��ز�������
```
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TinkerPatch.with().fetchPatchUpdate(true);
    }
```

# ʹ�ò���
### 1.������׼��
    ����һ����AS Terminal ����� ִ��gradlew assemblerelease ����
    ����������AS gradleִ��assemblerelease�����У�����ͼ
  ![image](http://lbz-blog.test.upcdn.net/post/gradletask.png)
  
### 2.�ѻ�׼����Ϣ����tinkerpatch.gradle��
 
```
def bakPath = file("${buildDir}/bakApk/")
def baseInfo = "app-1.0.0-0801-14-13-17"
def variantName = "release"
```
### 3.���Ʊ����׼��
 ��ʱ�˻�׼������Ϊ��ʽ�������汾������ȥ����Ҫ�����׵�����bakApk�ж�Ӧ�İ汾����һ�ݱ����ڱ��أ�����ִ��AS��Clean Project����ʱɾ��������ͼ��
 ��app-1.0.0-0801-14-13-17�����ļ��и��Ʊ����ڱ��ء�����ڹ�����������ʱ��û���ҵ��Ļ����Ͱѱ��ص�copy��ȥ��
 ![image](http://lbz-blog.test.upcdn.net/post/savebaseapk.png)
 
### 4.����������
  �����ϵ�APP����bug������Ҫ������Դ�滻����Ҫ�����������������͹�����׼������һ���������֣�
  
  ����һ����AS Terminal ����� ִ��gradlew tinkerPatchRelease ����
  
  ����������AS gradleִ��tinkerPatchRelease�����У�����ͼ
  
  ![image](http://lbz-blog.test.upcdn.net/post/gradletinkertask.png)
  
  ��������λ�� build/outputs/tinkerPatch�¡�����ͼ
  
 ![image](http://lbz-blog.test.upcdn.net/post/tinkerapkpath.png)

### 4.����������
####   1.ע���½ http://www.tinkerpatch.com
####   2.����APP,APP��Ҫ��Ӧ�ó�������һ��
####   3.���APP�汾,�ѳ�����app version      name��д��ȥ��ע�⣬������������appVersion code��name���ǲ���ġ������������������1.0.0��
####   4.�����²�����ѡ�񲹶�����patch_signed_7zip.apk�����ǩ������
####   5.�����ɹ�֮�����ϵ�APP�ڽ�����������ڵ�ʱ�򣬻�ִ�м��ز���������ʱɱ���������½�����������ٿ�������ʼ��TinkerPatch��ʱ������setPatchRestartOnSrceenOffΪtrue������£������ַ����������Գ��ֳ������������ݡ���ʱ���޸�����ɡ�
### 5.��ز�����װ������ɹ��ʣ�ɾ��������ɾ���汾����ͣ�·��ȹ��ܣ�������ϸ�Ķ� http://www.tinkerpatch.com/Docs/intro


# �ο�����
https://github.com/laibinzhi/TinkerPatchSampleDemo