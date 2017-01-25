# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

## ButterKnife ##
## https://guides.codepath.com/android/Configuring-ProGuard#butterknife ##

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

## Glide ##
## https://github.com/bumptech/glide#proguard ##

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

## RxJava ##
## https://gist.github.com/kosiara/487868792fbd3214f9c9 ##

-keep class rx.schedulers.Schedulers {
    public static <methods>;
}

-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}

-keep class rx.schedulers.TestScheduler {
    public <methods>;
}

-keep class rx.schedulers.Schedulers {
    public static ** test();
}

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

-dontwarn rx.internal.util.unsafe.**

## GSON ##
## https://guides.codepath.com/android/Configuring-ProGuard#gson ##

-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

-keepnames class * { @icepick.State *;}

## Retrolambda ##
## https://github.com/evant/gradle-retrolambda#proguard ##

-dontwarn java.lang.invoke.*

## OkHttp3 ##
## https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-square-okhttp3.pro ##

-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

## Okio ##
## https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-square-okio.pro ##

-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

## Retrofit2 ##
## https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-square-retrofit2.pro##

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

## Moxy ##

-keep class **$$PresentersBinder
-keep class **$$State
-keep class **$$ParamsHolder
-keep class **$$ViewStateClassNameProvider
-keepnames class * extends com.arellomobile.mvp.*

## OrmLite ##
## http://stackoverflow.com/questions/9853096/proguard-with-ormlite-on-android ##

# OrmLite uses reflection
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }

# Keep the helper class and its constructor
-keep class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
-keepclassmembers class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper {
  public <init>(android.content.Context);
}

# Keep the annotations
-keepattributes *Annotation*

# Keep all model classes that are used by OrmLite
# Also keep their field names and the constructor
-keep @com.j256.ormlite.table.DatabaseTable class * {
    @com.j256.ormlite.field.DatabaseField <fields>;
    @com.j256.ormlite.field.ForeignCollectionField <fields>;
    # Add the ormlite field annotations that your model uses here
    <init>();
}

## Parceler ##
## https://github.com/johncarl81/parceler#configuring-proguard ##

-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }

## Icepick ##
## https://github.com/frankiesardo/icepick#proguard ##

-dontwarn icepick.**
-keep class icepick.** { *; }
-keep class **$$Icepick { *; }
-keepclasseswithmembernames class * {
    @icepick.* <fields>;
}

-keepnames class * { @icepick.State *; }
