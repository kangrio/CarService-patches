plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.kangrio.extension.stub"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }
    buildFeatures {
        aidl = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}