buildscript {
    repositories {
        // Check that you have the following line (if not, add it):
        google()  // Google's Maven repository
        mavenCentral()

    }
}
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "8.2.2" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}