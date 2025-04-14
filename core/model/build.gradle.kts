plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.dadadadev.model"
    compileSdk = 35
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
}