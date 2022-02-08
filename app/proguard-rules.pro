#-------------------------------------------基本不用动区域 start--------------------------------------------
#---------------------------------基本指令区----------------------------------
#关闭代码二进制优化
-dontoptimize
#不进行代码压缩
-dontshrink
#指定代码的压缩级别，在0~7之间，默认为5，一般不需要更改
-optimizationpasses 5
#包明不混合大小写，混淆后的类名为小写
-dontusemixedcaseclassnames
#指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses
#指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
#不做预校验，preverify是proguard的4个步骤之一，android不需要做预校验，去除这一步可以加快混淆速度
-dontpreverify
#忽略所有警告
-ignorewarnings
#混淆时是否记录日志
-verbose
#指定混淆时采用的算法，后面的参数是一个过滤器
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*,!code/allocation/variable
#保护注解
-dontwarn android.annotation
#保护异常
-keepattributes Exceptions
#保护反射
-keepattributes EnclosingMethod
#保护代码中的泛型不被混淆
-keepattributes Signature
#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#如果引用了v4或者v7包
-dontwarn android.support.**
-keep class android.support.** {*;}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * { native <methods>; }
-printmapping proguardMapping.txt
#混淆前后的映射 (我是在使用了这条语句后，就在build\outputs\mapping\你的项目名\release\文件夹下，出现了dump.txt, mapping.txt, seeds.txt, usage.txt)
-printmapping mapping.txt
# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#----------------------------------基本指令区---------------------------------------
#---------------------------------默认保留区 start ---------------------------------
# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用自定义控件类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends android.support.**
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keepclassmembers class **$JavaScriptObject { public *; }
-dontwarn android.support.v7.**
-dontwarn android.support.v4.**
#kotlin 相关
-dontwarn kotlin.**
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-keepclasseswithmembers @kotlin.Metadata class * { *; }
-keepclassmembers class **.WhenMappings {
    <fields>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

-keep class kotlinx.** { *; }
-keep interface kotlinx.** { *; }
-dontwarn kotlinx.**
-dontnote kotlinx.serialization.SerializationKt

-keep class org.jetbrains.** { *; }
-keep interface org.jetbrains.** { *; }
-dontwarn org.jetbrains.**
#WebView与JavascriptInterface
-keep public class android.webkit.WebView {*;}
-keep public class android.webkit.WebViewClient {*;}
-keepattributes *JavascriptInterface*
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *; }
#保持 Serializable 不被混淆
-keepclassmembers class * implements java.io.Serializable { <fields>; }
#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#不混淆资源类
-keepclassmembers class **.R$* { public static <fields>; }
#屏蔽日志
-assumenosideeffects class android.util.LogHelper.{
public static boolean isLogHelper.able(Java.lang.String, int);
public static int v(...);
public static int i(...);
public static int w(...);
public static int d(...);
public static int e(...);
}
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
# 禁止混淆枚举类型
-keepclassmembers,allowoptimization enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 禁止混淆Parcelable对象
-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}
# 禁止混淆Serializable对象
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#不混淆Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 保留R下面的资源
-keep class **.R$* {
 *;
}
#对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
}
#webview
#有用到WEBView的JS调用接口不被混淆
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#---------------------------------默认保留区 end---------------------------------
#-------------------------------------------基本不用动区域 end-----------------------------------

#---------------------------------------第三方包 start------------------------------------------------
#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.** -keep class **$$ViewInjector{ *; }
-keepclasseswithmembernames class * {     @butterknife.* <fields>;}
-keepclasseswithmembernames class * {     @butterknife.* <methods>;}

#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#rxandroid
-dontwarn sun.misc.**
-dontnote rx.internal.util.PlatformDependent
-dontwarn rx.**
-keep class rx.**{*;}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#百度文字识别
-keep class com.baidu.ocr.sdk.**{*;}
-dontwarn com.baidu.ocr.**
#eventbus
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keep class org.greenrobot.eventbus.android.AndroidComponentsImpl
#-----------百度地图 start--------------
-keep class com.baidu.mapapi.** {*; }
-keep class com.baidu.platform.** {*; }
-keep class com.baidu.location.** {*; }
-keep class com.baidu.vi.** {*; }
-keep class vi.com.gdi.bgl.android.** {*; }
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-keep class com.mapabc.minimap.map.vmap.** {*;}
-keep class com.autonavi.**{*;}
-dontwarn com.baidu.**
#3D 地图
-keep class com.amap.api.mapcore.**{*;}
-keep class com.amap.api.maps.**{*;}
# 搜索
-keep class com.amap.api.services.**{*;}
# 2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
#定位
-keep class com.amap.api.fence.**{*;}
-dontwarn com.amap.api.location.**
-dontwarn com.aps.**
-keep class com.amap.api.location.**{*;}
-keep class com.aps.**{*;}
#解决Android10的定位·
-keep class com.loc.**{*;}
#-----------百度地图 end----------------------
#---------- gson ----------
-keep class sun.misc.Unsafe { *; }
-keep class sun.misc.Unsafe.** { *; }
-keep class com.google.** { *; }

# AndroidEventBus
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}

#jpush
-dontoptimize
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

#-融云混淆
-keepattributes Exceptions,InnerClasses
-keep class io.rong.** {*;}
-keep class cn.rongcloud.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent {*;}
-dontwarn io.rong.push.**
-dontnote com.xiaomi.**
-dontnote com.google.android.gms.gcm.**
-dontnote io.rong.**

# Location
-keep class com.amap.api.**{*;}
-keep class com.amap.api.services.**{*;}

#okgo
-dontwarn okio.**
-keep class okio.**{*;}
#okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

#okrx
-dontwarn com.lzy.okrx.**
-keep class com.lzy.okrx.**{*;}

#okrx2
-dontwarn com.lzy.okrx2.**
-keep class com.lzy.okrx2.**{*;}

#okserver
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.**{*;}

#极光
-keep class cn.jiguang.**{
    *;
}
-dontwarn javax.annotation.**
-dontwarn sun.misc.Unsafe
-dontwarn org.conscrypt.*
-dontwarn okio.**

#其他混淆
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}

-keep class com.qq.e.** {
    public protected *;
}
-keep class android.support.v4.**{
    public *;
}
-keep class android.support.v7.**{
    public *;
}

-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**

-keepclassmembers class * extends android.app.Activity { public void *(android.view.View);
}
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
-keep class com.baidu.mobads.*.** { *; }

#AVLoadingIndicatorView
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

#MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }

##XBanner 图片轮播混淆配置
-keep class com.stx.xhb.xbanner.**{*;}

#okhttp3
-dontwarn com.squareup.okhttp3.**
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep class com.squareup.okhttp3.** { *;}
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

#Picasso
-dontwarn com.squareup.okhttp.**

#gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.qiancheng.carsmangersystem.**{*;}
