plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp") version "2.1.10-1.0.29"
    kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.dadadadev.social_feed"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:result"))
    implementation(project(":core:model"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.koin.androidx.compose)
    androidTestImplementation(libs.koin.test)
    androidTestImplementation(libs.koin.test.junit4)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation(libs.androidx.security.crypto)
}