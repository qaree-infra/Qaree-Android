plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    namespace = "com.muhmmad.qaree"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.muhmmad.qaree"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    val nav_version = "2.7.7"
    val paging_version = "3.2.1"
    val hilt_version = "2.50"
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    //Scalable size unit
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    //Scalable size unit for texts
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    //apollo
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")
    implementation("com.apollographql.apollo:apollo-android-support:1.0.0")
    //Dagger-hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_version")
    //ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    //Data Module
    implementation(project(":data"))
    //OTP view
    implementation("com.github.aabhasr1:OtpView:v1.1.2-ktx")
    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-analytics")
    //Firebase-crashlytics
    implementation("com.google.firebase:firebase-crashlytics")
    //Firebase-Performance
    implementation("com.google.firebase:firebase-perf")
    //Shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.1.0@aar")
    //Coil
    implementation("io.coil-kt:coil:2.6.0")
    //Palette
    implementation("androidx.palette:palette-ktx:1.0.0")
    //Paypal
    implementation("com.paypal.android:card-payments:1.3.0")
    implementation("com.paypal.android:paypal-web-payments:1.3.0")
    //Paging 3
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
}

kapt {
    correctErrorTypes = true
}