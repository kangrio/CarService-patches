-dontoptimize
-keepattributes *
-keep class android.** { *; }
-keep class com.kangrio.** { *; }
-repackageclasses 'com.kangrio'

-keep class com.android.car.** { *; }
-dontwarn *