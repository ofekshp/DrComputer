//buildscript {
//    dependencies {
//        val nav_version = "2.7.7"
//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
//        classpath("com.google.gms:google-services:4.4.1")
//    }
//}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}