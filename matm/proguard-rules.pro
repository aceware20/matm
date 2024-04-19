# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-keepattributes Signature
#-keepattributes Annotation
#-dontwarn sun.misc.**
#-keep class com.fingpay.microatmsdk.data.** {
#    <fields>;
#    public <methods>;
#}
#-keep class * extends com.google.gson.TypeAdapter
#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer
#-keepclassmembers,allowobfuscation class * {
#  @com.google.gson.annotations.SerializedName <fields>;
#}
#-dontwarn com.squareup.okhttp.**

# The following line may be different
#-libraryjars <java.home>/lib/rt.jar(java/**,javax/**)


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
# (3)Not remove unused code
#-dontshrink

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class com.android.vending.licensing.ILicensingService
# (2)Simple XML
-keep public class org.simpleframework.**{ *; }
-keep class org.simpleframework.xml.**{ *; }
-keep class org.simpleframework.xml.core.**{ *; }
-keep class org.simpleframework.xml.util.**{ *; }
# (1)Annotations and signatures
-keepclassmembers class * {
    @org.simpleframework.xml.* *;
}
-keepattributes ElementList, Root, Element, Attribute
-keepattributes *Annotation*
-keepattributes Signature

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepattributes EnclosingMethod

-keepattributes *Annotation*,Signature
-keep class com.mantra.mfs100.** { *; }
-keepattributes Signature

-keep class org.junit.** { *; }
-dontwarn org.junit.**

-keep class junit.** { *; }
-dontwarn junit.**

-keep public class org.bouncycastle.**{ *; }
-keep public class org.apache.**{ *; }
-dontwarn org.apache.**

-keep public class com.mantra.mfs100regdvcsample.model.**{ *; }

-keepattributes Signature
-keepattributes Annotation
-dontwarn sun.misc.**
-keep class com.fingpay.microatmsdk.data.** {
    <fields>;
    public <methods>;
}
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keep class org.json.* { *; }
-keep class com.acemoney.matm.transferData.**{*;}





-keep class com.androidbuts.multispinnerfilter.** {*; }


-dontwarn org.apache.poi.**

-keep class androidx.appcompat.** { *; }
-keepclassmembers class androidx.appcompat.** { *; }

-keep class androidx.core.** { *; }
-keepclassmembers class androidx.core.** { *; }

-keep class com.google.android.material.** { *; }
-keepclassmembers class com.google.android.material.** { *; }

-keep class com.github.bumptech.glide.** { *; }
-keepclassmembers class com.github.bumptech.glide.** { *; }

-keep class androidx.constraintlayout.** { *; }
-keepclassmembers class androidx.constraintlayout.** { *; }

-keep class com.google.android.gms.maps.** { *; }
-keepclassmembers class com.google.android.gms.maps.** { *; }

-keep class androidx.navigation.** { *; }
-keepclassmembers class androidx.navigation.** { *; }

-keep class com.squareup.retrofit2.** { *; }
-keepclassmembers class com.squareup.retrofit2.** { *; }

-keep class com.squareup.okhttp3.** { *; }
-keepclassmembers class com.squareup.okhttp3.** { *; }

-keep class com.squareup.okio.** { *; }
-keepclassmembers class com.squareup.okio.** { *; }

-keep class com.google.code.gson.** { *; }
-keepclassmembers class com.google.code.gson.** { *; }

-keep class com.github.ybq.** { *; }
-keepclassmembers class com.github.ybq.** { *; }

-keep class org.bouncycastle.** { *; }
-keepclassmembers class org.bouncycastle.** { *; }

-keep class com.google.code.ksoap2.** { *; }
-keepclassmembers class com.google.code.ksoap2.** { *; }

-keep class androidx.legacy.** { *; }
-keepclassmembers class androidx.legacy.** { *; }

-keep class org.apache.commons.** { *; }
-keepclassmembers class org.apache.commons.** { *; }

-keep class androidx.cardview.** { *; }
-keepclassmembers class androidx.cardview.** { *; }

-keep class com.github.mukeshsolanki.** { *; }
-keepclassmembers class com.github.mukeshsolanki.** { *; }

-keep class io.sentry.** { *; }
-keepclassmembers class io.sentry.** { *; }

-keep class com.squareup.picasso.** { *; }
-keepclassmembers class com.squareup.picasso.** { *; }

-keep class fr.avianey.com.viewpagerindicator.** { *; }
-keepclassmembers class fr.avianey.com.viewpagerindicator.** { *; }

-keep class com.intuit.sdp.** { *; }
-keepclassmembers class com.intuit.sdp.** { *; }

-keep class org.jsoup.** { *; }
-keepclassmembers class org.jsoup.** { *; }

-keep class com.razorpay.** { *; }
-keepclassmembers class com.razorpay.** { *; }

-keep class com.ethanhua.skeleton.** { *; }
-keepclassmembers class com.ethanhua.skeleton.** { *; }

-keep class io.supercharge.shimmerlayout.** { *; }
-keepclassmembers class io.supercharge.shimmerlayout.** { *; }

-keep class androidx.multidex.** { *; }
-keepclassmembers class androidx.multidex.** { *; }

-keep class net.sourceforge.jexcelapi.** { *; }
-keepclassmembers class net.sourceforge.jexcelapi.** { *; }

-keep class pub.devrel.easypermissions.** { *; }
-keepclassmembers class pub.devrel.easypermissions.** { *; }

# ... Add similar rules for other libraries ...

# Keep rules for Firebase libraries
-keep class com.google.firebase.** { *; }
-keepclassmembers class com.google.firebase.** { *; }

# Keep rules for your project's modules
-keep class com.nativess.xmllayouttopdflibrary.** { *; }
-keepclassmembers class com.yourprojectpackage.** { *; }


-keep class com.google.android.play.** { *; }
-keepclassmembers class com.google.android.play.** { *; }

-keep class com.github.Gkemon.** { *; }
-keepclassmembers class com.github.Gkemon.** { *; }

-keep class com.github.native.** { *; }
-keepclassmembers class com.github.native.** { *; }

-keep class com.intuit.sdp.** { *; }
-keepclassmembers class com.intuit.sdp.** { *; }


-keep class androidx.constraintlayout.** { *; }
-keepclassmembers class androidx.constraintlayout.** { *; }


-keep class com.github.smarteist.** { *; }
-keepclassmembers class com.github.smarteist.** { *; }

-keep class com.firebaseui.** { *; }
-keepclassmembers class com.firebaseui.** { *; }

-keep class com.squareup.retrofit2.** {*;}
-keepclassmembers class com.squareup.retrofit2.** {*;}

-keep class io.reactivex.rxjava2.** {*; }
-keepclassmembers class io.reactivex.rxjava2.** {*; }


-keep class com.karumi.** {*; }
-keepclassmembers class com.karumi.** {*; }

-keep class com.github.yalantis.** {*; }
-keepclassmembers class com.github.yalantis.** {*; }

-keep class androidx.work.** {*; }
-keepclassmembers class androidx.work.** {*; }

-keep class com.journeyapps.** {*; }
-keepclassmembers class com.journeyapps.** {*; }

-keep class im.crisp.** {*; }
-keepclassmembers class im.crisp.** {*; }

-keep class androidx.multidex.** {*; }
-keepclassmembers class androidx.multidex.** {*; }

-keep class com.github.clans.** {*; }
-keepclassmembers class com.github.clans.** {*; }

-keep class com.airbnb.android.** {*; }
-keepclassmembers class com.airbnb.android.** {*; }

-keep class com.jakewharton.timber.** {*; }
-keepclassmembers class com.jakewharton.timber.** {*; }

-keep class com.makeramen.** {*; }
-keepclassmembers class com.makeramen.** {*; }

-keep class com.ncorti.** {*; }
-keepclassmembers class com.ncorti.** {*; }

-keep class me.philio.** {*; }
-keepclassmembers class me.philio.** {*; }

-keep class androidx.lifecycle.** {*; }
-keepclassmembers class androidx.lifecycle.** {*; }

-keep class org.jetbrains.kotlinx.** {*; }
-keepclassmembers class org.jetbrains.kotlinx.** {*; }


-keep class com.google.android.gms.** {*; }
-keepclassmembers class com.google.android.gms.** {*; }

-keep class com.facebook.shimmer.** {*; }
-keepclassmembers class com.facebook.shimmer.** {*; }



-keep class com.gitlab.isu_service.** {*; }
-keepclassmembers class com.gitlab.isu_service.** {*; }

-keep class com.gitlab.isu_tech.** {*; }
-keepclassmembers class com.gitlab.isu_tech.** {*; }

-keep class xml.** {*;}
-keepclassmembers class xml.** {*;}

-keep class androidx.browser.** {*; }
-keepclassmembers class androidx.browser.** {*; }

-keep class com.gitlab.isu_tech.** {*; }
-keepclassmembers class com.gitlab.isu_tech.** {*; }

-keep class com.gitlab.isu_sdk.** {*; }
-keepclassmembers class com.gitlab.isu_sdk.** {*; }

-keep class com.scottyab.** {*; }
-keepclassmembers class com.scottyab.** {*; }

-keep class io.** {*; }
-keepclassmembers class io.** {*; }

-keep class com.ajts.androidmads.SQLite2Excel.** {*; }
-keepclassmembers class com.ajts.androidmads.SQLite2Excel.** {*; }

-keep class com.lk.** {*;}
-keepclassmembers class com.lk.** {*;}

-keep class com.mf.** {*;}
-keepclassmembers class com.mf.** {*;}

-keep class com.morefun.** {*;}
-keepclassmembers class com.morefun.** {*;}

-keep class com.newwmlab.** {*;}
-keepclassmembers class com.newwmlab.** {*;}

-keep class com.mosambee.** {*;}
-keepclassmembers class com.mosambee.** {*;}


-keep class com.mmsc.** {*;}
-keepclassmembers class com.mmsc.** {*;}

-keep class com.fingpay.** {*; }
-keepclassmembers class com.fingpay.** {*; }

-keep class com.anfu.** {*; }
-keepclassmembers class com.anfu.** {*; }

-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.** { *; }
-keep class org.json.**{*;}
-keep class org.jsoup.**{*;}

-keep class com.pos.possdk.api_services.**{ *; }
-keep class com.pos.possdk.**{ *;}
-keep class com.aeps.aeps_sdk.**{ *;}
-keep class com.sdk.matmsdk.**{*;}
-keep class com.sdk.matmsdk.model.response.**{*;}
-keep class com.aeps.aeps_sdk.models.**{*;}
-keep class com.pos.possdk.api_services.MPOS.**{*;}
-keep class com.pos.possdk.api_services.otpmodel.**{*;}
-keep class com.pos.possdk.api_services.response.**{*;}
-keep class com.pos.possdk.api_services.responsemodel.**{*;}
-keep class com.pos.possdk.api_services.ModelPOSSDKData.**{*;}





























































































