plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.dadadadev.common"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.browser)
}