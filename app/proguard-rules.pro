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

##############################################
## Kotlin Coroutines
##############################################
-dontwarn kotlinx.coroutines.**

##############################################
## Retrofit + OkHttp
##############################################

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**

# Retrofit Annotations
-keepattributes Signature
-keepattributes *Annotation*

##############################################
## Gson Converter
##############################################

# Gson Core
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Keep POJO models (very important!)
-keep class juniar.nicolas.pokeapp.jetpackcompose.domain.model.** { *; }
-keep class juniar.nicolas.pokeapp.jetpackcompose.data.dto.** { *; }

##############################################
## Room Database
##############################################

# Keep Room entities, DAO, database
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Keep your entities and DAO
-keep class juniar.nicolas.pokeapp.jetpackcompose.data.local.** { *; }

##############################################
## Hilt / Dagger
##############################################
-keep class dagger.hilt.** { *; }
-keep class dagger.hilt.android.** { *; }
-keep class dagger.hilt.internal.** { *; }

-dontwarn dagger.hilt.**
-dontwarn dagger.hilt.android.**
-dontwarn dagger.hilt.internal.**

##############################################
## Jetpack Navigation
##############################################
-dontwarn androidx.navigation.**

##############################################
## Paging 3
##############################################
-dontwarn androidx.paging.**

##############################################
## DataStore
##############################################
-dontwarn androidx.datastore.**

##############################################
## Coil
##############################################
-dontwarn coil.**
-keep class coil.** { *; }
-dontwarn coil.decode.**
-dontwarn coil.fetch.**
-dontwarn coil.memory.**

##############################################
## Compose
##############################################
# Compose does not require extra rules, but safe to keep to avoid warnings
-dontwarn androidx.compose.**

##############################################
## General AndroidX
##############################################
-dontwarn androidx.**

# Keep Chucker main classes
-keep class com.chuckerteam.chucker.** { *; }

# Keep OkHttp interceptor
-keep class com.chuckerteam.chucker.api.** { *; }

# Avoid warnings
-dontwarn com.chuckerteam.chucker.**
