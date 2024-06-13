plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    namespace = "com.muhmmad.qaree"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.muhmmad.qaree"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        project.setProperty("archivesBaseName", "Qaree-$versionName")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        forEach {
            it.buildConfigField(
                "String",
                "paypalClientId",
                "\"AcwxZmzj2-Kstpv2OU4ivSAQZlYA6ThecdxZlXOI6T0QL_W--ODJ6ad0YuKPcBU6VmP2MUlsdrjo15Ey\""
            )
            it.buildConfigField(
                "String",
                "googleClientID",
                "\"1059587907099-likhbfpqumps57uamd86q247uueootdn.apps.googleusercontent.com\""
            )
            it.buildConfigField(
                "String",
                "googleWebClientID",
                "\"1059587907099-l555vs81f8dd0r2o17belt2bobbg15c0.apps.googleusercontent.com\""
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
    val hilt_version = "2.44"
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
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
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    //Data Module
    api(project(":data"))
    //OTP view
    implementation("com.github.aabhasr1:OtpView:v1.1.2-ktx")
    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    //Firebase-crashlytics
    implementation("com.google.firebase:firebase-crashlytics")
    //Firebase-Performance
    implementation("com.google.firebase:firebase-perf")
    //Firebase-FCM
    implementation("com.google.firebase:firebase-messaging")
    //Shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.1.0@aar")
    //Coil
    implementation("io.coil-kt:coil:2.6.0")
    //Palette
    implementation("androidx.palette:palette-ktx:1.0.0")
    //Paypal
    implementation("com.paypal.sdk:paypal-android-sdk:2.16.0")
    implementation("com.paypal.android:card-payments:1.3.0")
    implementation("com.paypal.android:paypal-web-payments:1.3.0")
    //Paging 3
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    //Socket IO
    implementation("io.socket:socket.io-client:2.0.0")
    //Gson
    implementation("com.google.code.gson:gson:2.10.1")
    //DataStore
    implementation("androidx.datastore:datastore:1.1.1")
    implementation("androidx.datastore:datastore-core:1.1.1")
    //Login With Google
    implementation("com.google.android.gms:play-services-auth:21.2.0")
}

kapt {
    correctErrorTypes = true
}