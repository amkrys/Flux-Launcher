plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.flux.launcher"
    compileSdk = 34

    val name = "0.0.1"
    val code = 1

    defaultConfig {
        applicationId = "com.flux.launcher"
        minSdk = 24
        targetSdk = 34
        versionCode = code
        versionName = name
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
    hilt {
        enableAggregatingTask = false
    }
    lint {
        abortOnError = false
    }
    tasks.withType<org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask>()
        .configureEach {
            kaptProcessJvmArgs.add("-Xmx512m")
        }
}

dependencies {

    // core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // preference
    implementation(libs.androidx.preference)

    // lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    // dimens
    implementation(libs.dimensSp)
    implementation(libs.dimensDp)

    // glide
    implementation(libs.glide.transformations)
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    // room
    implementation(libs.room)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}